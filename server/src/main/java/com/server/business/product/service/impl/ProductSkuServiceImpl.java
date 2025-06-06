package com.server.business.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.mapper.ProductSkuMapper;
import com.server.business.product.service.IProductSkuService;
import com.server.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/21 14:11
 */
@Service
@Slf4j
public class ProductSkuServiceImpl implements IProductSkuService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper skuMapper;


    @Override
    public ProductSkuDto getByProductId(Long productId, List<Integer> status) {
        List<ProductSku> productSkuList = skuMapper.selectList(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, productId)
                .eq(status.size() == 1, ProductSku::getStatus, status.get(0))
                // 如果某个属性全部下架，那么不显示该 sku 属性
                .in(status.size() > 1, ProductSku::getStatus, status)
        );

        // sku 属性列表
        List<String> skuNameList;
        try {
            skuNameList = List.of(productMapper.selectOne(new LambdaQueryWrapper<Product>()
                    .select(Product::getSkuAttrNames)
                    .eq(Product::getId, productId)
            ).getSkuAttrNames().split(","));
        } catch (Exception e) {
            String msg = "SKU属性不存在";
            log.error(msg, e);
            throw new BusinessException(msg);
        }

        ProductSkuDto vo = new ProductSkuDto();
        vo.setProductId(productId);
        vo.setSkuAttrNameList(skuNameList);
        vo.setSkuListByProductSkuList(productSkuList);

        if (!vo.check()) throw new BusinessException("20250421152128 请求参数有误");
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrUpdate(ProductSkuDto dto) {

        if (!dto.check()) throw new BusinessException("20250421152128 请求参数有误");

        List<ProductSkuDto.SingleSku> skuList = dto.getSkuList();

        int startingPrice = skuList.get(0).getPrice();

        List<ProductSku> insertList = new ArrayList<>();
        for (ProductSkuDto.SingleSku sku : skuList) {
            sku.setId(null); // 防止新增时原主键冲突
            sku.setSkuAttrValues(String.join(",", sku.getSkuAttrValueList()));
            sku.setProductId(dto.getProductId());
            sku.setStatus(1);
            startingPrice = Math.min(startingPrice, sku.getPrice());
            insertList.add(sku);
        }

        // 存入 skuAttrNames
        String skuAttrNames = String.join(",", dto.getSkuAttrNameList());
        productMapper.updateById(new Product()
                .setId(dto.getProductId())
                .setSkuAttrNames(skuAttrNames)
                .setStartingPrice(startingPrice)
        );

        // 把原 sku全部下架
        List<ProductSku> origin = skuMapper.selectList(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, dto.getProductId()));
        origin.forEach(item -> item.setStatus(-1));
        skuMapper.updateById(origin);

        // 放入新sku列表
        skuMapper.insert(insertList);
        return insertList.size();
    }

    @Override
    public Map<Long, ProductSku> getProductSkuBySkuIds(Set<Long> skuIds) {

        List<ProductSku> productSkus = skuMapper.selectList(
                new LambdaQueryWrapper<ProductSku>()
                        .in(ProductSku::getId, skuIds)
        );

        // 将查询结果转换为 Map，键为 SKU ID，值为 ProductSku 对象
        return productSkus.stream()
                .collect(Collectors.toMap(
                        ProductSku::getId,  // 使用 SKU ID 作为键
                        sku -> sku,         // 使用 ProductSku 对象本身作为值
                        (existing, replacement) -> existing  // 处理键冲突的情况，这里保留先出现的元素
                ));
    }

    @Override
    public Map<Long, Product> getProductBySkuIds(Set<Long> skuIds) {
        List<Product> products = skuMapper.selectProductListBySkuIds(skuIds);

        return products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductStock(Map<Long, Integer> skuIdAndQuantity) {
        for (Long skuId : skuIdAndQuantity.keySet()) {
            int rows = skuMapper.deductStock(skuId, skuIdAndQuantity.get(skuId));
            if (rows != 1) {
                throw new BusinessException("扣减库存失败 0528231107");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addStock(Map<Long, Integer> skuIdAndQuantity) {
        for (Long skuId : skuIdAndQuantity.keySet()) {
            int rows = skuMapper.addStock(skuId, skuIdAndQuantity.get(skuId));
            if (rows != 1) {
                throw new BusinessException("增加库存失败 0603093733");
            }
        }
    }


}
