package com.server.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 订单自动取消
 * @Author: yjy
 * @Date: 2025/5/22 9:03
 */
@Component
@Slf4j
public class OrderCancelTask {


    // 每隔5秒执行一次
    // @Scheduled(fixedRate = 5000)
    public void task() {
        log.info("订单自动取消任务开始执行");
    }

}
