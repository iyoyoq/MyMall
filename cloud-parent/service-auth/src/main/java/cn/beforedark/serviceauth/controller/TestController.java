package cn.beforedark.serviceauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/4 15:34
 */
@RequestMapping
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

}
