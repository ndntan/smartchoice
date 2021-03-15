package com.smartchoice.productprocessor.services.category.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.productprocessor.model.Category;
import com.smartchoice.productprocessor.model.SupplyCategory;
import com.smartchoice.productprocessor.services.category.CategoryService;
import com.smartchoice.productprocessor.services.product.ProductService;
import com.smartchoice.productprocessor.services.supplycategory.SupplyCategoryService;

@Component
public class CategoryResponseProcessor {

    private static final Logger log = LogManager.getLogger(CategoryResponseProcessor.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplyCategoryService supplyCategoryService;

    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(CategoryResponse categoryResponse) {
        log.info("Processing a category response {}", categoryResponse);
        Long requestId = categoryResponse.getRequestId();
        Category category = categoryService.findById(requestId);
        if (category == null) {
            log.warn("Category processor not found a category id {}", requestId);
            return;
        }

        SupplyCategory supplyCategory = new SupplyCategory();
        supplyCategory.setCategory(category);
        supplyCategory.setName(categoryResponse.getName());
        supplyCategory.setSupplier(categoryResponse.getSupplier());
        supplyCategory.setExternalCategoryId(categoryResponse.getId());

        supplyCategoryService.save(supplyCategory);
        log.info("Processed the category response {} and got a supply category {}", categoryResponse, supplyCategory);

        List<String> productPrimaryKeySearch = category.getProductPrimaryKeySearch();
        productPrimaryKeySearch.forEach((search -> {
            ProductRequest productRequest = new ProductRequest(supplyCategory.getExternalCategoryId(), category.getId(), search);
            log.info("Starting notifying the product consumer {}", productRequest);
            productService.notifySupplier(productRequest);
        }));
    }
}
