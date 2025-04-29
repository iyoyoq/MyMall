import request from '@/utils/request.js'

// 商品
export function productListApi (data) {
  return request({
    url: `/product/product/list`,
    method: 'post',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
    },
    data: data,
  })
}

export function productSaveApi (data) {
  return request({
    url: `/product/product/save`,
    method: 'post',
    data: data,
  })
}

export function productUpdateApi (data) {
  return request({
    url: `/product/product/update`,
    method: 'post',
    data: data,
  })
}

export function productDeleteApi (productId) {
  return request({
    url: `/product/product/delete`,
    method: 'post',
    params: {
      productId
    }
  })
}

// 商品分类
export function productCategoryListApi () {
  return request({
    url: `/product/category/list`,
    method: 'get',
  })
}

export function productCategoryCreateApi (dto) {
  return request({
    url: `/product/category/save`,
    method: 'post',
    data: dto,
  })
}

export function productCategoryUpdateApi (dto) {
  return request({
    url: `/product/category/update`,
    method: 'post',
    data: dto,
  })
}

export function productSkuCreateApi (dto) {
  return request({
    url: `/product/sku/save`,
    method: 'post',
    data: dto,
  })
}

export function productSkuQueryApi (productId) {
  return request({
    url: `/product/sku/detail`,
    method: 'get',
    params: {
      productId,
    },
  })
}

