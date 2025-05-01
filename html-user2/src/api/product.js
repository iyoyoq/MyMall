import request from '@/utils/request.js'

// 获取商品列表
export function productListApi (query) {
  return request({
    url: '/product/product/list',
    method: 'post',
    data: { query },
    params: {
      pageNum: query.pageNum,
      pageSize: query.pageSize,
    },
  })
}

// 获取商品详情
export function productApi (productId) {
  return request({
    url: '/product/product/detail',
    method: 'get',
    params: {
      id: productId,
    },
  })
}

// 商品分类
export function productCategoryListApi () {
  return request({
    url: `/product/category/list`,
    method: 'get',
  })
}
