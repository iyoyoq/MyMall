package com.server.business.pay.service.impl;

import com.server.business.pay.domain.Pay;
import com.server.business.pay.domain.dto.PayFinishDto;
import com.server.business.pay.mapper.PayMapper;
import com.server.business.pay.service.IPayService;
import com.server.business.pay.service.IUserPointsService;
import com.server.exception.BusinessException;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 支付服务
 * @Author: yjy
 * @Date: 2025/5/25 13:11
 */
@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private PayMapper payMapper;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private IUserPointsService userPointsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pay createPay(Pay dto) {
        if (dto.getPayMethod() != 10) throw new BusinessException("20250525140406暂时只支持积分支付");

        Pay pay = Pay.builder()
                .payUserId(requestContext.getUser().getId())
                .amount(dto.getAmount())
                .payMethod(dto.getPayMethod())
                .payStatus(10) // 未支付状态
                .build();
        payMapper.insert(pay);
        return pay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishPay(PayFinishDto dto) {
        Pay pay = payMapper.selectById(dto.getId());
        if(pay == null) throw new BusinessException("20250525164955支付单不存在");

        // 积分支付
        if (pay.getPayStatus() == 10) {
            userPointsService.change(pay.getPayUserId(), -pay.getAmount(), -20);
            payMapper.updateById(pay.setPayStatus(20));
        } else {
            throw new BusinessException("20250525143923不支持的支付类型");
        }

    }
}
