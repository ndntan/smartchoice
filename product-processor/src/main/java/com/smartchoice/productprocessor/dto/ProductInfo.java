package com.smartchoice.productprocessor.dto;

import com.smartchoice.productprocessor.model.Product;

public class ProductInfo {
    private String name;
    private String description;
    private String icon;
    private Double price;

    public ProductInfo(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.icon = product.getIcon();
        this.price = product.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setIcon(this.icon);
        product.setPrice(this.price);

        return product;
    }

}
