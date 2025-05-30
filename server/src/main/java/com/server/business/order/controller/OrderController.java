package com.server.business.order.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.order.domain.dto.OrderCreateDto;
import com.server.business.order.domain.dto.OrderPayDto;
import com.server.business.order.domain.vo.OrderDetailVo;
import com.server.business.order.domain.vo.OrderListVo;
import com.server.business.order.service.IOrderService;
import com.server.pojo.R;
import com.server.util.RequestContext;
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

    @Autowired
    private RequestContext requestContext;

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
     * 支去付，此时需要保存订单信息，支付成功需要更新订单状态
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

    /**
     * 查看订单列表
     */
    @GetMapping("/list")
    @CheckLogin(allowRole = {LoginType.USER, LoginType.ADMIN})
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "status", required = false) Integer status   // 订单状态（10待支付 20待发货 30待收货 40已完成 （保留50已评价） 5已取消）
            ) {
        if (requestContext.isUser()){
            userId = requestContext.getUser().getId();
        }
        OrderListVo vo = orderService.list(pageNum, pageSize, userId, status);
        return R.ok(vo);
    }

}
