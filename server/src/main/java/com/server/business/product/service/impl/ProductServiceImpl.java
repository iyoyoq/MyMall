package com.server.business.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.dto.ProductListQueryDto;
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
    public RPage<Product> selectPage(Integer pageNum, Integer pageSize, ProductListQueryDto dto) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dto.getName()), Product::getName, dto.getName());
        wrapper.eq(dto.getCategoryId() != null, Product::getCategoryId, dto.getCategoryId());
        wrapper.eq(Product::getStatus, 1); // 已上架
        Page<Product> productPage = productMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        RPage<Product> result = new RPage<>(productPage);
        return result;
    }
}
