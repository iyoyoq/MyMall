package cn.beforedark.serviceauth.controller;

import cn.beforedark.common.model.auth.dto.UserLoginDTO;
import cn.beforedark.common.model.auth.vo.LoginVO;
import cn.beforedark.common.model.response.R;
import cn.beforedark.serviceauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/4 15:34
 */
@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping("/send")
    public String test() {
        return "hello world";
    }

    /**
     * 手机短信验证码登录
     */
    @PostMapping("/codeLogin")
    public R<LoginVO> login(@RequestBody UserLoginDTO dto) {
        LoginVO vo = userService.login(dto);
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
