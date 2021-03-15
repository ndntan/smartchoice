package com.smartchoice.productprocessor.repository.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.smartchoice.productprocessor.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}