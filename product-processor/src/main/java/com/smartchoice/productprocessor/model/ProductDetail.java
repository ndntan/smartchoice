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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.smartchoice.common.model.Supplier;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "productdetail_externalid_supplier_uindex", columnNames = {
        "externalId", "supplier" }))
public class ProductDetail implements Serializable {

    public static final String EXTERNAL_ID = "externalId";
    public static final String SUPPLIER = "supplier";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalName;

    private Long externalId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String icon;

    private Long price;

    @ManyToOne(cascade= CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupplyCategory supplyCategory;

    @ManyToOne(cascade= CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private Long discount;

    private Long discountRate;

    @Column(columnDefinition = "TEXT")
    private String productPath;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.UTC);

    @Enumerated(EnumType.STRING)
    private Supplier supplier;

    public ProductDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public SupplyCategory getSupplyCategory() {
        return supplyCategory;
    }

    public void setSupplyCategory(SupplyCategory supplyCategory) {
        this.supplyCategory = supplyCategory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
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

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetail product = (ProductDetail) o;
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
                .append("originalName", originalName)
                .append("description", description)
                .append("icon", icon)
                .append("price", price)
                .append("supplyCategory", supplyCategory)
                .append("product", product)
                .append("discount", discount)
                .append("discountRate", discountRate)
                .append("productPath", productPath)
                .append("updatedTime", updatedTime)
                .append("createdTime", createdTime)
                .append("externalId", externalId)
                .append("supplier", supplier)
                .toString();
    }
}
