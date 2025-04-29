package com.server.business.product.service;

import com.server.business.product.domain.Product;
import com.server.pojo.RPage;

public interface IProductService {
    RPage<Product> selectPage(Integer pageNum, Integer pageSize, Product dto);

    int insert(Product dto);

    Product getById(Long id);

    int updateById(Product dto);

    int logicDelete(Long productId);
}
