package com.smartchoice.productprocessor.services.category;

import java.util.List;

import com.smartchoice.productprocessor.dto.CategoryInfo;
import com.smartchoice.productprocessor.model.Category;

public interface CategoryService {

    List<CategoryInfo> findAll();

    CategoryInfo save(CategoryInfo category);

    List<CategoryInfo> save(List<CategoryInfo> categoryInfos);

    void delete(Long id);

    Category findById(Long id);
}
