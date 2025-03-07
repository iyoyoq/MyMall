package com.server.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.entity.ProductInfo;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.pojo.dto.ProductInfoQueryConditionDto;
import com.server.pojo.dto.ProductInfoUpdateDto;
import com.server.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/21 10:05
 */
@RestController
@RequestMapping("/admin/product")
public class AdminProductController {


    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 分页条件查询
     */
    @PostMapping
    public R page(@RequestBody ProductInfoQueryConditionDto dto) {
        Page<ProductInfo> result = productInfoService.queryList(dto);
        return R.ok(result);
    }

    /**
     * 修改商品状态
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductInfoUpdateDto dto) {
        ProductInfo insert  =  ProductInfo.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .build();
        boolean result = productInfoService.updateById(insert); // 调用 update 方法
        if (result) {
            return R.ok();
        }
        throw new BusinessException("操作失败");
    }

    /**
     * 商品细节
     */
    @GetMapping("/detail")
    public R detail(Long id) {
        ProductInfo optById = productInfoService.getById(id);
        return R.ok(optById);
    }

}
