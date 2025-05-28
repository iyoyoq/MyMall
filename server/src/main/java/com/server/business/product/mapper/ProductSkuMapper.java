package com.server.business.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.vo.ProductSkuDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/21 14:08
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    List<ProductSkuDetailVo> getDetailBySkuIds(@Param("ids") List<Long> ids);

    List<Product> selectProductListBySkuIds(Set<Long> skuIds);
}
