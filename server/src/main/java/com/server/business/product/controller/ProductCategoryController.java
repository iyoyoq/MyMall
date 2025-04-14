package com.server.business.product.controller;

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

    @GetMapping("/list")
    public R list() {
        List<ProductCategory> result = categoryService.list();
        return R.ok(result);
    }

}
