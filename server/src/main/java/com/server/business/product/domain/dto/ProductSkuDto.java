package com.server.business.product.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.server.business.product.domain.ProductSku;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 商品 sku 增加和修改的 传输对象
 * @Author: yjy
 * @Date: 2025/4/21 14:16
 */
@Data
public class ProductSkuDto {

    // 商品 id
    private Long productId;

    // sku 属性名
    private List<String> skuAttrNameList;

    // sku 列表
    private List<SingleSku> skuList;


    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    public static class SingleSku extends ProductSku {
        // sku 属性列表
        private List<String> skuAttrValueList;

        public SingleSku(Integer price, Integer stockQuantity, List<String> skuAttrValueList){
            this.setPrice(price);
            this.setStockQuantity(stockQuantity);
            this.setSkuAttrValues(String.join(",", skuAttrValueList));
            this.skuAttrValueList = skuAttrValueList;
        }
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

    /**
     * 把原始数据库 逗号隔开的数据  进行转义为 List skuAttrValueList
     * @param productSkuList 原始数据
     */
    public void setSkuListByProductSkuList(List<ProductSku> productSkuList){
        List<SingleSku> s = new ArrayList<>();
        for (ProductSku productSku : productSkuList) {
            SingleSku singleSku = BeanUtil.copyProperties(productSku, SingleSku.class);
            singleSku.setSkuAttrValueList(List.of(productSku.getSkuAttrValues().split(",")));
            s.add(singleSku);
        }
        this.skuList = s;
    }

}
