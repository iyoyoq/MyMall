package com.server.business.pay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/25 13:51
 */

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class PayFinishDto {

    private Long id;  // 支付单id

}
