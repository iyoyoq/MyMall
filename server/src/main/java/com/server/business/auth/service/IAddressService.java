package com.server.business.auth.service;

import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.dto.AddressCreateDto;
import com.server.pojo.RPage;

import java.util.List;
import java.util.Optional;

/**
 * 用户地址表(Address)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:36:55
 */
public interface IAddressService {

    RPage<Address> selectPage(Integer pageNum, Integer pageSize, Address address);

    int insert(AddressCreateDto db);

    int removeById(List<Long> id);

    int updateById(Address address);

    void setDefault(Long userId, Long addressId);

    /**
     * 获取默认地址
     */
    Optional<Address> getDefault(Long userId);
}

