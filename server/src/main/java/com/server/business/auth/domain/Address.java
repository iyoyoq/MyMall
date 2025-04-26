package com.server.business.auth.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户地址表(Address)表实体类
 *
 * @author localyjy
 * @since 2025-03-06 16:36:54
 */
@Data
@TableName("auth_address")
@Accessors(chain = true)
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
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //  创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

