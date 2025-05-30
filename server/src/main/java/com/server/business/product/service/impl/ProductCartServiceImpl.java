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
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                new LambdaUpdateWrapper<ProductCart>()
                        .eq(ProductCart::getUserId, requestContext.getUser().getId())
                        .in(ProductCart::getSkuId, skuIdList));
    }

    // 20250514160527
    // 这是非理性的，这是疯狂的，这是不可能的，是病态的，神经质的，必然失败的，可笑的，不切实际的

    @Override
    public ProductCartVo list(Integer pageNum, Integer pageSize) {
        Page<ProductCart> mpPage = cartMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<ProductCart>()
                        .select(ProductCart::getSkuId, ProductCart::getPurchaseQuantity)
                        .eq(ProductCart::getUserId, requestContext.getUser().getId())
                        .orderByDesc(ProductCart::getCreateTime)
        );

        ProductCartVo vo = new ProductCartVo();

        if (mpPage.getTotal() == 0L) {
            vo.setPage(RPage.empty());
            return vo;
        }

        List<ProductSkuDetailVo> skuDetailList = skuMapper.getDetailBySkuIds(
                mpPage.getRecords().stream().map(ProductCart::getSkuId).collect(Collectors.toList()));

        skuDetailList.forEach(detail -> {
            // 存入购物车中SKU的数目
            detail.setPurchaseQuantity(mpPage.getRecords().stream()
                    .filter(cart -> cart.getSkuId().equals(detail.getId()))
                    .mapToInt(ProductCart::getPurchaseQuantity)
                    .sum());
        });

        vo.setPage(new RPage<>(skuDetailList, mpPage.getTotal()));
        return vo;
    }
}
