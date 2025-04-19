package com.server.business.product.service;

import com.server.business.product.domain.ProductCategory;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 21:02
 */

public interface IProductCategoryService {
    List<ProductCategory> list();

    int insert(ProductCategory dto);

    int removeById(Long id);

    int updateById(ProductCategory productCategory);
}
