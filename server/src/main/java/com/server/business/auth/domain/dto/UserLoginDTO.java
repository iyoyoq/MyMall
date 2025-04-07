package com.server.business.auth.domain.dto;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/7 11:14
 */
@Data
public class UserLoginDTO {
    private String phone;
    // private String password;
    private String code;
}
