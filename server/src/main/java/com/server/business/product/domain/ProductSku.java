package com.server.business.product.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 商品SKU表(ProductSku)表实体类
 *
 * @author localyjy
 * @since 2025-04-21 14:07:29
 */
@Data
public class ProductSku {
    // SKU ID
    private Long id;
    // 所属商品ID
    private Long productId;
    // SKU属性值, 逗号分隔
    private String skuAttrValues;
    // SKU编号
    private String code;
    // SKU名称
    private String name;
    // 销售价格/分
    private Integer price;
    // 库存数量
    private Integer stockQuantity;
    // 锁定库存（待付款订单）
    private Integer lockedStock;
    // 销量
    private Integer salesCount;
    // 状态(-1删除  0 下架 1上架)
    private Integer status;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

