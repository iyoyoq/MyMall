package com.server.business.product.service;

import com.server.business.product.domain.dto.ProductSkuDto;

import java.util.List;

public interface IProductSkuService {

    /**
     * 获取sku详情
     * @param productId  productId
     * @param status 1上架 -1下架
     * @return vo
     */
    ProductSkuDto getByProductId(Long productId, List<Integer> status);

    int insertOrUpdate(ProductSkuDto dto);

}
