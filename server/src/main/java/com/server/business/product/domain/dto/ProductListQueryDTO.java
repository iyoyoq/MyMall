package com.server.business.product.domain.dto;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:42
 */
@Data
public class ProductListQueryDTO {
    private String name;  // 商品名称

    private Long categoryId;   // 分类 id

}
