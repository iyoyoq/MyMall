package com.server.business.auth.controller.user;


import com.server.business.auth.service.IUserService;
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
    @Resource
    private IUserService userServiceImpl;


    /**
     * 单条查询
     */


}
