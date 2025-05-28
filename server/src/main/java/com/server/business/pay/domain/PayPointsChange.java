package com.server.business.pay.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户支付积分流水表(PayUserPayPoints)表实体类
 *
 * @author localyjy
 * @since 2025-05-25 13:22:46
 */
@Data
@TableName("pay_points_change")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PayPointsChange {
    // 唯一约束
    private Long id;
    // 用户id
    private Long userId;
    // 改变量，正负分别表示增减
    private Integer valueChange;
    // 改变原因枚举，1后台管理手动改变,  2金钱支付反馈
    private Integer reason;
    // 流水创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

