package com.smartchoice.sendosupplier.server.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;

public class SendoProductResponseData {

    private Long id;

    private String name;

    private Long promotion_price;

    private String image;

    private String link;

    public SendoProductResponseData() {
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

    public Long getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(Long promotion_price) {
        this.promotion_price = promotion_price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductResponse toProductResponse(ProductRequest productRequest) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPrice(this.promotion_price);
        productResponse.setProductName(this.name);
        productResponse.setProductId(this.id);
        productResponse.setImage(this.image);
        productResponse.setProductPath(this.link);
        productResponse.setCategoryId(productRequest.getCategoryId());
        productResponse.setExternalCategoryId(productRequest.getExternalCategoryId());
        productResponse.setSupplier(Supplier.SENDO);

        return productResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("promotion_price", promotion_price)
                .append("image", image)
                .append("link", link)
                .toString();
    }
}
