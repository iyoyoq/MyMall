package com.server.business.auth.domain.dto;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/7 11:14
 */
@Data
public class UserLoginDto {
    private String phone;
    // private String password;
    private String code;
}
