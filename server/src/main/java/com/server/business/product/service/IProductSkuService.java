package com.server.business.product.service;

import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.dto.ProductSkuDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IProductSkuService {

    /**
     * 获取sku详情
     * @param productId  productId
     * @param status 1上架 -1下架
     * @return vo
     */
    ProductSkuDto getByProductId(Long productId, List<Integer> status);

    int insertOrUpdate(ProductSkuDto dto);


    /**
     * key  为 skuId , value 为 sku
     */
    Map<Long, ProductSku> getProductSkuBySkuIds(Set<Long> skuIds);

    /**
     * key  为 productId , value 为 product
     */
    Map<Long, Product> getProductBySkuIds(Set<Long> skuIds);

    /**
     * 扣减库存
     */
    void deductStock(Map<Long, Integer> skuIdAndQuantity);
}
