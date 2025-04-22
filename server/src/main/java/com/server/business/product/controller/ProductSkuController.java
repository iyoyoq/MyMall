package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.service.IProductSkuService;
import com.server.pojo.R;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 商品 sku
 * @Author: yjy
 * @Date: 2025/4/21 14:05
 */
@RestController
@RequestMapping("/product/sku")
public class ProductSkuController {

    @Autowired
    private IProductSkuService skuService;

    @Autowired
    private RequestContext requestContext;

    /**
     * 查
     */
    @GetMapping("/detail")
    @CheckLogin
    public R list(@RequestParam("productId") Long productId) {
        List<Integer> status = List.of(1);  // 已上架的 sku
        if (requestContext.isAdmin()) status = List.of(1); // 已上架的 sku

        ProductSkuDto vo = skuService.getByProductId(productId, status);
        return R.ok(vo);
    }

    /**
     * 增
     */
    @PostMapping("/save")
    // @CheckLogin(allowRole = {LoginType.USER})
    public R create(@RequestBody ProductSkuDto dto) {
        int ok = skuService.insertOrUpdate(dto);
        return R.ok();
    }


    /**
     * 改
     */
    @PostMapping("/update")
    @CheckLogin(allowRole = {LoginType.USER})
    public R update(@RequestBody ProductSkuDto dto) {
        int b = skuService.insertOrUpdate(dto);
        return R.ok();
    }


}
