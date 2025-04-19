package com.server.business.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.server.business.product.domain.Favorite;
import com.server.business.product.domain.ProductCategory;
import com.server.business.product.mapper.ProductCategoryMapper;
import com.server.business.product.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 21:02
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;


    @Override
    public List<ProductCategory> list() {
        // 查询所有有效分类（状态为 0 或 1）
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductCategory::getStatus, List.of(0, 1));  // 注意用 Integer 而不是 Long
        List<ProductCategory> list = productCategoryMapper.selectList(wrapper);

        // 建立一个以ID为key的映射，方便快速查找父类
        Map<Long, ProductCategory> map = list.stream()
                .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));

        // 用于存放顶层分类（parentId == -1）
        List<ProductCategory> rootList = new ArrayList<>();

        for (ProductCategory category : list) {
            if (category.getParentId() == -1) {
                rootList.add(category); // 顶层分类
            } else {
                // 找到它的父分类，并把它加到父分类的 children 中
                ProductCategory parent = map.get(category.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(category);
                }
            }
        }

        return rootList;
    }


    @Override
    public int insert(ProductCategory dto) {
        int result = productCategoryMapper.insert(dto);
        return result;
    }

    @Override
    public int removeById(Long id) {
        int result = productCategoryMapper.updateById(new ProductCategory().setId(id).setStatus(1));
        return result;
    }

    @Override
    public int updateById(ProductCategory productCategory) {
        int result = productCategoryMapper.updateById(productCategory);
        return result;
    }
}
