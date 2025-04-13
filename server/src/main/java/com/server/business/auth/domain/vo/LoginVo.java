package com.server.business.auth.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 登录结果, 以及存在 redis中的数据
 * @Author: yjy
 * @Date: 2025/3/1 19:25
 */
@Data
public class LoginVo {
    private String token;
    // 用户唯一标识
    private Long id;
    // token 创建时间
    private LocalDateTime tokenCreatTime = LocalDateTime.now();

    public LoginVo() {
    }

    public LoginVo(String token, Long id) {
        this.token = token;
        this.id = id;
    }
}
