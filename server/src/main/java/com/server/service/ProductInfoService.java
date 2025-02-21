package com.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.server.entity.ProductInfo;
import com.server.model.dto.ProductInfoCreateDto;
import com.server.model.dto.ProductInfoQueryConditionDto;

/**
 * 物品表(M3ProductInfo)表服务接口
 *
 * @author localyjy
 * @since 2024-12-11 18:00:21
 */
public interface ProductInfoService extends IService<ProductInfo> {

    void createProductInfo(ProductInfoCreateDto dto);

    Page<ProductInfo> queryList(ProductInfoQueryConditionDto dto);
}

