package com.server.business.order.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.order.domain.dto.OrderCreateDto;
import com.server.business.order.domain.dto.OrderPayDto;
import com.server.business.order.domain.vo.OrderDetailVo;
import com.server.business.order.service.IOrderService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/19 10:54
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    @CheckLogin(allowRole = {LoginType.USER})
    public R createOrder(@RequestBody OrderCreateDto dto) {
        Long orderId = orderService.save(dto);
        return R.ok(orderId);
    }

    /**
     * 支去付，此时需要保存订单信息
     */
    @PostMapping("/goPay")
    @CheckLogin(allowRole = {LoginType.USER})
    public R payOrder(@RequestBody OrderPayDto dto) {
        orderService.goPay(dto);
        return R.ok();
    }

    /**
     * 查看订单细节
     */
    @GetMapping("/detail")
    @CheckLogin
    public R detail(@RequestParam("orderId") Long orderId) {
        OrderDetailVo vo = orderService.getById(orderId);
        return R.ok(vo);
    }

}
