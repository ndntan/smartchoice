package com.smartchoice.productprocessor.services.category.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.dto.CategoryInfo;
import com.smartchoice.productprocessor.model.Category;
import com.smartchoice.productprocessor.repository.category.CategoryRepository;
import com.smartchoice.productprocessor.services.category.CategoryService;
import com.smartchoice.productprocessor.services.executor.AfterCommitExecutor;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AfterCommitExecutor afterCommitExecutor;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<CategoryInfo> findAll() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();

        List<CategoryInfo> createdCategoriesDTOS = new ArrayList<>();
        categories.forEach((createdCategory) -> {
            createdCategoriesDTOS.add(new CategoryInfo(createdCategory));
        });
        return createdCategoriesDTOS;
    }

    @Override
    public CategoryInfo save(CategoryInfo categoryInfo) {
        Category category = categoryRepository.save(categoryInfo.toCategory());

        CategoryInfo createdCategoryInfo = new CategoryInfo(category);
        afterCommitExecutor.execute(() -> notifySupplier(Collections.singletonList(createdCategoryInfo)));
        return createdCategoryInfo;
    }

    @Override
    public List<CategoryInfo> save(List<CategoryInfo> categoryInfos) {
        if (CollectionUtils.isEmpty(categoryInfos)) {
            return Collections.EMPTY_LIST;
        }

        List<Category> categories = new ArrayList<>();
        for (CategoryInfo categoryInfo : categoryInfos) {
            categories.add(categoryInfo.toCategory());
        }

        List<CategoryInfo> createdCategoriesDTOS = new ArrayList<>();
        Iterable<Category> createdCategories = categoryRepository.saveAll(categories);
        createdCategories.forEach((createdCategory) -> {
            createdCategoriesDTOS.add(new CategoryInfo(createdCategory));
        });

        afterCommitExecutor.execute(() -> notifySupplier(createdCategoriesDTOS));
        return createdCategoriesDTOS;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    private void notifySupplier(List<CategoryInfo> categoryInfos) {
        if (CollectionUtils.isNotEmpty(categoryInfos)) {
            categoryInfos.forEach((categoryDTO -> {
                List<Supplier> suppliers = Arrays.asList(Supplier.values());
                suppliers.forEach((supplier -> {
                    if (supplier.isExternal()) {
                        amqpTemplate.convertAndSend(supplier.getCategoryRequestMainExchange(),
                                supplier.getCategoryRequestMainQueue(), categoryDTO.toCategoryRequest());
                    }
                }));
            }));
        }
    }
}
