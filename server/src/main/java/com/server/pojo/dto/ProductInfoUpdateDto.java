package com.server.pojo.dto;

import lombok.Data;

/**
 * @Description: 更新商品详情
 * @Author: yjy
 * @Date: 2025/1/2 10:19
 */
@Data
public class ProductInfoUpdateDto {
    private Long id;
    // 状态 1 待审核 2 审核失败 9 上线 12 卖出
    private Integer status;
}
