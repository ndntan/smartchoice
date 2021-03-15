package com.smartchoice.productprocessor.dto;

import java.time.LocalDateTime;

import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.SupplyCategory;

public class SupplyCategoryInfo {

    private Long id;
    private String name;
    private Supplier supplier;
    private LocalDateTime updatedTime;
    private LocalDateTime createdTime;
    private CategoryInfo categoryInfo;

    public SupplyCategoryInfo() {
    }

    public SupplyCategoryInfo(SupplyCategory supplyCategory) {
        this.id = supplyCategory.getId();
        this.name = supplyCategory.getName();
        this.supplier = supplyCategory.getSupplier();
        this.updatedTime = supplyCategory.getUpdatedTime();
        this.createdTime = supplyCategory.getCreatedTime();
        this.categoryInfo = new CategoryInfo(supplyCategory.getCategory());
    }

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

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
