package com.server.business.pay.controller;

import com.server.aop.CheckLogin;
import com.server.aop.LoginType;
import com.server.business.pay.domain.PayPoints;
import com.server.business.pay.domain.dto.PayPointsDto;
import com.server.business.pay.domain.vo.PayPointsVo;
import com.server.business.pay.service.IUserPointsService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.server.aop.LoginType.ADMIN;
import static com.server.aop.LoginType.USER;

/**
 * @Description: 用户积分控制器
 * @Author: yjy
 * @Date: 2025/5/25 16:35
 */

@RestController
@RequestMapping("/pay/points")
public class UserPointsController {

    @Autowired
    private IUserPointsService userPointsService;

    @PostMapping("/addPoints")
    @CheckLogin(allowRole = {LoginType.ADMIN})
    public R addPoints(@RequestParam("userId") Long userId, @RequestParam("points") Integer points){
        userPointsService.change(userId, Math.abs(points), 10);
        return R.ok();
    }

    /**
     * 用户积分信息
     */
    @PostMapping("/query")
    @CheckLogin(allowRole = {ADMIN})
    public R payPoints(@RequestParam("pageNum") Integer pageNum,
                       @RequestParam("pageSize") Integer pageSize,
                       @RequestBody PayPointsDto dto) {
        PayPointsVo vo = userPointsService.payPoints(pageNum, pageSize, dto);
        return R.ok(vo);
    }

    /**
     * 查询自己的积分信息
     */
    @GetMapping("/user")
    @CheckLogin(allowRole = {USER})
    public R user() {
        PayPoints vo = userPointsService.user();
        return R.ok(vo);
    }

}
