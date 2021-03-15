package com.smartchoice.productprocessor.repository.product;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.smartchoice.productprocessor.model.Product;

@Service
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product findWithLevenshteinAlgorithm(String productNameSearch, Long threshold) {
        Objects.requireNonNull(productNameSearch);
        Objects.requireNonNull(threshold);

//        Query q = entityManager.createNativeQuery("SELECT a.id, a.version, a.firstname, a.lastname FROM Author a", Author.class);
        return null;
    }
}

