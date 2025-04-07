package com.server.business.product.domain;

import java.util.Date;
import lombok.Data;

/**
 * 商品分类表(ProductCategory)表实体类
 *
 * @author localyjy
 * @since 2025-04-06 20:59:23
 */
@Data
public class ProductCategory {
    // ID
    private Long id;
    // 父分类id
    private Long parentId;
    // 分类名称
    private String name;
    // 状态(-1删除 0下架 1上架)
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


}

