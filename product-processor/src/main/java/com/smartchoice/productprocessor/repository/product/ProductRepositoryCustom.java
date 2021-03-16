package com.smartchoice.productprocessor.repository.product;

import java.util.List;

public interface ProductRepositoryCustom {
    Long findWithTrigramsAlgorithm(String productNameSearch, Double threshold);

    List<Long> findManyWithTrigramsAlgorithm(String fullSearchText, Double threshold);
}
