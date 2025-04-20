package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.product.domain.Product;
import com.server.business.product.service.IProductService;
import com.server.pojo.R;
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:36
 */

@RestController
@RequestMapping("/product/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private RequestContext requestContext;

    /**
     * 获取商品详情
     */
    @PostMapping("/list")
    @CheckLogin
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody Product dto) {
        if (LoginType.USER.equals(requestContext.getUser().getLoginType())) {
            dto.setStatus(1);  // 用户只能查看已上架的物品
        }
        RPage<Product> result = productService.selectPage(pageNum, pageSize, dto);
        return R.ok(result);
    }

    /**
     * 增
     */
    @CheckLogin(allowRole = LoginType.ADMIN)
    @PostMapping("/save")
    public R create(@RequestBody Product dto) {
        int result = productService.insert(dto);
        return R.ok();
    }


    /**
     * 改
     */
    @PostMapping("/update")
    @CheckLogin(allowRole = LoginType.ADMIN)
    public R update(@RequestBody Product dto) {
        int result = productService.updateById(dto);
        return R.ok();
    }


}
