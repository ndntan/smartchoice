package com.smartchoice.productprocessor.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.smartchoice.common.model.Supplier;

@Entity
public class SupplyCategory implements Serializable {

    public static final String EXTERNAL_CATEGORY_ID = "externalCategoryId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;

    private LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.UTC);

    private LocalDateTime updatedTime;

    private Long externalCategoryId;

    @Enumerated(EnumType.STRING)
    private Supplier supplier;

    @ManyToOne(cascade= CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

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

    public Long getExternalCategoryId() {
        return externalCategoryId;
    }

    public void setExternalCategoryId(Long supplyCategoryId) {
        this.externalCategoryId = supplyCategoryId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyCategory category = (SupplyCategory) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("createdTime", createdTime)
                .append("updatedTime", updatedTime)
                .append("externalCategoryId", externalCategoryId)
                .append("supplier", supplier)
                .append("category", category)
                .toString();
    }
}
