package com.server.business.auth.service;

import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.dto.AddressCreateDTO;
import com.server.pojo.RPage;

import java.util.List;

/**
 * 用户地址表(Address)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:36:55
 */
public interface IAddressService {

    RPage<Address> selectPage(Integer pageNum, Integer pageSize, Address address);

    int insert(AddressCreateDTO db);

    int removeById(List<Long> id);

    int updateById(Address address);
}

