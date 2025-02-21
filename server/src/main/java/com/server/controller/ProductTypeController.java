package com.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.server.entity.ProductType;
import com.server.model.R;
import com.server.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/9 13:22
 */
@RestController
@RequestMapping("/product/type")
@SaCheckLogin
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 获取所有启用的商品分类
     */
    @GetMapping()
    public R<List<ProductType>> getAllTypeList() {
        List<ProductType> list = productTypeService.allNormal();  //allNormal：所有正常状态的
        return R.ok(list);
    }

}
