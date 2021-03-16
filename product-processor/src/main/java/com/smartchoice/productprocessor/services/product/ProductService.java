package com.smartchoice.productprocessor.services.product;

import java.util.List;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.productprocessor.model.Product;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    void delete(Long id);

    Product findById(Long id);

    void notifySupplier(ProductRequest productRequest);

    Product findWithTrigramsAlgorithm(String searchText, Double threshold);

    List<Long> findManyWithTrigramsAlgorithm(String fullSearchText, Double threshold);

    List<Product> search(String text);
}
