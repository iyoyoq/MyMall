package com.server.business.pay.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 支付模块-支付表-绑定订单主表(PayPay)表实体类
 *
 * @author localyjy
 * @since 2025-05-25 13:58:39
 */
@Data
@TableName("pay_pay")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class Pay {
    // 支付ID
    private Long id;
    // 支付者唯一ID，关联auth_user表
    private Long payUserId;
    // 支付金额（单位：分）
    private Integer amount;
    // 支付方式（ 10积分 20微信 30支付宝 100银行卡）
    private Integer payMethod;
    // 支付状态（10未支付 20已支付 30支付失败）
    private Integer payStatus;
    // 支付时间
    private LocalDateTime payTime;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

