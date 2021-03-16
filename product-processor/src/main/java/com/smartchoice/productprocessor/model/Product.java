package com.smartchoice.productprocessor.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.util.VNCharacterUtil;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String searchableName;

    @Column(columnDefinition = "TEXT")
    private String icon;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.UTC);

    @OneToMany(targetEntity = ProductDetail.class, cascade = {
            CascadeType.ALL}, orphanRemoval = true, mappedBy = "product")
    private Set<ProductDetail> productDetails = new HashSet<>();

    public Product() {
    }

    public Product(ProductResponse productResponse, Category category) {
        this.name = productResponse.getProductName();
        this.icon = productResponse.getImage();
        this.category = category;
        this.searchableName = VNCharacterUtil.generateSearchableText(productResponse.getProductName());
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Set<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Set<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public void addProductDetail(ProductDetail productDetail) {
        this.productDetails.add(productDetail);
    }

    public void addProductDetails(Set<ProductDetail> productDetails) {
        this.productDetails.clear();
        if (productDetails != null) {
            this.productDetails.addAll(productDetails);
        }
    }

    public String getSearchableName() {
        return searchableName;
    }

    public void setSearchableName(String searchableName) {
        this.searchableName = searchableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
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
                .append("icon", icon)
                .append("category", category)
                .append("updatedTime", updatedTime)
                .append("createdTime", createdTime)
                .append("productDetails", productDetails)
                .append("searchableName", searchableName)
                .toString();
    }
}
