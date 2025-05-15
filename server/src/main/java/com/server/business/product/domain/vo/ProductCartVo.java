package com.server.business.product.domain.vo;

import com.server.pojo.RPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/14 15:30
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartVo {

    private RPage<ProductSkuDetailVo> page;


}
