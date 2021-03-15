package com.smartchoice.productprocessor.repository.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smartchoice.productprocessor.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, ProductRepositoryCustom {
}
