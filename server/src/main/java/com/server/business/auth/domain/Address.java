package com.server.business.auth.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户地址表(Address)表实体类
 *
 * @author localyjy
 * @since 2025-03-06 16:36:54
 */
@Data
@TableName("auth_address")
public class Address {
    // 地址ID
    private Long id;
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
    private Integer isDefault;
    // 状态（1正常 -1删除）
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;


}

