package com.server.business.product.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Favorite;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.dto.ProductListQueryDTO;
import com.server.business.product.service.IProductService;
import com.server.pojo.R;
import com.server.pojo.RPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:36
 */

@RestController
@RequestMapping("/product/user/product")
public class ProductController {

    @Autowired
    private IProductService productService;


    /**
     * 获取商品详情
     */
    @PostMapping("/list")
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody ProductListQueryDTO dto) {
        RPage<Product> result = productService.selectPage(pageNum, pageSize, dto);
        return R.ok(result);
    }

}
