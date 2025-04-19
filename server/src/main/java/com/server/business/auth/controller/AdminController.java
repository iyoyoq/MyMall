package com.server.business.auth.controller;

import com.server.business.auth.domain.dto.AdminLoginDto;
import com.server.business.auth.domain.vo.LoginVo;
import com.server.business.auth.service.IAdminService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 管理员
 * @Author: yjy
 * @Date: 2025/4/19 9:09
 */
@RestController
@RequestMapping("/auth/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;


    /**
     * 密码登录
     */
    @PostMapping("/passwordLogin")
    public R<LoginVo> login(@RequestBody AdminLoginDto dto) {
        LoginVo vo = adminService.passwordLogin(dto);
        return R.ok(vo);
    }
}
