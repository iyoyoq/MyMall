package com.server.business.auth.domain.dto;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/7 10:17
 */
@Data
public class AddressCreateDTO {
    // 地址ID
    // private Long id;
    // 用户ID，关联auth_user表
    private Long userId;
    // 收货人姓名
    private String receiverName;
    // 收货人手机号
    private String receiverPhone;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区/县
    private String district;
    // 详细地址
    private String detailAddress;
    // 是否默认地址（0否 1是）
    // private Integer isDefault;
    // 状态（1正常 -1删除）
    // private Integer status;
    // 创建时间
    // private LocalDateTime createTime;
    // 更新时间
    // private LocalDateTime updateTime;
}
