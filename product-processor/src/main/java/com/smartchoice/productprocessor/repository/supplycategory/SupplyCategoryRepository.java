package com.smartchoice.productprocessor.repository.supplycategory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smartchoice.productprocessor.model.Category;
import com.smartchoice.productprocessor.model.SupplyCategory;

@Repository
public interface SupplyCategoryRepository extends CrudRepository<SupplyCategory, Integer> {
}