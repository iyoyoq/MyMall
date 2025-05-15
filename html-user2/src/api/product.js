import request from '@/utils/request.js'

// 获取商品列表
export function productListApi (query) {
  return request({
    url: '/product/product/list',
    method: 'post',
    data: { ...query },
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

// 商品收藏 新增
export function addFavoriteApi (data) {
  return request({
    url: `/product/favorite/add`,
    method: 'post',
    data,
  })
}

// 商品收藏 list
export function listFavoriteApi (params) {
  return request({
    url: `/product/favorite/list`,
    method: 'get',
    params,
  })
}

// 商品收藏 批量取消
export function favoriteBatchCancelApi (favoriteIdList) {
  return request({
    url: `/product/favorite/batchCancel`,
    method: 'post',
    data: [
      ...favoriteIdList,
    ],
  })
}


// 商品收藏 单条取消
export function favoriteCancelApi (productId) {
  return request({
    url: `/product/favorite/cancel`,
    method: 'post',
    params: {
      productId,
    },
  })
}

