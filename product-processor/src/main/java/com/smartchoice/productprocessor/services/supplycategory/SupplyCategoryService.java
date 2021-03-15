package com.smartchoice.productprocessor.services.supplycategory;

import java.util.List;

import com.smartchoice.productprocessor.dto.SupplyCategoryInfo;
import com.smartchoice.productprocessor.model.Product;
import com.smartchoice.productprocessor.model.SupplyCategory;

public interface SupplyCategoryService {

    List<SupplyCategory> findAll();

    SupplyCategory save(SupplyCategory supplyCategory);

    void delete(Long id);

    SupplyCategory findById(Long id);

    SupplyCategory findByExternalId(Long id);

}
