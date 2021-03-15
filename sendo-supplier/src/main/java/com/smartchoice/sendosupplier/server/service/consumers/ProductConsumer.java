package com.smartchoice.sendosupplier.server.service.consumers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.sendosupplier.server.service.collectors.ProductCollector;

@Component
public class ProductConsumer {

    private static final Logger log = LogManager.getLogger(ProductConsumer.class);

    @Autowired
    private ProductCollector productCollector;

    public void consume(ProductRequest productRequest) {
        log.info("Received a product request {}", productRequest);
        productCollector.collect(productRequest);
    }
}
