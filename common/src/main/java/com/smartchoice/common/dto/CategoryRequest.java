package com.smartchoice.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.simmetrics.metrics.Levenshtein;

public class CategoryRequest {

    private Long categoryId;

    private String categoryName;

    private long consumerAttempts;

    public CategoryRequest(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
                .append("categoryId", categoryId)
                .append("categoryName", categoryName)
                .append("consumerAttempts", consumerAttempts)
                .toString();
    }
}
