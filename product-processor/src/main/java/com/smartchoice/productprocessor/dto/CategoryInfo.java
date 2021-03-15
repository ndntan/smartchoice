package com.smartchoice.productprocessor.dto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.smartchoice.common.dto.CategoryRequest;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.Category;

public class CategoryInfo {

    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<String> productPrimaryKeySearch;

    public CategoryInfo() {
    }

    public CategoryInfo(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.productPrimaryKeySearch = category.getProductPrimaryKeySearch();
        this.createdTime = category.getCreatedTime();
        this.updatedTime = category.getUpdatedTime();
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<String> getProductPrimaryKeySearch() {
        return productPrimaryKeySearch;
    }

    public void setProductPrimaryKeySearch(List<String> productPrimaryKeySearch) {
        this.productPrimaryKeySearch = productPrimaryKeySearch;
    }

    public Category toCategory(){
        Category category = new Category();
        category.setName(this.name);
        category.setProductPrimaryKeySearch(this.productPrimaryKeySearch);
        category.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        category.setUpdatedTime(LocalDateTime.now(ZoneOffset.UTC));

        return category;
    }

    public CategoryRequest toCategoryRequest(){
        return new CategoryRequest(this.id, this.name);
    }
}
