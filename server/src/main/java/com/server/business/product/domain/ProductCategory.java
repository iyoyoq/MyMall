package com.server.business.product.domain;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品分类表(ProductCategory)表实体类
 *
 * @author localyjy
 * @since 2025-04-06 20:59:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_category")
public class ProductCategory {
    // ID
    @TableId
    private Long id;
    // 父分类id, -1代表顶层
    private Long parentId;
    // 分类名称
    private String name;
    // 状态(-1删除 0下架 1上架)
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    // 返给前端的字段
    @TableField(exist = false)
    private List<ProductCategory> children;
}

