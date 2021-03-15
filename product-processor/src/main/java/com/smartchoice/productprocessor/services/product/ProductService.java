package com.smartchoice.productprocessor.services.product;

import java.util.List;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.productprocessor.model.Product;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    void delete(Integer id);

    Product findById(Integer id);

    void notifySupplier(ProductRequest productRequest);
}
