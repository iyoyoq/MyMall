package com.server.business.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Product;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.service.IProductService;
import com.server.pojo.RPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:37
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public RPage<Product> selectPage(Integer pageNum, Integer pageSize, Product dto) {

        Page<Product> productPage = productMapper.selectProductPage(new Page<>(pageNum, pageSize), dto);
        RPage<Product> result = new RPage<>(productPage);

        return result;
    }

    @Override
    public int insert(Product dto) {
        return productMapper.insert(dto);
    }

    @Override
    public int updateById(Product dto) {
        return productMapper.updateById(dto);
    }
}
