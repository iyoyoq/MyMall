package com.server.business.auth.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.dto.AddressCreateDTO;
import com.server.business.auth.service.IAddressService;
import com.server.pojo.R;
import com.server.util.RequestContext;
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

    @Autowired
    private RequestContext requestContext;


    /**
     * 分页查询：查自己的地址
     */
    @PostMapping("/list")
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody Address address) {
        address.setUserId(requestContext.getUser().getId());
        Page<Address> result = addressService.selectPage(pageNum, pageSize, address);
        return R.ok(result);
    }

    /**
     * 单条查询
     */
    @GetMapping("/detail")
    public R detail(Long id) {
        Address address = addressService.selectById(id);
        return R.ok(address);
    }


    /**
     * 增
     */
    @PostMapping("/save")
    public R create(@RequestBody AddressCreateDTO dto) {
        int ok = addressService.insert(dto);
        return R.judge(ok > 0, "保存失败");
    }

    /**
     * 删
     */
    @DeleteMapping("/remove")
    public R remove(Long id) {
        int result = addressService.removeById(id);
        return R.judge(result > 0, "删除失败");
    }

    /**
     * 改
     */
    @PutMapping("/update")
    public R update(Address address) {
        int b = addressService.updateById(address);
        return R.judge(b > 0, "");
    }


}
