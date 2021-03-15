package com.smartchoice.productprocessor.repository.productdetail;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smartchoice.productprocessor.model.ProductDetail;

@Repository
public interface ProductDetailRepository extends CrudRepository<ProductDetail, Long>, ProductDetailRepositoryCustom {
}
