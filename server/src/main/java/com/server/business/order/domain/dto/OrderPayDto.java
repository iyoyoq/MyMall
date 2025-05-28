package com.server.business.order.domain.dto;

import lombok.Data;

/**
 * @Description: 订单支付参数
 * @Author: yjy
 * @Date: 2025/5/24 18:15
 */
@Data
public class OrderPayDto {

    private Long id;
    private Long addressId;
    private String userRemark;  //用户备注
    private Integer payMethod; // 支付方式（ 10积分 20微信 30支付宝 100银行卡）

}
