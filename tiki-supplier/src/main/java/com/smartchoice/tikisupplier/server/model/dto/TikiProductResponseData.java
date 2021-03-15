package com.smartchoice.tikisupplier.server.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.dto.ProductResponse;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.common.util.VNCharacterUtil;

public class TikiProductResponseData {

    private Long id;

    private String name;

    private Long price;

    private Long discount;

    private Long discount_rate;

    private String thumbnail;

    private String short_description;

    private String url_path;

    public TikiProductResponseData() {
    }

    public TikiProductResponseData(Long id, String name, Long price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
        productResponse.setImage(this.thumbnail);
        productResponse.setDiscount(this.discount);
        productResponse.setDiscountRate(this.discount_rate);
        productResponse.setShortDescription(this.short_description);
        productResponse.setProductPath(productServer + "/" + this.url_path);
        productResponse.setCategoryId(productRequest.getCategoryId());
        productResponse.setSupplyCategoryId(productRequest.getExternalCategoryId());
        productResponse.setSupplier(Supplier.TIKI);

        return productResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("price", price)
                .append("thumbnail", thumbnail)
                .toString();
    }

    public static void main(String args[])
    {
        String str= "Điện Thoại iPhone XR 64GB - (Hàng) Chính Hãng VN/A\"";
        String removeAccent = VNCharacterUtil.removeAccent(str);
        removeAccent = removeAccent.replaceAll("[^a-zA-Z0-9-]", " ");
        System.out.println(removeAccent);
    }
}
