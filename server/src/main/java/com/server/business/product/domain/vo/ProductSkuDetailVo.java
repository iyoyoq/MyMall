package com.server.business.product.domain.vo;

import com.server.business.product.domain.ProductSku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: Sku 细节信息
 * @Author: yjy
 * @Date: 2025/5/14 16:19
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSkuDetailVo extends ProductSku {
    // 商品名称
    private String productName;

    // 商品主图 url
    private String mainImage;
}
