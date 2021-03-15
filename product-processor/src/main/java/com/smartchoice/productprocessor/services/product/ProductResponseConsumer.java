package com.smartchoice.productprocessor.services.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.productprocessor.services.product.impl.ProductResponseProcessor;

@Component
public class ProductResponseConsumer {

    private static final Logger log = LogManager.getLogger(ProductResponseConsumer.class);

    @Autowired
    private ProductResponseProcessor productResponseProcessor;

    public void consume(ProductResponse productResponse) {
        log.info("Received a product response {}", productResponse);
        productResponseProcessor.process(productResponse);
    }
}
