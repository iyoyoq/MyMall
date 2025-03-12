package com.server.business.auth.controller.user;


import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.User;
import com.server.business.auth.service.IUserService;
import com.server.pojo.R;
import com.server.util.RequestContext;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户信息
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
    @GetMapping("/detail")
    public R detail(Long id) {
        User user = userService.selectById(id);
        return R.ok(user);
    }

    /**
     * 自己的 User 信息
     */
    @GetMapping("/detail/self")
    public R detail() {
        User user = requestContext.getUser();
        return R.ok(user);
    }

}
