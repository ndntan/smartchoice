package com.smartchoice.productprocessor.repository.supplycategory.impl;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.smartchoice.productprocessor.model.SupplyCategory;
import com.smartchoice.productprocessor.repository.supplycategory.SupplyCategoryRepositoryCustom;

@Repository
public class SupplyCategoryRepositoryImpl implements SupplyCategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SupplyCategory findByExternalId(Long id) {
        Objects.requireNonNull(id);
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SupplyCategory> criteriaQuery = criteriaBuilder.createQuery(SupplyCategory.class);

        Root<SupplyCategory> root = criteriaQuery.from(SupplyCategory.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(SupplyCategory.EXTERNAL_CATEGORY_ID), id));
        Query<SupplyCategory> query = session.createQuery(criteriaQuery);
        return query.uniqueResult();
    }
}