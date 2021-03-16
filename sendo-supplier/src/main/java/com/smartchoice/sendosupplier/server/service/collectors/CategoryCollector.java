package com.smartchoice.sendosupplier.server.service.collectors;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simmetrics.StringMetric;
import org.simmetrics.builders.StringMetricBuilder;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.smartchoice.common.dto.CategoryRequest;
import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;
import com.smartchoice.common.util.VNCharacterUtil;
import com.smartchoice.sendosupplier.server.dto.SendoCategoryResponse;
import com.smartchoice.sendosupplier.server.service.TokenProvider;

@Component
public class CategoryCollector {

    private static final Logger log = LogManager.getLogger(CategoryCollector.class);

    @Value("${sendo.api.endpoint}")
    private String sendoApiEndpoint;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${spring.rabbitmq.self-config.max-attempts}")
    private Long maxAttempts;

    private CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(10)
            .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build())
            .build();

    private Gson gson = SCGson.getGson();

    public void collect(CategoryRequest categoryRequest) {
        try {
            log.info("Collecting data for the category request {}", categoryRequest);
            categoryRequest.increaseAttempts();
            // construct the request
            URIBuilder builder = new URIBuilder(sendoApiEndpoint + "/api/partner/category/0");
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + tokenProvider.obtainSendoToken());
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    log.info("Category collector: request {} response {}", categoryRequest, responseBody);
                    SendoCategoryResponse sendoCategoryResponse = gson.fromJson(responseBody, SendoCategoryResponse.class);
                    List<CategoryResponse> categories = sendoCategoryResponse.getResult();
                    if (CollectionUtils.isNotEmpty(categories)) {
                        StringMetric metric =
                                StringMetricBuilder.with(new CosineSimilarity<>())
                                        .simplify(Simplifiers.toLowerCase(Locale.ENGLISH))
                                        .simplify(Simplifiers.replaceNonWord())
                                        .tokenize(Tokenizers.whitespace())
                                        .build();
                        for (CategoryResponse categoryResponse : categories) {
                            String categoryResponseName = categoryResponse.getName();
                            Long categoryResponseId = categoryResponse.getId();
                            if (StringUtils.isNotEmpty(categoryResponseName) && categoryResponseId != null) {
                                String nonAccentCategoryRequestName = VNCharacterUtil.removeAccent(categoryRequest.getCategoryName());
                                String nonAccentCategoryResponseName = VNCharacterUtil.removeAccent(categoryResponse.getName());
                                float score = metric.compare(nonAccentCategoryRequestName, nonAccentCategoryResponseName);
                                if (score > 0.5) {
                                    categoryResponse.setRequestId(categoryRequest.getCategoryId());
                                    categoryResponse.setSupplier(Supplier.SENDO);
                                    amqpTemplate.convertAndSend(ExchangeName.SC_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_RESPONSE_MAIN,
                                            QueueName.SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN, categoryResponse);
                                }
                            }
                        }
                    }
                } else {
                    log.info("Category collector: request {} response code {}", categoryRequest, statusCode);
                }
            }
        } catch (Exception e) {
            log.error("Sendo supplier: Unexpected exception {}", categoryRequest, e);
            if (categoryRequest.getConsumerAttempts() < maxAttempts) {
                log.info("Sending the category request to retry queue {}", categoryRequest);
                amqpTemplate.convertAndSend(Supplier.SENDO.getCategoryRequestRetryExchange(),
                        Supplier.SENDO.getCategoryRequestRetryQueue(), categoryRequest);
            } else {
                throw new AmqpRejectAndDontRequeueException("Exceeded maximum attempts, parking the category request. " + categoryRequest, e);
            }
        }
    }
}
