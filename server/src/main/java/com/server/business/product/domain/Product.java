package com.server.business.product.domain;

import java.util.Date;
import lombok.Data;

/**
 * 商品表(ProductProduct)表实体类
 *
 * @author localyjy
 * @since 2025-04-06 20:46:35
 */
@Data
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


}

