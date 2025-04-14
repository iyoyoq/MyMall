package com.server.business.auth.controller;

import com.server.business.auth.domain.dto.UserLoginDto;
import com.server.business.auth.domain.vo.LoginVo;
import com.server.business.auth.service.IUserService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/1 19:12
 */
@RestController
@RequestMapping("/auth/user/login")
public class LoginController {

    @Autowired
    private IUserService userService;


    /**
     * 手机短信验证码登录
     */
    @PostMapping("/codeLogin")
    public R<LoginVo> login(@RequestBody UserLoginDto dto) {
        LoginVo vo = userService.login(dto);
        return R.ok(vo);
    }

    /**
     * 获取手机验证码
     */
    @GetMapping("/sendLoginCode")
    public R<String> sendCode(String phone) {
        userService.sendLoginCode(phone);
        return R.ok("验证码发送成功");
    }


}
