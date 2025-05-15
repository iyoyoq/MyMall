package com.server.business.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.vo.ProductSkuDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/21 14:08
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    List<ProductSkuDetailVo> getDetailBySkuIds(List<Long> ids);

}
