package com.server.business.auth.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.User;
import com.server.business.auth.domain.dto.AddressCreateDto;
import com.server.business.auth.mapper.AddressMapper;
import com.server.business.auth.service.IAddressService;
import com.server.exception.BusinessException;
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户地址表(Address)表服务实现类
 *
 * @author localyjy
 * @since 2025-03-06 16:36:56
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RequestContext requestContext;

    @Override
    public RPage<Address> selectPage(Integer pageNum, Integer pageSize, Address address) {
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Address::getUserId, address.getUserId());
        lambdaQueryWrapper.eq(Address::getStatus, 1);  // 未删除的地址
        lambdaQueryWrapper.orderByDesc(Address::getCreateTime);
        return new RPage<>(addressMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper));
    }


    @Override
    public int insert(AddressCreateDto dto) {
        dto.setUserId(requestContext.getUser().getId());
        Address db = BeanUtil.copyProperties(dto, Address.class);
        return addressMapper.insert(db);
    }

    @Override
    public int removeById(List<Long> id) {
        Address address = new Address();
        address.setStatus(-1);
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, requestContext.getUser().getId());
        wrapper.in(Address::getId, id);
        return addressMapper.update(address, wrapper);
    }

    @Override
    public int updateById(Address address) {
        if (!requestContext.getUser().getId().equals(address.getUserId())) {
            throw new BusinessException("请求错误");
        }
        return addressMapper.updateById(address);
    }

    @Override
    public void setDefault(Long userId, Long addressId) {
        // 全部设为非默认
        addressMapper.update(new Address().setUserId(userId).setIsDefault(0),
                new LambdaQueryWrapper<Address>().eq(Address::getUserId, userId)
        );
        addressMapper.updateById(new Address().setId(addressId).setIsDefault(1));
    }


}

