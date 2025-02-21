package com.server.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物品表(M3ProductInfo)表实体类
 *
 * @author localyjy
 * @since 2024-12-11 17:12:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    // 商品标识
    private Long id;
    // 发布用户id
    private Long userId;
    // 商品标题
    private String title;
    // 商品描述
    private String intro;
    // 商品图片
    private String image;
    // 商品价格
    private Long price;
    // 商品原价
    private Long originalPrice;
    // 发货方式 0邮寄 1自提
    private Integer postType;
    // 想要的人数
    private Integer likeCount;
    // 地址代码
    private String adcode;
    // 省
    private String province;
    // 市
    private String city;
    // 区
    private String district;
    // 状态 0 删除  1 待审核 2 审核失败 9 上线 12 卖出
    private Integer status;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;


}

