package com.smartchoice.productprocessor.repository.product.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
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

    @Override
    public List<Long> findManyWithTrigramsAlgorithm(String fullSearchText, Double threshold) {
        Objects.requireNonNull(fullSearchText);
        Objects.requireNonNull(threshold);
        Query query = entityManager.createNativeQuery("SELECT id FROM product WHERE SIMILARITY(searchable_name, :productNameSearch) >= :threshold ORDER BY SIMILARITY(searchable_name, :productNameSearch);");
        query.setParameter("productNameSearch", fullSearchText);
        query.setParameter("threshold", threshold);
        List resultList = query.getResultList();
        List<Long> convertedResults = new ArrayList<>();
        if (CollectionUtils.isEmpty(resultList)) {
            return convertedResults;
        }

        for (Object object : resultList) {
            BigInteger val = (BigInteger) object;
            convertedResults.add(val.longValue());
        }

        return convertedResults;
    }
}

