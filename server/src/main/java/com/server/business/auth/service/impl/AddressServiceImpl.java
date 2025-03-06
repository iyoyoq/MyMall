package com.server.business.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.business.auth.domain.Address;
import com.server.business.auth.mapper.AddressMapper;
import com.server.business.auth.service.IAddressService;
import org.springframework.stereotype.Service;

/**
 * 用户地址表(Address)表服务实现类
 *
 * @author localyjy
 * @since 2025-03-06 16:36:56
 */
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}

