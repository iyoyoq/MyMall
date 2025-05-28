package com.server.business.pay.service;

import com.server.business.pay.domain.Pay;
import com.server.business.pay.domain.dto.PayFinishDto;

/**
 * @Description: 支付服务层接口
 * @Author: yjy
 * @Date: 2025/5/24 18:46
 */
public interface IPayService {

    Pay createPay(Pay dto);

    void finishPay(PayFinishDto dto);
}
