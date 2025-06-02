package com.server.business.order.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 订单主表(OrderOrder)表实体类
 *
 * @author localyjy
 * @since 2025-05-19 11:00:41
 */
@Data
@TableName("order_order")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Order {
    // 订单ID
    private Long id;
    // 用户ID，关联auth_user表
    private Long userId;

    // address_id
    private Long addressId;

    // 用户订单备注
    private String userRemark;
    // 物流单号
    private String logisticsCode;

    // 总运费
    private Integer totalShippingFee;

    // 订单总金额（单位：分）
    private Integer totalAmount;
    // 订单状态（10待支付 20待发货 30待收货 40已完成 50已评价 5已取消）
    private Integer status;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 订单超时自动取消时间
    private LocalDateTime payDdl;

    // 支付id
    private Long payId;

    // 支付时间
    private LocalDateTime payTime;


    // 发货时间
    private LocalDateTime deliveryTime;

    // 收货时间
     private LocalDateTime receiveTime;

    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}

