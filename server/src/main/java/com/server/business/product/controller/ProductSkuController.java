package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.domain.vo.ProductSkuVo;
import com.server.business.product.service.IProductSkuService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查
     */
    @GetMapping("/detail")
    @CheckLogin
    public R list(@RequestParam("productId") Long productId) {
        ProductSkuVo vo = skuService.getByProductId(productId);
        return R.ok(vo);
    }

    /**
     * 增
     */
    @PostMapping("/save")
    // @CheckLogin(allowRole = {LoginType.USER})
    public R create(@RequestBody ProductSkuDto  dto) {
        int ok = skuService.insert(dto);
        return R.judge(ok > 0, "保存失败");
    }


    /**
     * 改
     */
    @PostMapping("/update")
    @CheckLogin(allowRole = {LoginType.USER})
    public R update(@RequestBody ProductSkuDto dto) {
        int b = skuService.updateById(dto);
        return R.judge(b > 0,   "");
    }


}
