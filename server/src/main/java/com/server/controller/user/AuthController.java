package com.server.controller.user;

import com.server.business.auth.service.IUserService;
import com.server.model.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 权限校验相关
 * @Author: yjy
 * @Date: 2024/12/7 11:11
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    /**
     * 手机短信验证码登录
     */
    // @PostMapping("/user/codeLogin")
    // public R<SaTokenInfo> login(@RequestBody UserLoginDto dto) {
    //     SaTokenInfo loginToken = userService.login(dto);
    //     return R.ok(loginToken);
    // }

    /**
     * 获取手机验证码
     */
    @GetMapping("/user/sendLoginCode")
    public R sendCode(String phone) {
        userService.sendLoginCode(phone);
        return R.ok();
    }
}
