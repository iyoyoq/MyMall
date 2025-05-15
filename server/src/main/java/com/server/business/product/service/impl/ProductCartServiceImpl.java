package com.server.business.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.ProductCart;
import com.server.business.product.domain.vo.ProductCartVo;
import com.server.business.product.domain.vo.ProductSkuDetailVo;
import com.server.business.product.mapper.ProductCartMapper;
import com.server.business.product.mapper.ProductSkuMapper;
import com.server.business.product.service.IProductCartService;
import com.server.business.product.service.IProductService;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/14 15:08
 */

@Service
public class ProductCartServiceImpl implements IProductCartService {

    @Autowired
    private ProductCartMapper cartMapper;

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private ProductSkuMapper skuMapper;

    @Override
    public int insert(ProductCart dto) {
        Long userId = requestContext.getUser().getId();
        dto.setId(null).setUserId(userId);
        ProductCart cartFromDb = cartMapper.selectOne(new LambdaQueryWrapper<ProductCart>()
                .eq(ProductCart::getUserId, userId)
                .eq(ProductCart::getSkuId, dto.getSkuId())
        );
        if (cartFromDb != null && cartFromDb.getId() != null) {
            // 购物车中已经存在该SKU， 添加数量
            cartFromDb.setPurchaseQuantity(cartFromDb.getPurchaseQuantity() + dto.getPurchaseQuantity());
            cartMapper.updateById(cartFromDb);
            return 1;
        }
        return cartMapper.insert(dto);
    }

    @Override
    public int remove(List<Long> skuIdList) {
        return cartMapper.delete(
                new LambdaUpdateWrapper<ProductCart>().in(ProductCart::getId, skuIdList));
    }

    // 20250514160527
    // 这是非理性的，这是疯狂的，这是不可能的，是病态的，神经质的，必然失败的，可笑的，不切实际的

    @Override
    public ProductCartVo list(Integer pageNum, Integer pageSize) {
        Page<ProductCart> mpPage = cartMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<ProductCart>()
                        .eq(ProductCart::getUserId, requestContext.getUser().getId())
        );

        ProductCartVo vo = new ProductCartVo();

        List<ProductSkuDetailVo> detailList = skuMapper.getDetailBySkuIds(
                mpPage.getRecords().stream().map(ProductCart::getSkuId).collect(Collectors.toList()));
        return vo;
    }
}
