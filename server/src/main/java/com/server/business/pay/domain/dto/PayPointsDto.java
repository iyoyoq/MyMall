package com.server.business.pay.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/26 15:56
 */
@Data
@Accessors(chain = true)
public class PayPointsDto {
    private String code; //用户编号

}
