package com.server.business.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.User;
import com.server.business.auth.mapper.UserMapper;
import com.server.business.pay.domain.PayPoints;
import com.server.business.pay.domain.PayPointsChange;
import com.server.business.pay.domain.dto.PayPointsDto;
import com.server.business.pay.domain.vo.PayPointsVo;
import com.server.business.pay.mapper.PayPointsChangeMapper;
import com.server.business.pay.mapper.PayPointsMapper;
import com.server.business.pay.service.IUserPointsService;
import com.server.exception.BusinessException;
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/25 14:32
 */
@Service
public class UserPointsServiceImpl implements IUserPointsService {
    @Autowired
    private PayPointsChangeMapper payPointsChangeMapper; // 用户积分流水表
    @Autowired
    private PayPointsMapper payPointsMapper; // 用户积分余额表

    // others
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RequestContext requestContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(long userId, int points, int reason) {
        PayPoints payPoints = payPointsMapper.selectById(userId);
        if (payPoints == null) {
            payPoints = new PayPoints().setUserId(userId).setBalance(0);
            payPointsMapper.insert(payPoints);
        }
        if (points < 0 && payPoints.getBalance() < Math.abs(points)) {
            throw new BusinessException("用户积分余额不足");
        }
        int update = payPointsMapper.update(
                new PayPoints().setBalance(payPoints.getBalance() + points),
                new LambdaQueryWrapper<PayPoints>()
                        .eq(PayPoints::getUserId, userId)
                        .eq(PayPoints::getBalance, payPoints.getBalance())
        );
        if (update == 1) {
            payPointsChangeMapper.insert(
                    new PayPointsChange().setUserId(userId).setValueChange(points).setReason(reason)
            );
        } else {
            throw new BusinessException("20250525153406用户积分更新失败");
        }
    }


    @Override
    public PayPointsVo payPoints(Integer pageNum, Integer pageSize, PayPointsDto dto) {
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<User>()
                        .like(StringUtils.hasText(dto.getCode()), User::getCode, dto.getCode())
                        .orderByDesc(User::getCreateTime)
        );

        if (userPage.getTotal() == 0) {
            return new PayPointsVo();
        }

        Map<Long, User> userMap = userPage.getRecords().stream()
                .filter(user -> user != null && user.getId() != null)
                .collect(Collectors.toMap(
                        User::getId,
                        user -> user,
                        (existing, replacement) -> {
                            throw new IllegalArgumentException("发现重复的用户 ID: " + existing.getId());
                        }
                ));

        Map<Long, Integer> pointsMap = payPointsMapper.selectList(new LambdaQueryWrapper<PayPoints>()
                .in(PayPoints::getUserId, userMap.keySet())
        ).stream().collect(Collectors.toMap(PayPoints::getUserId, PayPoints::getBalance));

        PayPointsVo vo = new PayPointsVo();

        List<PayPointsVo.Points> points = new ArrayList<>();
        userPage.getRecords().forEach(user -> {
            points.add(new PayPointsVo.Points()
                    .setUser(user)
                    .setBalance(pointsMap.get(user.getId()))
            );
        });

        vo.setPointPage(new RPage<>(points, userPage.getTotal()));


        return vo;
    }

    @Override
    public PayPoints user() {
        List<PayPoints> payPoints = payPointsMapper.selectList(new LambdaQueryWrapper<PayPoints>()
                .eq(PayPoints::getUserId, requestContext.getUser().getId()));
        if (payPoints.isEmpty()) {
            return new PayPoints().setBalance(0);
        } else if (payPoints.size() > 1) {
            throw new BusinessException("20250527200728用户积分数据异常");
        } else {
            return payPoints.get(0);
        }


    }


}
