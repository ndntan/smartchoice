package com.smartchoice.productprocessor.repository.product;

import com.smartchoice.productprocessor.model.Product;

public interface ProductRepositoryCustom {
    Product findWithLevenshteinAlgorithm(String productNameSearch, Long threshold);
}
