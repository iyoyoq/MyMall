package com.server.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.entity.ProductInfo;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.pojo.dto.ProductInfoCreateDto;
import com.server.pojo.dto.ProductInfoQueryConditionDto;
import com.server.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/11 18:04
 */
@RestController
@RequestMapping("/product/info")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 上传商品
     */
    @PostMapping
    public R createProductInfo(@RequestBody ProductInfoCreateDto dto) {
        productInfoService.createProductInfo(dto);
        return R.ok();
    }


    /**
     * 分页条件查询
     */
    @PostMapping("/page")
    public R page(@RequestBody ProductInfoQueryConditionDto dto) {
        dto.setStatus(9); // 只选审核成功的
        Page<ProductInfo> result = productInfoService.queryList(dto);
        return R.ok(result);
    }

    /**
     * 查看商品细节
     */
    @GetMapping("/detail")
    public R detail(Long id) {
        ProductInfo r = productInfoService.getById(id);
        if (r == null) {
            throw new BusinessException("商品不存在");
        }
        return R.ok(r);
    }
}
