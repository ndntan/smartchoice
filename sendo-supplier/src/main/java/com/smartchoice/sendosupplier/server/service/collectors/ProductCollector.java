package com.smartchoice.sendosupplier.server.service.collectors;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
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
import com.google.gson.JsonObject;
import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;
import com.smartchoice.sendosupplier.server.dto.SendoProductResponse;
import com.smartchoice.sendosupplier.server.dto.SendoProductResponseData;
import com.smartchoice.sendosupplier.server.dto.SendoProductResponseResult;
import com.smartchoice.sendosupplier.server.service.TokenProvider;

@Component
public class ProductCollector {

    private static final Logger log = LogManager.getLogger(ProductCollector.class);

    private CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(10)
            .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build())
            .build();

    private Gson gson = SCGson.getGson();

    @Value("${sendo.api.endpoint}")
    private String sendoApiEndpoint;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${spring.rabbitmq.self-config.max-attempts}")
    private Long maxAttempts;

    private SendoProductResponse executeGet(ProductRequest productRequest, Long limit) throws IOException, URISyntaxException {
        SendoProductResponse sendoProductResponse = new SendoProductResponse();
        // construct the request
        URIBuilder builder = new URIBuilder(sendoApiEndpoint + "/api/partner/product/search");
        HttpPost httpPost = new HttpPost(builder.build());
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + tokenProvider.obtainSendoToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("page_size", limit);
        jsonObject.addProperty("product_name", productRequest.getProductPrimaryKeySearch());
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                log.info("Product collector: request {} response {}", productRequest, responseBody);
                sendoProductResponse = gson.fromJson(responseBody, SendoProductResponse.class);
            } else {
                log.info("Product collector: request {} response code {}", productRequest, statusCode);
            }
        }

        return sendoProductResponse;
    }

    public void collect(ProductRequest productRequest) {
        try {
            log.info("Collecting data for the product request {}", productRequest);
            productRequest.increaseAttempts();
            SendoProductResponse sendoProductResponse = executeGet(productRequest, 1000L);
            if (sendoProductResponse != null) {
                SendoProductResponseResult result = sendoProductResponse.getResult();
                if (result != null) {
                    List<SendoProductResponseData> sendoProductResponseData = result.getData();
                    if (CollectionUtils.isNotEmpty(sendoProductResponseData)) {
                        publishProducts(sendoProductResponseData, productRequest);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Sendo supplier: Unexpected exception {}", productRequest, e);
            if (productRequest.getConsumerAttempts() < maxAttempts) {
                log.info("Sending the product request to retry queue {}", productRequest);
                amqpTemplate.convertAndSend(Supplier.SENDO.getProductRequestRetryExchange(),
                        Supplier.SENDO.getProductRequestRetryQueue(), productRequest);
            } else {
                throw new AmqpRejectAndDontRequeueException("Exceeded maximum attempts, parking the product request. " + productRequest, e);
            }
        }
    }

    private void publishProducts(List<SendoProductResponseData> sendoProductResponseData, ProductRequest productRequest) {
        sendoProductResponseData.forEach(item -> {
            ProductResponse productResponse = item.toProductResponse(productRequest);
            amqpTemplate.convertAndSend(ExchangeName.SC_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_MAIN,
                    QueueName.SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN, productResponse);
        });
    }
}
