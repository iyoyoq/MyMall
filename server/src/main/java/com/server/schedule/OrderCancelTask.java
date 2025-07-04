package com.server.schedule;

import com.server.business.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 订单自动取消
 * @Author: yjy
 * @Date: 2025/5/22 9:03
 */
@Component
@Slf4j
public class OrderCancelTask {

    @Autowired
    private IOrderService orderService;

    // 每隔60秒执行一次
    // @Scheduled(fixedRate = 60 * 1000)
    public void task() {
         orderService.scanAndCancelOrder();
        // log.info("订单自动取消任务开始执行");
    }

}
