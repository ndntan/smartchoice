package com.smartchoice.productprocessor.services.productdetail;

import java.util.List;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.Product;
import com.smartchoice.productprocessor.model.ProductDetail;

public interface ProductDetailService {

    List<ProductDetail> findAll();

    ProductDetail save(ProductDetail productDetail);

    void delete(Long id);

    ProductDetail findById(Long id);

    ProductDetail find(Long externalId, Supplier supplier);
}
