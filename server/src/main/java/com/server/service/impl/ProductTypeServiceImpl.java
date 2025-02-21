package com.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.entity.ProductType;
import com.server.mapper.ProductTypeMapper;
import com.server.service.ProductTypeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Override
    public List<ProductType> allNormal() {
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1");  // 正常启用的
        return list(queryWrapper);
    }
}
