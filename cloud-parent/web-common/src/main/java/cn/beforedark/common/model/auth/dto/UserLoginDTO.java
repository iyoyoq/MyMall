package cn.beforedark.common.model.auth.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/8/7 23:26
 */
@Data
public class UserLoginDTO {
    private String phone;
    private String code;
}
