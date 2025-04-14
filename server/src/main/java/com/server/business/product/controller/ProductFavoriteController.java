package com.server.business.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Favorite;
import com.server.business.product.domain.dto.FavoriteCreateDto;
import com.server.business.product.service.IFavoriteService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/list")
    public R list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody Favorite favorite) {
        Page<Favorite> result = favoriteService.selectPage(pageNum, pageSize, favorite);
        return R.ok(result);
    }

    /**
     * 单条查询
     */
    @GetMapping("/detail")
    public R detail(Long id) {
        Favorite address = favoriteService.selectById(id);
        return R.ok(address);
    }


    /**
     * 增
     */
    @PostMapping("/save")
    public R create(@RequestBody FavoriteCreateDto dto) {
        int ok = favoriteService.insert(dto);
        return R.judge(ok > 0, "保存失败");
    }

    /**
     * 删
     */
    @DeleteMapping("/remove")
    public R remove(Long id) {
        int result = favoriteService.removeById(id);
        return R.judge(result > 0, "删除失败");
    }

    /**
     * 改
     */
    @PutMapping("/update")
    public R update(Favorite favorite) {
        int b = favoriteService.updateById(favorite);
        return R.judge(b > 0, "");
    }

}
