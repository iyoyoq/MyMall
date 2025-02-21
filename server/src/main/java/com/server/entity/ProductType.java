package com.server.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * (ProductType)表实体类
 *
 * @author localyjy
 * @since 2024-12-09 13:38:29
 */
@Data
public class ProductType {
    // 类型标识
    private Long id;
    // 类型代码
    private String typeCode;
    // 类型名称
    private String typeName;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    // 更新时间
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;
    // 状态 0禁用 1启用 2逻辑删除
    private Integer status;


}

