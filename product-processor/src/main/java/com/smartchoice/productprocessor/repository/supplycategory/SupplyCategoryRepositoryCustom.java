package com.smartchoice.productprocessor.repository.supplycategory;

import com.smartchoice.productprocessor.model.SupplyCategory;

public interface SupplyCategoryRepositoryCustom {
    public SupplyCategory findByExternalId(Long id);
}
