package com.smartchoice.productprocessor.services.productdetail.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.ProductDetail;
import com.smartchoice.productprocessor.repository.productdetail.ProductDetailRepository;
import com.smartchoice.productprocessor.services.productdetail.ProductDetailService;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<ProductDetail> findAll()
    {
        return (List<ProductDetail>) productDetailRepository.findAll();
    }

    @Override
    public ProductDetail save(ProductDetail productDetail)
    {
       return productDetailRepository.save(productDetail);
    }

    @Override
    public void delete(Long id)
    {
       productDetailRepository.deleteById(id);
    }

    @Override
    public ProductDetail findById(Long id) {
        return productDetailRepository.findById(id).orElse(null);
    }

    @Override
    public ProductDetail find(Long externalId, Supplier supplier) {
        return productDetailRepository.find(externalId, supplier);
    }
}
