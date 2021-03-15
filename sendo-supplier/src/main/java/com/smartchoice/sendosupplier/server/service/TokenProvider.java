package com.smartchoice.sendosupplier.server.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.smartchoice.common.model.gson.SCGson;
import com.smartchoice.sendosupplier.server.dto.SendoTokenRequest;
import com.smartchoice.sendosupplier.server.dto.SendoTokenResult;

@Service
public class TokenProvider {

    private static final Logger log = LogManager.getLogger(TokenProvider.class);

    @Value("${sendo.api.endpoint}")
    private String sendoApiEndpoint;

    @Value("${sendo.api.auth.shop_key}")
    private String shopKey;

    @Value("${sendo.api.auth.secret_key}")
    private String secretKey;

    private CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(10)
            .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build())
            .build();

    private Gson gson = SCGson.getGson();

    Cache<String, SendoTokenResult> tokenCache;

    @PostConstruct
    public void start() {
        tokenCache = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build();
    }

    public SendoTokenResult generateToken() throws URISyntaxException, IOException {
        log.info("Generating a token from shop key and secret key");
        // construct the request
        URIBuilder builder = new URIBuilder(sendoApiEndpoint + "/login");
        HttpPost httpPost = new HttpPost(builder.build());
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("shop_key", shopKey);
        jsonObject.addProperty("secret_key", secretKey);
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                SendoTokenRequest sendoTokenRequest = gson.fromJson(responseBody, SendoTokenRequest.class);
                return sendoTokenRequest.getResult();
            } else {
                log.error("Sendo supplier: could not request a token. Status code {}", statusCode);
                throw new IOException("Sendo supplier: could not request a token. Status code " + statusCode);
            }
        }
    }

    public String obtainSendoToken() throws IOException, URISyntaxException {
        if (tokenCache != null) {
            SendoTokenResult existingToken = tokenCache.getIfPresent("token");
            if (existingToken != null) {
                return existingToken.getToken();
            }
        }
        SendoTokenResult sendoTokenResult = generateToken();
        tokenCache.put("token", sendoTokenResult);
        return sendoTokenResult.getToken();
    }
}
