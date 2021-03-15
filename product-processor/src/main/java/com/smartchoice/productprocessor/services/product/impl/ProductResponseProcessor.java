package com.smartchoice.productprocessor.services.product.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.dto.CategoryResponse;
import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.productprocessor.model.Category;
import com.smartchoice.productprocessor.model.ProductDetail;
import com.smartchoice.productprocessor.model.SupplyCategory;
import com.smartchoice.productprocessor.services.category.CategoryService;
import com.smartchoice.productprocessor.services.product.ProductService;
import com.smartchoice.productprocessor.services.productdetail.ProductDetailService;
import com.smartchoice.productprocessor.services.supplycategory.SupplyCategoryService;

@Component
public class ProductResponseProcessor {

    private static final Logger log = LogManager.getLogger(ProductResponseProcessor.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplyCategoryService supplyCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(ProductResponse productResponse) {
        log.info("Processing a product response {}", productResponse);

        ProductDetail productDetail = productDetailService.find(productResponse.getProductId(), productResponse.getSupplier());
        if (productDetail != null) {
            productDetail.setUpdatedTime(LocalDateTime.now(ZoneOffset.UTC));
            productDetail.setPrice(productResponse.getPrice());
            productDetail.setDescription(productResponse.getShortDescription());
            productDetail.setDiscount(productResponse.getDiscount());
            productDetail.setDiscountRate(productResponse.getDiscountRate());
            productDetail.setIcon(productResponse.getImage());
            productDetail.setOriginalName(productResponse.getProductName());
            productDetail.setProductPath(productResponse.getProductPath());
        } else {

        }

//        Long requestId = productResponse.getRequestId();
//        Category category = categoryService.findById(requestId);
//        if (category == null) {
//            log.warn("Category processor not found a category id {}", requestId);
//            return;
//        }
//
//        SupplyCategory supplyCategory = new SupplyCategory();
//        supplyCategory.setCategory(category);
//        supplyCategory.setName(categoryResponse.getName());
//        supplyCategory.setSupplier(categoryResponse.getSupplier());
//        supplyCategory.setExternalCategoryId(categoryResponse.getId());
//
//        supplyCategoryService.save(supplyCategory);
//        log.info("Processed the category response {} and got a supply category {}", categoryResponse, supplyCategory);
//
//        List<String> productPrimaryKeySearch = category.getProductPrimaryKeySearch();
//        productPrimaryKeySearch.forEach((search -> {
//            ProductRequest productRequest = new ProductRequest(supplyCategory.getExternalCategoryId(), category.getId(), search);
//            log.info("Starting notifying the product consumer {}", productRequest);
//            productService.notifySupplier(productRequest);
//        }));
    }
}
