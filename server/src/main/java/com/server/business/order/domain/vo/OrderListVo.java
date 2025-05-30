package com.server.business.order.domain.vo;


import com.server.pojo.RPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 订单列表 VO
 * @Author: yjy
 * @Date: 2025/5/28 12:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderListVo {

    private RPage<OrderDetailVo> page;

}
