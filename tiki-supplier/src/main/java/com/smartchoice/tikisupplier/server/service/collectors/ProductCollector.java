package com.smartchoice.tikisupplier.server.service.collectors;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;
import com.smartchoice.tikisupplier.server.model.dto.TikiProductReponsePaging;
import com.smartchoice.tikisupplier.server.model.dto.TikiProductResponse;
import com.smartchoice.tikisupplier.server.model.dto.TikiProductResponseData;

@Component
public class ProductCollector {

    private static final Logger log = LogManager.getLogger(ProductCollector.class);

    private CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(10)
            .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build())
            .build();

    private Gson gson = SCGson.getGson();

    @Value("${tiki.api.product.endpoint}")
    private String tikiProductApiEndpoint;

    @Value("${tiki.product.endpoint}")
    private String tikiProductEndpoint;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.self-config.max-attempts}")
    private Long maxAttempts;

    private TikiProductResponse executeGet(ProductRequest productRequest, Long page, Long limit) throws IOException, URISyntaxException {
        TikiProductResponse tikiProductResponse = new TikiProductResponse();
        // construct the request
        URIBuilder builder = new URIBuilder(tikiProductApiEndpoint + "/v2/products");
        builder.setParameter("category", String.valueOf(productRequest.getExternalCategoryId()));
        builder.setParameter("q", productRequest.getProductPrimaryKeySearch());
        builder.setParameter("page", String.valueOf(page));
        builder.setParameter("limit", String.valueOf(limit));

        HttpGet httpGet = new HttpGet(builder.build());
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                log.info("Product collector: request {} response {}", productRequest, responseBody);
                tikiProductResponse = gson.fromJson(responseBody, TikiProductResponse.class);
            } else {
                log.info("Product collector: request {} response code {}", productRequest, statusCode);
            }
        }

        return tikiProductResponse;
    }

    public void collect(ProductRequest productRequest) {
        try {
            log.info("Collecting data for the product request {}", productRequest);
            productRequest.increaseAttempts();
            List<TikiProductResponseData> tikiProductResponseDataList = new ArrayList<>();
            Long limit = 100L;
            Long currentPage = 1L;
            Long lastPage;
            do {
                TikiProductResponse tikiProductResponse = executeGet(productRequest, currentPage, limit);
                if (tikiProductResponse != null && CollectionUtils.isNotEmpty(tikiProductResponse.getData())) {
                    tikiProductResponseDataList.addAll(tikiProductResponse.getData());
                }
                TikiProductReponsePaging paging = tikiProductResponse.getPaging();
                if (paging != null) {
                    lastPage = paging.getLast_page();
                    currentPage++;
                } else {
                    break;
                }
            } while (currentPage <= lastPage);

            publishProducts(tikiProductResponseDataList, productRequest);
        } catch (Exception e) {
            log.error("Tiki supplier: Unexpected exception {}", productRequest, e);
            if (productRequest.getConsumerAttempts() < maxAttempts) {
                log.info("Sending the product request to retry queue {}", productRequest);
                amqpTemplate.convertAndSend(Supplier.TIKI.getProductRequestRetryExchange(),
                        Supplier.TIKI.getProductRequestRetryQueue(), productRequest);
            } else {
                throw new AmqpRejectAndDontRequeueException("Exceeded maximum attempts, parking the product request. " + productRequest , e);
            }
        }
    }

    private void publishProducts(List<TikiProductResponseData> tikiProductResponseDataList, ProductRequest productRequest) {
        tikiProductResponseDataList.forEach(tikiProductResponseData -> {
            ProductResponse productResponse = tikiProductResponseData.toProductResponse(productRequest, tikiProductEndpoint);
            amqpTemplate.convertAndSend(ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_MAIN,
                    QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN, productResponse);
        });
    }
}
