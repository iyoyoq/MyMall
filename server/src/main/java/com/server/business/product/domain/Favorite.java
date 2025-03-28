package com.server.business.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户收藏表(Favorite)表实体类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
@Data
@TableName("product_favorite")
public class Favorite {
    // 收藏ID
    private Long id;
    // 用户ID，关联auth_user表
    private Long userId;
    // 商品ID，关联商品表
    private Long productId;

    private Integer status;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}

