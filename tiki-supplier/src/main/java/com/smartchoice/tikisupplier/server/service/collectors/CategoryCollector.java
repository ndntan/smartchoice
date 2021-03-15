package com.smartchoice.tikisupplier.server.service.collectors;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.simmetrics.metrics.Levenshtein;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.smartchoice.common.dto.CategoryRequest;
import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.common.model.rabbitmq.QueueName;

@Component
public class CategoryCollector {

    private static final Logger log = LogManager.getLogger(CategoryCollector.class);
    private CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(10)
            .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build())
            .build();

    private Gson gson = SCGson.getGson();

    @Value("${tiki.api.category.endpoint}")
    private String tikiApiEndpoint;

    @Value("${tiki.api.category.auth.key}")
    private String tikiApiAuthKey;

    @Value("${tiki.api.category.auth.value}")
    private String tikiApiAuthValue;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void collect(CategoryRequest categoryRequest) {
        log.info("Collecting data for the category request {}", categoryRequest);
        try {
            // construct the request
            URIBuilder builder = new URIBuilder(tikiApiEndpoint + "/integration/categories");

            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setHeader(tikiApiAuthKey, tikiApiAuthValue);
            CloseableHttpResponse response = client.execute(httpGet);
            try {
                String responseBody = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    log.info("Category collector: request {} response {}", categoryRequest, responseBody);
                    List<CategoryResponse> categories = new ArrayList<>(Arrays.asList(gson.fromJson(responseBody, CategoryResponse[].class)));
                    if (CollectionUtils.isNotEmpty(categories)) {
                        Levenshtein algorithm = new Levenshtein();
                        for (CategoryResponse categoryResponse : categories) {
                            String categoryResponseName = categoryResponse.getName();
                            Long categoryResponseId = categoryResponse.getId();
                            if (StringUtils.isNotEmpty(categoryResponseName) && categoryResponseId != null) {
                                float score = algorithm.compare(categoryRequest.getCategoryName().toLowerCase(),
                                        categoryResponse.getName().toLowerCase());
                                if (score > 0.6) {
                                    categoryResponse.setRequestId(categoryRequest.getCategoryId());
                                    categoryResponse.setSupplier(Supplier.TIKI);
                                    amqpTemplate.convertAndSend(QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN, categoryResponse);
                                }
                            }
                        }
                    }
                } else {
                    log.info("Category collector: request {} response code {}", categoryRequest, statusCode);
                }
            } finally {
                response.close();
            }
        } catch (URISyntaxException | IOException e) {
            log.error("Tiki supplier: Failed to contact tiki supplier {}", categoryRequest, e);
        } catch (Exception e) {
            log.error("Tiki supplier: Unexpected exception {}", categoryRequest, e);
        }
    }
}
