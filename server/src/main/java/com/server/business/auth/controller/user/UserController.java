package com.server.business.auth.controller.user;


import com.server.aop.CheckLogin;
import com.server.business.auth.domain.User;
import com.server.business.auth.service.IUserService;
import com.server.pojo.R;
import com.server.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.server.aop.LoginType.USER;

/**
 * @Description: 用户信息  接口地址规范 -> 模块名/user或admin/表名/self或other
 * @Author: yjy
 * @Date: 2025/3/11 21:13
 */
@RestController
@RequestMapping("/auth/user/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RequestContext requestContext;

    /**
     * 单条查询
     */
    @GetMapping("/other/detail")
    public R detail(Long id) {
        User user = userService.selectById(id);
        return R.ok(user);
    }

    /**
     * 自己的 User 信息
     */
    @CheckLogin(allowRole = {USER})
    @GetMapping("/self/detail")
    public R detail() {
        Long id = requestContext.getUser().getId();
        User user = userService.selectById(id);
        return R.ok(user);
    }

    /**
     * 改自己的 User 信息
     */
    @PutMapping("/self/update")
    public R update(@RequestBody User user) {
        int b = userService.updateSelfById(user);
        return R.judge(b > 0, "");
    }

}
