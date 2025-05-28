package com.server.business.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单明细表(OrderDetail)表实体类
 *
 * @author localyjy
 * @since 2025-05-19 11:00:48
 */
@Data
@TableName("order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetail {
    // 订单明细ID
    private Long id;
    // 订单编号，关联order表
    private String orderCode;
    // 商品ID, 关联product_product表
    private Long productId;
    // 商品快照id, 关联product_snapshot
    private Long productSnapshotId;
    // SKU ID，关联product_sku表
    private Long skuId;
    // 单价（下单时的价格/分）
    private Integer price;
    // 购买数量
    private Integer quantity;
    // 运费（分）
    private Integer shippingFee;
    // 总金额（单价 * 数量）+ 1*运费
    private Integer totalPrice;


}

