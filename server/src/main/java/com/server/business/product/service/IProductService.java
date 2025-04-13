package com.server.business.product.service;

import com.server.business.product.domain.Product;
import com.server.business.product.domain.dto.ProductListQueryDto;
import com.server.pojo.RPage;

public interface IProductService {
    RPage<Product> selectPage(Integer pageNum, Integer pageSize, ProductListQueryDto dto);
}
