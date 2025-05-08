package com.server.business.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:38
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    Page<Product> selectProductPage(@Param("page") Page<Object> page,
                                    @Param("dto") Product dto,
                                    @Param("allSonCategoryId") Set<Long> allSonCategoryId
    );
}
