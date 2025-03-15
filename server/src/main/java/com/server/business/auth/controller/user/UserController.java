package com.server.business.auth.controller.user;


import com.server.business.auth.domain.User;
import com.server.business.auth.service.IUserService;
import com.server.pojo.R;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户信息  接口地址规范 -> 模块名/user或admin/表名/self或other
 * @Author: yjy
 * @Date: 2025/3/11 21:13
 */
@RestController
@RequestMapping("/auth/user/user")
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
    @GetMapping("/self/detail")
    public R detail() {
        User user = requestContext.getUser();
        return R.ok(user);
    }

}
