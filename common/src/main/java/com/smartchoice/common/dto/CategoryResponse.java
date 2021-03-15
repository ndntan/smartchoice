package com.smartchoice.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.smartchoice.common.model.Supplier;

public class CategoryResponse {

    private Long id;

    private String name;

    private Supplier supplier;

    private Long requestId;

    private long consumerAttempts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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
                .append("id", id)
                .append("name", name)
                .append("supplier", supplier)
                .append("requestId", requestId)
                .append("consumerAttempts", consumerAttempts)
                .toString();
    }
}
