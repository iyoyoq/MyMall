package com.server.business.order.service.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.server.business.auth.domain.Address;
import com.server.business.auth.mapper.AddressMapper;
import com.server.business.auth.service.IAddressService;
import com.server.business.order.domain.Order;
import com.server.business.order.domain.OrderDetail;
import com.server.business.order.domain.dto.OrderCreateDto;
import com.server.business.order.domain.dto.OrderPayDto;
import com.server.business.order.domain.vo.OrderDetailVo;
import com.server.business.order.mapper.OrderDetailMapper;
import com.server.business.order.mapper.OrderMapper;
import com.server.business.order.service.IOrderService;
import com.server.business.pay.domain.Pay;
import com.server.business.pay.domain.dto.PayFinishDto;
import com.server.business.pay.service.IPayService;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.vo.ProductSkuDetailVo;
import com.server.business.product.mapper.ProductSkuMapper;
import com.server.business.product.service.IProductSkuService;
import com.server.config.business.OrderConfig;
import com.server.exception.BusinessException;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/5/19 10:54
 */
@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;


    // other modules
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private IProductSkuService productSkuService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ProductSkuMapper skuMapper;
    @Autowired
    private IPayService payService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(OrderCreateDto dto) {

        Long userId = requestContext.userLoginCheck().getId();
        dto.setUserId(userId);

        Set<Long> skuIds = dto.getSkuIdAndQuantity().keySet();
        Map<Long, ProductSku> productSkus = productSkuService.getProductSkuBySkuIds(skuIds);
        Map<Long, Product> products = productSkuService.getProductBySkuIds(skuIds);

        // 订单主表id
        Long orderId = IdWorker.getId();
        AtomicInteger orderTotalPrice = new AtomicInteger(0);
        AtomicInteger totalShippingFee = new AtomicInteger(0);

        List<OrderDetail> details = dto.getSkuIdAndQuantity().entrySet().stream()
                .map(buyNum -> {  // 购买数量
                    ProductSku productSku = productSkus.get(buyNum.getKey());
                    Product product = products.get(productSku.getProductId());

                    // 订单细节表唯一id
                    Long detailId = IdWorker.getId();

                    // 订单明细价格
                    int detailPrice = productSku.getPrice() * buyNum.getValue() + product.getShippingFee();
                    orderTotalPrice.addAndGet(detailPrice);
                    totalShippingFee.addAndGet(product.getShippingFee());
                    return new OrderDetail()
                            // +-*/ mp的获取唯一id
                            .setId(detailId)
                            .setOrderCode(orderId.toString())
                            .setProductId(product.getId())
                            .setProductSnapshotId(product.getCurrentSnapshotId())
                            .setSkuId(productSku.getId())
                            .setPrice(productSku.getPrice())
                            .setQuantity(buyNum.getValue())
                            .setShippingFee(product.getShippingFee())
                            .setTotalPrice(detailPrice);
                })
                .toList();

        LocalDateTime createTime = LocalDateTime.now();

        orderMapper.insert(new Order()
                .setId(orderId)
                .setUserId(dto.getUserId())
                .setTotalAmount(orderTotalPrice.get())
                .setTotalShippingFee(totalShippingFee.get())
                .setAddressId(addressService.getDefault(dto.getUserId()).orElseGet(() -> new Address().setId(null)).getId())
                .setCreateTime(createTime)
                .setPayDdl(createTime.plusSeconds(OrderConfig.autoCancelTime))
                .setStatus(10));

        orderDetailMapper.insert(details);

        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetailVo getById(Long orderId) {
        OrderDetailVo vo = new OrderDetailVo();

        Order order = orderMapper.selectById(orderId);

        if (order == null) throw new BusinessException("订单不存在");

        vo.setOrder(order);
        vo.setAddress(addressMapper.selectById(order.getAddressId()));

        List<OrderDetail> detailList = orderDetailMapper.selectList(
                new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderCode, orderId.toString()));

        // 获取订单细节表
        List<ProductSkuDetailVo> skuList = skuMapper.getDetailBySkuIds(detailList.stream().map(OrderDetail::getSkuId).collect(Collectors.toList()));
        // 转为 map 便于取值
        Map<Long, ProductSkuDetailVo> skuIdAndSku = skuList.stream().collect(Collectors.toMap(ProductSkuDetailVo::getId, sku -> sku));

        vo.setOrderDetailList(new ArrayList<>(detailList.size()));
        for (OrderDetail orderDetail : detailList) {
            vo.getOrderDetailList().add(
                    new OrderDetailVo.OrderDetailExtendInfo(orderDetail, skuIdAndSku.get(orderDetail.getSkuId()))
            );
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pay goPay(OrderPayDto dto) {
        Order order = orderMapper.selectById(dto.getId());
        if (order == null || LocalDateTime.now().isAfter(order.getPayDdl())) {
            throw new BusinessException("20250526121224订单超时已自动关闭");
        }

        BeanUtil.copyProperties(dto, order);
        orderMapper.updateById(order);


        if (dto.getPayMethod() == 10) {
            Pay pay = payService.createPay(new Pay()
                    .setPayUserId(requestContext.getUser().getId())
                    .setAmount(order.getTotalAmount())
                    .setPayMethod(dto.getPayMethod())
            );
            payService.finishPay(new PayFinishDto(pay.getId()));
            return pay;
        } else {
            throw new BusinessException("20250525164409暂不支持该种支付方式");
        }

    }
}
