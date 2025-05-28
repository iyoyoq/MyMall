package com.server.business.order.domain.vo;

import com.server.business.auth.domain.Address;
import com.server.business.order.domain.Order;
import com.server.business.order.domain.OrderDetail;
import com.server.business.product.domain.vo.ProductSkuDetailVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/21 19:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailVo {

    private Order order;

    private List<OrderDetailExtendInfo> orderDetailList;

    private Address address;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class OrderDetailExtendInfo{
        private OrderDetail orderDetail;
        private ProductSkuDetailVo sku;
    }
}
