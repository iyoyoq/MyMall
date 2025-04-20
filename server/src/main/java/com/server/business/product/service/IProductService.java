package com.server.business.product.service;

import com.server.business.product.domain.Product;
import com.server.pojo.RPage;

public interface IProductService {
    RPage<Product> selectPage(Integer pageNum, Integer pageSize, Product dto);

    int insert(Product dto);

    int updateById(Product dto);
}
