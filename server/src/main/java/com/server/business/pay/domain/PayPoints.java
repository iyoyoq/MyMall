package com.server.business.pay.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户积分余额表(PayUserPoints)表实体类
 *
 * @author localyjy
 * @since 2025-05-25 14:26:49
 */
@Data
@TableName("pay_points")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PayPoints {
    // 用户id
    @TableId(type = IdType.INPUT) // 自己输入主键
    private Long userId;
    // 积分余额, 1积分=1分钱
    private Integer balance;
    // 积分账户创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}

