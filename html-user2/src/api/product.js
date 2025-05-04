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

// 商品收藏 update
export function updateFavoriteApi (data) {
  return request({
    url: `/product/favorite/update`,
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

// 商品收藏 list
export function favoriteBatchCancelApi (favoriteIdList) {
  return request({
    url: `/product/favorite/batchCancel`,
    method: 'post',
    data: [
      ...favoriteIdList,
    ],
  })
}

