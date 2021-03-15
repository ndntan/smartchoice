package com.smartchoice.productprocessor.repository.productdetail;

import com.smartchoice.common.model.Supplier;
import com.smartchoice.productprocessor.model.ProductDetail;

public interface ProductDetailRepositoryCustom {
    ProductDetail find(Long externalId, Supplier supplier);
}
