package com.server.business.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商品快照表(ProductSnapshot)表实体类
 *
 * @author localyjy
 * @since 2025-04-22 10:45:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductSnapshot {
    // 快照id
    private Long id;
    // 所属商品id
    private Long productId;
    // 商品信息json
    private String productInfoJson;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

}

