package cn.beforedark.common.model.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/8/7 23:23
 */
@Data
public class LoginVO {
    private String token;
    // 用户唯一标识
    private Long id;
    // token 创建时间
    private LocalDateTime tokenCreatTime = LocalDateTime.now();
    // 登录类型
    private LoginType loginType;

    public LoginVO() {
    }

    public LoginVO(String token, Long id, LoginType loginType) {
        this.token = token;
        this.id = id;
        this.loginType = loginType;
    }
}
