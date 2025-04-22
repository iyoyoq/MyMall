package com.server.business.product.service;

import com.server.business.product.domain.dto.ProductSkuDto;

import java.util.List;

public interface IProductSkuService {
    ProductSkuDto getByProductId(Long productId, List<Integer> status);

    int insertOrUpdate(ProductSkuDto dto);

}
