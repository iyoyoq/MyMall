package com.server.business.auth.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 管理员表(AuthAdmin)表实体类
 *
 * @author localyjy
 * @since 2025-04-13 17:40:44
 */
@Data
@TableName("auth_admin")
public class Admin {

    private Long id;
    // 昵称
    private String nickName;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

