import request from '@/utils/request.js'

// 获取购物车列表
export function cartListApi(query) {
  return request({
    url: '/product/cart/list',
    method: 'get',
    params: {
      pageNum: query.pageNum,
      pageSize: query.pageSize,
    },
  })
}

// 添加商品至购物车
export function cartAddApi(data) {
  return request({
    url: '/product/cart/add',
    method: 'post',
    data
  })
}

// 从购物车中移除
export function cartRemoveApi(skuIdList) {
  return request({
    url: '/product/cart/remove',
    method: 'post',
    data: skuIdList
  })
}
