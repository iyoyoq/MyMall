package com.server.business.pay.domain.vo;

import com.server.business.auth.domain.User;
import com.server.pojo.RPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 用户积分余额
 * @Author: yjy
 * @Date: 2025/5/26 15:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPointsVo  {

    private RPage<Points> pointPage;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class Points {
        private User user;

        // 积分余额, 1积分=1分钱
        private Integer balance;
    }

}
