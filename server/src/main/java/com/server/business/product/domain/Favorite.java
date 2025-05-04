package com.server.business.product.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户收藏表(Favorite)表实体类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
@Data
@TableName("product_favorite")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Favorite {
    // 收藏ID
    private Long id;
    // 用户ID，关联auth_user表
    private Long userId;
    // 商品ID，关联商品表
    private Long productId;
    // 0取消收藏 1收藏
    private Integer status;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 商品信息
    @TableField(exist = false)
    private Product product;
}

