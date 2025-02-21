package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物品表(M3ProductInfo)表数据库访问层
 *
 * @author localyjy
 * @since 2024-12-11 18:00:20
 */
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

}

