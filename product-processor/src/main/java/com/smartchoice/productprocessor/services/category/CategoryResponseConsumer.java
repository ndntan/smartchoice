package com.smartchoice.productprocessor.services.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.productprocessor.services.category.impl.CategoryResponseProcessor;

@Component
public class CategoryResponseConsumer {

    private static final Logger log = LogManager.getLogger(CategoryResponseConsumer.class);

    @Autowired
    private CategoryResponseProcessor categoryResponseProcessor;

    public void consume(CategoryResponse categoryResponse) {
        log.info("Received a category response {}", categoryResponse);
        categoryResponseProcessor.process(categoryResponse);
    }
}
