package com.server.business.product.service;

import com.server.business.product.domain.ProductCart;
import com.server.business.product.domain.vo.ProductCartVo;

import java.util.List;

public interface IProductCartService {
    int insert(ProductCart dto);


    int remove(List<Long> skuIdList);

    ProductCartVo list(Integer pageNum, Integer pageSize);
}
