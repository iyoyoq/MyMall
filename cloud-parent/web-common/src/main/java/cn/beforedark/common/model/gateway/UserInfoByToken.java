package cn.beforedark.common.model.gateway;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/6 14:57
 */
@Data
public class UserInfoByToken {
    private Long userId;
    private String loginType; // "Admin" or "Customer"
    private LocalDateTime tokenCreatTime;
}
