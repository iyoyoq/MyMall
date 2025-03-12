package com.server.business.auth.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户收藏表(Favorite)表实体类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
@Data
public class Favorite {
    // 收藏ID
    private Long id;
    // 用户ID，关联auth_user表
    private Long userId;
    // 商品ID，关联商品表
    private Long productId;
    // 创建时间
    private Date createTime;


}

