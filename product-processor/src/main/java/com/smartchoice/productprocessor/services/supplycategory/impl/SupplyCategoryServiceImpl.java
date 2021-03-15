package com.smartchoice.productprocessor.services.supplycategory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.productprocessor.dto.SupplyCategoryInfo;
import com.smartchoice.productprocessor.model.SupplyCategory;
import com.smartchoice.productprocessor.repository.supplycategory.SupplyCategoryRepository;
import com.smartchoice.productprocessor.services.supplycategory.SupplyCategoryService;

@Service
@Transactional
public class SupplyCategoryServiceImpl implements SupplyCategoryService {

    @Autowired
    private SupplyCategoryRepository supplyCategoryRepository;

    @Override
    public SupplyCategoryInfo save(SupplyCategory supplyCategory) {
        SupplyCategory createdSupplyCategory = supplyCategoryRepository.save(supplyCategory);

        return new SupplyCategoryInfo(createdSupplyCategory);
    }
}
