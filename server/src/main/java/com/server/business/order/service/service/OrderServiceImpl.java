package com.server.business.order.service.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.mapper.AddressMapper;
import com.server.business.auth.service.IAddressService;
import com.server.business.order.domain.Order;
import com.server.business.order.domain.OrderDetail;
import com.server.business.order.domain.dto.OrderCreateDto;
import com.server.business.order.domain.dto.OrderDeliveryDto;
import com.server.business.order.domain.dto.OrderPayDto;
import com.server.business.order.domain.dto.OrderReceiveDto;
import com.server.business.order.domain.vo.OrderDetailVo;
import com.server.business.order.domain.vo.OrderListVo;
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
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

        // 1.扣减库存
        productSkuService.deductStock(dto.getSkuIdAndQuantity());

        // 2.生成订单
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
                .map(buyNum -> {  // k:skuId, v:购买数目
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

        // 生成订单信息
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
        LocalDateTime now = LocalDateTime.now();
        Order order = orderMapper.selectById(dto.getId());
        if (order == null) {
            throw new BusinessException("订单不存在 0529083852");
        }
        if (order.getStatus() != 10) {
            throw new BusinessException("订单已支付 0529083816");
        }
        if (now.isAfter(order.getPayDdl())) {
            throw new BusinessException("订单超时已自动关闭 0528223440");
        }


        // 2.进行支付
        Pay pay;
        if (dto.getPayMethod() == 10) {
            pay = payService.createPay(new Pay()
                    .setPayUserId(requestContext.getUser().getId())
                    .setAmount(order.getTotalAmount())
                    .setPayMethod(dto.getPayMethod())
            );
            payService.finishPay(new PayFinishDto(pay.getId()));
        } else {
            throw new BusinessException("暂不支持该种支付方式 0528223436");
        }

        // 3. 支付成功，更新订单状态
        BeanUtil.copyProperties(dto, order);
        order.setStatus(20);  //   更新订单状态为已支付
        order.setPayTime(now);
        order.setPayId(pay.getId());
        orderMapper.updateById(order);
        return pay;
    }

    @Override
    public OrderListVo list(Integer pageNum, Integer pageSize, Long userId, Integer status) {
        OrderListVo vo = new OrderListVo();
        Page<Order> orderPage = orderMapper.selectPage(new Page<Order>(pageNum, pageSize),
                new LambdaQueryWrapper<Order>()
                        .eq(userId != null, Order::getUserId, userId)
                        .eq(status != null, Order::getStatus, status)
                        .orderByDesc(Order::getCreateTime)
        );
        if (orderPage.getTotal() == 0) {
            vo.setPage(RPage.empty());
            return vo;
        }
        List<OrderDetailVo> orderDetailVoList = this.getOrderDetailVoList(orderPage.getRecords());
        vo.setPage(new RPage<>(orderDetailVoList, orderPage.getTotal()));

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delivery(OrderDeliveryDto dto) {
        Order order = new Order()
                .setDeliveryTime(LocalDateTime.now())
                .setLogisticsCode(dto.getLogisticsCode())
                .setStatus(30)
                ;

        int i = orderMapper.update(order,
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getId, dto.getOrderId())
                        .eq(Order::getStatus, 20)
        );

        if (i != 1) throw new BusinessException("发货执行失败 0602112543");
    }

    @Override
    public void receive(OrderReceiveDto dto) {
        Order order = new Order()
                .setReceiveTime(LocalDateTime.now())
                .setStatus(40)
                ;

        int i = orderMapper.update(order,
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getId, dto.getOrderId())
                        .eq(Order::getStatus, 30)
                        .eq(Order::getUserId, requestContext.getUser().getId())
        );

        if (i != 1) throw new BusinessException("收货执行失败 0602163606");
    }

    /**
     * 根据订单获取订单详情
     */
    private List<OrderDetailVo> getOrderDetailVoList(List<Order> orders) {
        // 订单详情
        List<OrderDetail> detailList = orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>()
                .in(!CollectionUtils.isEmpty(orders), OrderDetail::getOrderCode, orders.stream().map(Order::getId).collect(Collectors.toList()))
        );
        Map<String, List<OrderDetail>> orderDetailMap = detailList.stream().collect(Collectors.groupingBy(OrderDetail::getOrderCode));


        // sku信息
        List<ProductSkuDetailVo> skuList = skuMapper.getDetailBySkuIds(detailList.stream().map(OrderDetail::getSkuId).collect(Collectors.toList()));
        Map<Long, ProductSkuDetailVo> skuMap = skuList.stream().collect(Collectors.toMap(ProductSkuDetailVo::getId, sku -> sku));


        List<OrderDetailVo> result = new ArrayList<>();
        for (Order order : orders) {
            // 每个订单
            OrderDetailVo vo = new OrderDetailVo().setOrder(order);
            result.add(vo);
            List<OrderDetailVo.OrderDetailExtendInfo> orderDetailList = new ArrayList<>();
            vo.setOrderDetailList(orderDetailList);

            // 一个订单中的订单详情
            for (OrderDetail orderDetail : orderDetailMap.get(order.getId().toString())) {
                orderDetailList.add(new OrderDetailVo.OrderDetailExtendInfo()
                        .setOrderDetail(orderDetail)
                        .setSku(skuMap.get(orderDetail.getSkuId()))
                );
            }
        }
        return result;
    }
}
