package com.smartchoice.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProductRequest {

    private Long externalCategoryId;

    private Long categoryId;

    private String productPrimaryKeySearch;

    private long consumerAttempts;

    public ProductRequest(Long externalCategoryId, Long categoryId, String productPrimaryKeySearch) {
        this.externalCategoryId = externalCategoryId;
        this.categoryId = categoryId;
        this.productPrimaryKeySearch = productPrimaryKeySearch;
    }

    public Long getExternalCategoryId() {
        return externalCategoryId;
    }

    public void setExternalCategoryId(Long externalCategoryId) {
        this.externalCategoryId = externalCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductPrimaryKeySearch() {
        return productPrimaryKeySearch;
    }

    public void setProductPrimaryKeySearch(String productPrimaryKeySearch) {
        this.productPrimaryKeySearch = productPrimaryKeySearch;
    }

    public long getConsumerAttempts() {
        return consumerAttempts;
    }

    public void setConsumerAttempts(long consumerAttempts) {
        this.consumerAttempts = consumerAttempts;
    }

    public void increaseAttempts() {
        this.consumerAttempts++;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("externalCategoryId", externalCategoryId)
                .append("categoryId", categoryId)
                .append("productPrimaryKeySearch", productPrimaryKeySearch)
                .append("consumerAttempts", consumerAttempts)
                .toString();
    }
}
