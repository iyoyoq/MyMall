package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.entity.ProductType;

import java.util.List;


public interface ProductTypeService extends IService<ProductType> {

  List<ProductType> allNormal();
}
