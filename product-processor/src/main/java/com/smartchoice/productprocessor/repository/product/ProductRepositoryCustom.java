package com.smartchoice.productprocessor.repository.product;

public interface ProductRepositoryCustom {
    Long findWithTrigramsAlgorithm(String productNameSearch, Double threshold);
}
