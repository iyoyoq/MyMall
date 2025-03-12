package com.server.business.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.dto.AddressCreateDTO;

/**
 * 用户地址表(Address)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:36:55
 */
public interface IAddressService {

    Page<Address> selectPage(Integer pageNum, Integer pageSize, Address address);

    Address selectById(Long id);

    int insert(AddressCreateDTO db);

    int removeById(Long id);

    int updateById(Address address);
}

