package com.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.entity.ProductInfo;
import com.server.mapper.ProductInfoMapper;
import com.server.pojo.dto.ProductInfoCreateDto;
import com.server.pojo.dto.ProductInfoQueryConditionDto;
import com.server.service.ProductInfoService;
import com.server.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 物品表(M3ProductInfo)表服务实现类
 *
 * @author localyjy
 * @since 2024-12-11 18:00:22
 */
@Service
@Slf4j
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Override
    public void createProductInfo(ProductInfoCreateDto dto) {
        ProductInfo dbo = new ProductInfo();
        BeanUtil.copyProperties(dto, dbo, "image", "price", "originalPrice");
        dbo.setImage(dto.getImageAsJson());
        dbo.setPrice((long) (dto.getPrice() * 100));
        dbo.setOriginalPrice((long) (dto.getOriginalPrice() * 100));
        dbo.setUserId(RequestContext.getUserId());
        dbo.setLikeCount(0);
        dbo.setStatus(1); // 待审核
        save(dbo);
    }

    public Page<ProductInfo> queryList(ProductInfoQueryConditionDto dto) {
        Page<ProductInfo> page = new Page<>(dto.getCurrent(), dto.getSize());

        // 使用 QueryWrapper 构建动态条件
        QueryWrapper<ProductInfo> wrapper = dto.toQueryWrapper();

        return baseMapper.selectPage(page, wrapper);
    }




}

