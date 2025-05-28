package com.server.business.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    // OrderDetailVo getDetailVoById(@Param("orderId") Long orderId);
}
