package com.server.business.pay.service;

import com.server.business.pay.domain.PayPoints;
import com.server.business.pay.domain.dto.PayPointsDto;
import com.server.business.pay.domain.vo.PayPointsVo;


/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/25 14:32
 */
public interface IUserPointsService {

    void change(long userId, int points, int reason);


    PayPointsVo payPoints(Integer pageNum, Integer pageSize, PayPointsDto dto);

    PayPoints user();
}
