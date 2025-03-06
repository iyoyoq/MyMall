package com.server.business.auth.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 登录结果
 * @Author: yjy
 * @Date: 2025/3/1 19:25
 */
@Data
public class UserLoginVo {
    private String token;

    // 用户唯一标识
    private Long id;
    // 头像
    private String avatar;
    // 个人简介
    private String intro;
    // 昵称
    private String nickName;
    // 手机号
    private Long phone;
    // // 密码
    // private String password;
    // // 状态 0禁用 1启用
    // private Integer status;
    // 创建时间
    // @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    // 更新时间
    // @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    // 平台编号
    private String number;
    // 待审核昵称
    private String checkNickName;
    // 待审核简介
    private String checkIntro;
    // 待审核头像
    private String checkAvatar;
    // 审核状态 1待审核 2审核失败 3审核成功
    private Integer checkStatus;

}
