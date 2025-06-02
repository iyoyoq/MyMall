package com.server.business.order.service;

import com.server.business.order.domain.dto.OrderCreateDto;
import com.server.business.order.domain.dto.OrderDeliveryDto;
import com.server.business.order.domain.dto.OrderPayDto;
import com.server.business.order.domain.dto.OrderReceiveDto;
import com.server.business.order.domain.vo.OrderDetailVo;
import com.server.business.order.domain.vo.OrderListVo;
import com.server.business.pay.domain.Pay;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/19 10:54
 */
public interface IOrderService {
    Long  save(OrderCreateDto dto);

    OrderDetailVo getById(Long orderId);


    Pay goPay(OrderPayDto dto);

    OrderListVo list(Integer pageNum, Integer pageSize, Long userId, Integer status);

    void delivery(OrderDeliveryDto dto);

    void receive(OrderReceiveDto dto);

    void scanAndCancelOrder();
}
