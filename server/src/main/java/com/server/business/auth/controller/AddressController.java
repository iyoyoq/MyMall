package com.server.business.auth.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.dto.AddressCreateDto;
import com.server.business.auth.service.IAddressService;
import com.server.pojo.R;
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 用户地址增删改查
 * @Author: yjy
 * @Date: 2025/3/6 17:02
 */
@RestController
@RequestMapping("/auth/address")
public class AddressController {
    @Autowired
    private IAddressService addressService;

    @Autowired
    private RequestContext requestContext;

    /**
     * 查自己的地址: 不分页, 最多查一百条就行
     */
    @PostMapping("/list")
    @CheckLogin(allowRole = {LoginType.USER})
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody Address address) {
        address.setUserId(requestContext.getUser().getId());
        RPage<Address> result = addressService.selectPage(1, 100, address);
        return R.ok(result);
    }



    /**
     * 增
     */
    @PostMapping("/save")
    @CheckLogin(allowRole = {LoginType.USER})
    public R create(@RequestBody AddressCreateDto dto) {
        int ok = addressService.insert(dto);
        return R.judge(ok > 0, "保存失败");
    }

    /**
     * 删
     */
    @DeleteMapping("/remove")
    @CheckLogin(allowRole = {LoginType.USER})
    public R remove(@RequestBody List<Long> idList) {
        int result = addressService.removeById(idList);
        return R.judge(result > 0, "删除失败");
    }

    /**
     * 改
     */
    @PutMapping("/update")
    @CheckLogin(allowRole = {LoginType.USER})
    public R update(@RequestBody Address address) {
        int b = addressService.updateById(address);
        return R.judge(b > 0,   "");
    }


}
