package com.server.business.product.controller;

import com.server.aop.CheckLogin;
import com.server.business.product.domain.Favorite;
import com.server.business.product.service.IFavoriteService;
import com.server.pojo.R;
import com.server.pojo.RPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 用户收藏表
 * @Author: yjy
 * @Date: 2025/3/12 22:52
 */
@RestController
@RequestMapping("/product/favorite")
public class ProductFavoriteController {
    @Autowired
    private IFavoriteService favoriteService;


    /**
     * 分页查询
     */
    @GetMapping("/list")
    @CheckLogin
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            Favorite favorite) {
        RPage<Favorite> result = favoriteService.pageForSelf(pageNum, pageSize, favorite);
        return R.ok(result);
    }

    /**
     * 批量取消收藏
     */
    @PostMapping("/batchCancel")
    @CheckLogin
    public R batchCancel(@RequestBody List<Long> favoriteIdList) {
        int b = favoriteService.batchCancel(favoriteIdList);
        return R.ok();
    }

    /**
     * 商品页面单条取消收藏
     */
    @PostMapping("/cancel")
    @CheckLogin
    public R cancel(@RequestParam("productId") Long productId) {
        int b = favoriteService.cancel(productId);
        return R.ok();
    }


    /**
     * 新增收藏
     */
    @PostMapping("/add")
    @CheckLogin
    public R update(@RequestBody Favorite favorite) {
        int b = favoriteService.add(favorite);
        return R.judge(b > 0, "新增失败");
    }

}
