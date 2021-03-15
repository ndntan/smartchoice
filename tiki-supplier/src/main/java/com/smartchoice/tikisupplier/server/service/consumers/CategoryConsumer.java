package com.smartchoice.tikisupplier.server.service.consumers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartchoice.common.dto.CategoryRequest;
import com.smartchoice.tikisupplier.server.service.collectors.CategoryCollector;

@Component
public class CategoryConsumer {

    private static final Logger log = LogManager.getLogger(CategoryConsumer.class);

    @Autowired
    private CategoryCollector categoryCollector;

    public void consume(CategoryRequest categoryRequest) {
        log.info("Received a category request {}", categoryRequest);
        categoryCollector.collect(categoryRequest);
    }
}
