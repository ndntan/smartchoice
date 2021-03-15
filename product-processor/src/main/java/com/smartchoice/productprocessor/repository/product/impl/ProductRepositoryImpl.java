package com.smartchoice.productprocessor.repository.product.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.smartchoice.productprocessor.repository.product.ProductRepositoryCustom;

@Service
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findWithTrigramsAlgorithm(String productNameSearch, Double threshold) {
        Objects.requireNonNull(productNameSearch);
        Objects.requireNonNull(threshold);

        Query query = entityManager.createNativeQuery("SELECT id FROM product WHERE SIMILARITY(searchable_name, :productNameSearch) >= :threshold ORDER BY SIMILARITY(searchable_name, :productNameSearch) DESC LIMIT 1;");
        query.setParameter("productNameSearch", productNameSearch);
        query.setParameter("threshold", threshold);
        List resultList = query.getResultList();
        if (resultList == null || resultList.size() == 0) {
            return null;
        }

        Object singleResult = resultList.get(0);
        BigInteger val = (BigInteger) singleResult;
        return val.longValue();
    }
}

