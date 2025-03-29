package com.server.business.auth.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.User;
import com.server.business.auth.domain.dto.AddressCreateDTO;
import com.server.business.auth.mapper.AddressMapper;
import com.server.business.auth.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<Address> selectPage(Integer pageNum, Integer pageSize, Address address) {
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Address::getUserId, address.getUserId());
        return addressMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
    }

    @Override
    public Address selectById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    public int insert(AddressCreateDTO dto) {
        Address db = BeanUtil.copyProperties(dto, Address.class);
        return addressMapper.insert(db);
    }

    @Override
    public int removeById(Long id) {
        Address address = new Address();
        address.setId(id);
        address.setStatus(-1);
        return addressMapper.updateById(address);
    }

    @Override
    public int updateById(Address address) {
        return addressMapper.updateById(address);
    }


}

