package com.smartchoice.productprocessor.repository.productdetail;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.ProductDetail;

@Service
public class ProductDetailRepositoryImpl implements ProductDetailRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductDetail find(Long externalId, Supplier supplier) {
        Objects.requireNonNull(externalId);
        Objects.requireNonNull(supplier);
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ProductDetail> criteriaQuery = criteriaBuilder.createQuery(ProductDetail.class);

        Root<ProductDetail> root = criteriaQuery.from(ProductDetail.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(ProductDetail.EXTERNAL_ID), externalId),
                criteriaBuilder.equal(root.get(ProductDetail.SUPPLIER), supplier)));
        Query<ProductDetail> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}

