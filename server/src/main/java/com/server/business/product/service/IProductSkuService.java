package com.server.business.product.service;

import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.domain.vo.ProductSkuVo;

public interface IProductSkuService {
    ProductSkuVo getByProductId(Long productId);

    int insert(ProductSkuDto dto);

    int updateById(ProductSkuDto dto);
}
