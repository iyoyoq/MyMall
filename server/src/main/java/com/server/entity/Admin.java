package com.server.entity;

import lombok.Data;

/**
 * 后台管理员表(Admin)表实体类
 *
 * @author localyjy
 * @since 2024-12-18 23:52:38
 */
@Data
public class Admin {
    // 唯一标识
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;

    private Long createTime;

    private Long updateTime;


}

