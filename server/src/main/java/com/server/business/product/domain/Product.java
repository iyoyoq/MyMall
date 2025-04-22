package com.server.business.product.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品表(ProductProduct)表实体类
 *
 * @author localyjy
 * @since 2025-04-06 20:46:35
 */
@Data
@TableName("product_product")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // 商品ID
    private Long id;
    // 当前商品信息快照id
    private Long currentSnapshotId;
    // 商品名称
    private String name;
    // 商品销量
    private Integer salesCount;
    // 分类ID
    private Long categoryId;
    // 商品描述
    private String description;
    // 主图URL
    private String mainImage;
    // 商品图片JSON数组
    private String images;
    // 状态(-1删除 0下架 1上架)
    private Integer status;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // sku 属性名称列表, 逗号分隔
    private String skuAttrNames;

    // 分类名称
    @TableField(exist = false)
    private String categoryName;

}

