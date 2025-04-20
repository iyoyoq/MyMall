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


// 商品分类
export function productCategoryListApi () {
  return request({
    url: `/product/category/list`,
    method: 'get',
  })
}



