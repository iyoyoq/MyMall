package com.server.business.product.domain;

import java.util.Date;

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
    // 商品名称
    private String name;
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
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    // sku 属性名称列表, 逗号分隔
    private String skuAttrNames;

    // 分类名称
    @TableField(exist = false)
    private String categoryName;

}

