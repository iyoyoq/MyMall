package com.server.business.auth.controller.user;

import com.server.business.auth.domain.vo.UserLoginVo;
import com.server.business.auth.service.IAddressService;
import com.server.business.auth.service.IUserService;
import com.server.model.R;
import com.server.model.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/6 17:02
 */
@RestController
@RequestMapping("/auth/user/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    /**
     * 查
     */
    @GetMapping("/list")
    public R list() {
        return R.ok();
    }

    /**
     * 查
     */
    @GetMapping("/detail")
    public R detail() {
        return R.ok();
    }


    /**
     * 增
     */
    @PostMapping("/create")
    public R create() {
        return R.ok();
    }

    /**
     * 删
     */
    @GetMapping("/remove")
    public R remove( ) {
        return R.ok();
    }

    /**
     * 改
     */
    @GetMapping("/update")
    public R update() {
        return R.ok();
    }




}
