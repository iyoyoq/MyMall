package com.server.controller;


import com.server.entity.User;
import com.server.model.R;
import com.server.service.UserService;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取自己的个人信息
     */
    @GetMapping("/host/info")
    public R<User> getUserInfo() {
        User user = userService.getUserInfo(RequestContext.getUserId());
        user.setPassword("");
        return R.ok(user);
    }

    /**
     * 获取用户信息 ：暂时废弃
     */
 /*    @GetMapping("/getUserInfo/byId")
    public R<User> getUserInfo(Long userId) {
        User user = userService.getUserInfo(userId);
        user.setPassword("");
        return R.ok(user);
    } */


}
