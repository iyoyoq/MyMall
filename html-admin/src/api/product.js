import request from '@/utils/request.js'

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
