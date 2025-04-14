import request from '@/utils/request.js'

const base = '/product'

export async function pageApi (param) {
  return request({
    url: `${base}/product/list`,
    method: 'post',
    params: {
      pageNum: param.pageNum,
      pageSize: param.pageSize,
    },
    data: param.dto
  })
}
