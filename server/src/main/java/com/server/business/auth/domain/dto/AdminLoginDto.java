package com.server.business.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 管理员登录
 * @Author: yjy
 * @Date: 2025/4/19 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminLoginDto {
    private String userName;
    private String password;
}
