package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.product.domain.ProductCart;
import com.server.business.product.domain.vo.ProductCartVo;
import com.server.business.product.service.IProductCartService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 购物车控制器
 * @Date: 2025/5/12 10:19
 * @Author: yjy
 * @Date: 2025/5/12 10:19
 */

@RestController
@RequestMapping("/product/cart")
public class ProductCartController {

    @Autowired
    private IProductCartService categoryService;


    /**
     * 加入购物车
     */
    @CheckLogin(allowRole = LoginType.USER)
    @PostMapping("/add")
    public R create(@RequestBody ProductCart dto) {
        int result = categoryService.insert(dto);
        return R.ok();
    }


    /**
     * 从购物车移除
     */
    @CheckLogin(allowRole = LoginType.USER)
    @PostMapping("/remove")
    public R remove(@RequestBody List<Long> skuIdList) {
        int result = categoryService.remove(skuIdList);
        return R.ok();
    }

    /**
     * 查询购物车
     */
    @CheckLogin(allowRole = LoginType.USER)
    @GetMapping("/list")
    public R list(@RequestParam("pageNum") Integer pageNum,
                  @RequestParam("pageSize") Integer pageSize) {
        ProductCartVo result = categoryService.list(pageNum, pageSize);
        return R.ok(result);
    }

}
