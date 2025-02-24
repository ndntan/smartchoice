package com.smartchoice.productprocessor.services.product.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;
import com.smartchoice.common.util.VNCharacterUtil;
import com.smartchoice.productprocessor.model.Category;
import com.smartchoice.productprocessor.model.Product;
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.self-config.max-attempts}")
    private Long maxAttempts;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(ProductResponse productResponse) {
        try {
            log.info("Processing a product response {}", productResponse);
            productResponse.increaseAttempts();
            ProductDetail existingProductDetail = productDetailService.find(productResponse.getProductId(), productResponse.getSupplier());
            if (existingProductDetail != null) {
                log.info("Found a product (product id {} supplier {}) so updating the stats to latest {}", productResponse.getProductId(), productResponse.getSupplier(), existingProductDetail);
                existingProductDetail.update(productResponse);
            } else {
                log.info("Not found the product (product id {} supplier {} product name {}). Using trigrams to search the most similar product",
                        productResponse.getProductId(), productResponse.getSupplier(), productResponse.getProductName());
                Product existingProduct = productService.findWithTrigramsAlgorithm(VNCharacterUtil.generateSearchableText(productResponse.getProductName()), 0.8);
                if (existingProduct != null) {
                    log.info("Trigram found the product (product id {} supplier {} product name {}). Detail {}", productResponse.getProductId(), productResponse.getSupplier(), productResponse.getProductName(), existingProduct);
                    Set<ProductDetail> existingProductDetails = existingProduct.getProductDetails();
                    if (CollectionUtils.isNotEmpty(existingProductDetails) && !containProductDetailOfSupplier(existingProductDetails, productResponse.getSupplier())) {
                        log.info("Searching supply category {}", productResponse.getExternalCategoryId());
                        SupplyCategory existingSupplyCategory = supplyCategoryService.findByExternalId(productResponse.getExternalCategoryId());
                        if (existingSupplyCategory != null) {
                            log.info("Found a supply category {}", existingSupplyCategory);
                            ProductDetail newProductDetail = new ProductDetail(existingProduct, existingSupplyCategory);
                            newProductDetail.update(productResponse);
                            if (!existingProductDetails.contains(newProductDetail)) {
                                log.info("Created a new product detail {}", newProductDetail);
                                existingProduct.addProductDetail(newProductDetail);
                            }
                        }
                    }
                } else {
                    log.info("Trigram NOT found the product (product id {} supplier {} product name {}). Starting verifying to create new product", productResponse.getProductId(), productResponse.getSupplier(), productResponse.getProductName());
                    Category existingCategory = categoryService.findById(productResponse.getCategoryId());
                    if (existingCategory != null) {
                        log.info("Found an existing category {}", existingCategory);
                        SupplyCategory existingSupplyCategory = supplyCategoryService.findByExternalId(productResponse.getExternalCategoryId());
                        if (existingSupplyCategory != null) {
                            log.info("Found an existing supply category {}", existingSupplyCategory);
                            Set<ProductDetail> productDetails = new HashSet<>();
                            Product newProduct = new Product(productResponse, existingCategory);
                            ProductDetail newProductDetail = new ProductDetail(newProduct, existingSupplyCategory);
                            newProductDetail.update(productResponse);
                            productDetails.add(newProductDetail);
                            newProduct.setProductDetails(productDetails);
                            productService.save(newProduct);
                            log.info("Created a new product {}", newProduct);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception occurs when processing the product response {}", productResponse, e);
            if (productResponse.getConsumerAttempts() < maxAttempts) {
                log.info("Sending the product response to retry queue {}", productResponse);
                amqpTemplate.convertAndSend(ExchangeName.SC_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_RETRY,
                        QueueName.SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_RETRY, productResponse);
            } else {
                throw new AmqpRejectAndDontRequeueException("Exceeded maximum attempts, parking the product response. " + productResponse, e);
            }
        }
    }

    private boolean containProductDetailOfSupplier(Set<ProductDetail> productDetails, Supplier supplier) {
        if (CollectionUtils.isNotEmpty(productDetails)) {
            for (ProductDetail productDetail : productDetails) {
                if (supplier.equals(productDetail.getSupplier())) {
                    return true;
                }
            }
        }

        return false;
    }
}
