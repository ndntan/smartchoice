package com.smartchoice.productprocessor.services.product.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.Product;
import com.smartchoice.productprocessor.model.ProductDetail;
import com.smartchoice.productprocessor.repository.product.ProductRepository;
import com.smartchoice.productprocessor.services.product.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product save(Product product)
    {
       return productRepository.save(product);
    }

    @Override
    public void delete(Long id)
    {
       productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void notifySupplier(ProductRequest productRequest) {
        Objects.requireNonNull(productRequest);
        List<Supplier> suppliers = Arrays.asList(Supplier.values());
        suppliers.forEach((supplier -> {
            if (supplier.isExternal()) {
                amqpTemplate.convertAndSend(supplier.getProductRequestMainExchange(),
                        supplier.getProductRequestMainQueue(), productRequest);
                log.info("Notified {} product consumer {}", supplier, productRequest);
            }
        }));
    }

    @Override
    public Product findWithTrigramsAlgorithm(String searchText, Double threshold) {
        Long existingId = productRepository.findWithTrigramsAlgorithm(searchText, threshold);
        if (existingId == null) {
            return null;
        }
        return productRepository.findById(existingId).orElse(null);
    }

    @Override
    public List<Long> findManyWithTrigramsAlgorithm(String fullSearchText, Double threshold) {
        return productRepository.findManyWithTrigramsAlgorithm(fullSearchText, threshold);
    }

    @Override
    public List<Product> search(String text) {
        List<Product> result = new ArrayList<>();
        List<Long> foundIds = productRepository.findManyWithTrigramsAlgorithm(text, 0.1);
        if (CollectionUtils.isNotEmpty(foundIds)) {
            Iterable<Product> existingProducts = productRepository.findAllById(foundIds);
            existingProducts.forEach((product) -> {
                Set<ProductDetail> productDetails = product.getProductDetails();
                if (CollectionUtils.isNotEmpty(productDetails) && productDetails.size() > 1) {
                    result.add(product);
                }
            });
        }

        return result;
    }
}
