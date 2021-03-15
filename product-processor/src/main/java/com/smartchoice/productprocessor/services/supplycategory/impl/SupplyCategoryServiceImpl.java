package com.smartchoice.productprocessor.services.supplycategory.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartchoice.productprocessor.model.SupplyCategory;
import com.smartchoice.productprocessor.repository.supplycategory.SupplyCategoryRepository;
import com.smartchoice.productprocessor.services.supplycategory.SupplyCategoryService;

@Service
@Transactional
public class SupplyCategoryServiceImpl implements SupplyCategoryService {

    @Autowired
    private SupplyCategoryRepository supplyCategoryRepository;

    @Override
    public SupplyCategory save(SupplyCategory supplyCategory) {
        return supplyCategoryRepository.save(supplyCategory);
    }

    @Override
    public List<SupplyCategory> findAll() {
        return (List<SupplyCategory>) supplyCategoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        supplyCategoryRepository.deleteById(id);
    }

    @Override
    public SupplyCategory findById(Long id) {
        return supplyCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public SupplyCategory findByExternalId(Long id) {
        return supplyCategoryRepository.findByExternalId(id);
    }
}
