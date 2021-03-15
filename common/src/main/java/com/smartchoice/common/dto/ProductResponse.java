package com.smartchoice.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.smartchoice.common.model.Supplier;

public class ProductResponse {

    private Long externalCategoryId;

    private Long categoryId;

    private Supplier supplier;

    private Long productId;

    private String productName;

    private Long price;

    private String image;

    private String productPath;

    private Long discount;

    private Long discountRate;

    private String shortDescription;

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("externalCategoryId", externalCategoryId)
                .append("categoryId", categoryId)
                .append("supplier", supplier)
                .append("productId", productId)
                .append("productName", productName)
                .append("price", price)
                .append("image", image)
                .append("productPath", productPath)
                .append("discount", discount)
                .append("discount_rate", discountRate)
                .append("short_description", shortDescription)
                .toString();
    }
}
