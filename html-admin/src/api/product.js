import request from '@/utils/request.js'

export function productCategoryListApi () {
  return request({
    url: `/product/category/list`,
    method: 'get',
  })
}
