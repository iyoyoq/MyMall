package com.server.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.server.entity.Admin;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 管理端 权限相关
 * @Author: yjy
 * @Date: 2024/12/18 23:44
 */
@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public R Login(@RequestBody Admin admin) {
        Admin dbo = adminService.getOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));
        if (dbo == null || !dbo.getPassword().equals(admin.getPassword())) {
            throw new BusinessException("用户名或密码错误！");
        }
        // StpAdminUtil.login(dbo.getId());
        return R.ok();
    }


}
