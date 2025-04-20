package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.product.domain.ProductCategory;
import com.server.business.product.service.IProductCategoryService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 21:00
 */
@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService categoryService;

    /**
     * 所有分类
     */
    @GetMapping("/list")
    @CheckLogin
    public R list() {
        List<ProductCategory> result = categoryService.list();
        return R.ok(result);
    }


    /**
     * 增
     */
    @CheckLogin(allowRole = LoginType.ADMIN)
    @PostMapping("/save")
    public R create(@RequestBody ProductCategory dto) {
        int result = categoryService.insert(dto);
        return R.ok();
    }

    /**
     * 删
     */
    @PostMapping("/remove")
    @CheckLogin(allowRole = LoginType.ADMIN)
    public R remove(Long id) {
        int result = categoryService.removeById(id);
        return R.ok();
    }

    /**
     * 改
     */
    @PostMapping("/update")
    @CheckLogin(allowRole = LoginType.ADMIN)
    public R update(@RequestBody ProductCategory productCategory) {
        int result = categoryService.updateById(productCategory);
        return R.ok();
    }

}
