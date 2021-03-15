package com.smartchoice.tikisupplier.server.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;

public class TikiProductResponseData {

    private Long id;

    private String name;

    private Long price;

    private Long discount;

    private Long discount_rate;

    private String thumbnail_url;

    private String short_description;

    private String url_path;

    public TikiProductResponseData() {
    }

    public TikiProductResponseData(Long id, String name, Long price, String thumbnail_url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail_url = thumbnail_url;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Long discount_rate) {
        this.discount_rate = discount_rate;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getUrl_path() {
        return url_path;
    }

    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }

    public ProductResponse toProductResponse(ProductRequest productRequest, String productServer) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPrice(this.price);
        productResponse.setProductName(this.name);
        productResponse.setProductId(this.id);
        productResponse.setImage(this.thumbnail_url);
        productResponse.setDiscount(this.discount);
        productResponse.setDiscountRate(this.discount_rate);
        productResponse.setShortDescription(this.short_description);
        productResponse.setProductPath(productServer + "/" + this.url_path);
        productResponse.setCategoryId(productRequest.getCategoryId());
        productResponse.setExternalCategoryId(productRequest.getExternalCategoryId());
        productResponse.setSupplier(Supplier.TIKI);

        return productResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("price", price)
                .append("thumbnail", thumbnail_url)
                .toString();
    }
}
