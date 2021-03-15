package com.smartchoice.productprocessor.services.product.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.dto.ProductRequest;
import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.Product;
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
    public List<Product> findAll()
    {
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
}
