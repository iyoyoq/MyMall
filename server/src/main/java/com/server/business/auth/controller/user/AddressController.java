package com.server.business.auth.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.service.IAddressService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Description: 用户地址增删改查
 * @Author: yjy
 * @Date: 2025/3/6 17:02
 */
@RestController
@RequestMapping("/auth/user/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    /**
     * 分页查询
     */
    @PostMapping("/list")
    public R list(
            Integer current,
            Integer size,
            @RequestBody Address address) {
        Page<Address> page = new Page<>(current, size);
        QueryWrapper<Address> wrapper = new QueryWrapper<>(address);
        Page<Address> result = addressService.page(page, wrapper);
        return R.ok(result);
    }

    /**
     * 单条查询
     */
    @GetMapping("/detail")
    public R detail(Long id) {
        Address address = addressService.getById(id);
        return R.ok(address);
    }


    /**
     * 增
     */
    @PostMapping("/save")
    public R create(@RequestBody Address address) {
        boolean ok = addressService.save(address);
        return R.judge(ok, "保存失败");
    }

    /**
     * 删
     */
    @DeleteMapping("/remove")
    public R remove(Long id) {
        Address address = new Address();
        address.setId(id);
        address.setStatus(-1);
        boolean b = addressService.updateById(address);
        return R.judge(b,"删除失败");
    }

    /**
     * 改
     */
    @PutMapping("/update")
    public R update(Address address) {
        boolean b = addressService.updateById(address);
        return R.judge(b,"");
    }


}
