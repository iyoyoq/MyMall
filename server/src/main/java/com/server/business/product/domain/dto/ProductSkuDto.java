package com.server.business.product.domain.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.server.business.product.domain.ProductSku;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @Description: 商品 sku 增加和修改的 传输对象
 * @Author: yjy
 * @Date: 2025/4/21 14:16
 */
@Data
public class ProductSkuDto {
    private Long productId;

    // sku 列表
    private List<SingleSku> skuList;

    // sku 属性名
    private List<String> skuAttrNameList;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class SingleSku extends ProductSku {
        // sku 属性列表
        private List<String> skuAttrValueList;
    }


    public boolean check() {
        if (CollectionUtil.isEmpty(skuList) || CollectionUtil.isEmpty(skuAttrNameList)) {
            return false;
        }
        for (SingleSku sku : skuList) {
            if (sku.getSkuAttrValueList().size() != skuAttrNameList.size()) {
                return false;
            }
        }
        return true;
    }


}
