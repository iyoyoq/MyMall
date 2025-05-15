package com.server.business.product.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品模块-购物车表(ProductCart)表实体类
 *
 * @author localyjy
 * @since 2025-05-12 10:15:10
 */
@Data
@TableName("product_cart")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductCart {

    private Long id;

    private Long userId;

    private Long skuId;

    private String skuCode;

    // 购买数量
    private Integer purchaseQuantity;

    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

