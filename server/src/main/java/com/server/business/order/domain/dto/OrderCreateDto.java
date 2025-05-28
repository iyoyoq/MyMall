package com.server.business.order.domain.dto;

import lombok.Data;

import java.util.Map;

/**
 * @Description: 创建订单dto
 * @Author: yjy
 * @Date: 2025/5/19 11:04
 */
@Data
public class OrderCreateDto {

    private Long userId;

    // k:skuId, v:购买数目
    private Map<Long, Integer> skuIdAndQuantity;
}
