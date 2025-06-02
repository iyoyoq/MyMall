import request from '@/utils/request.js'

export function orderListsApi (data) {
  return request({
    url: `/order/list`,
    method: 'get',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      userId: data.userId,
      status: data.status,
    }
  })
}

export function detailOrderApi (orderId) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: {
      orderId: orderId,
    },
  })
}

// 商品发货
export function deliveryApi (data) {
  return request({
    url: '/order/delivery',
    method: 'post',
    data: data
  })
}


export function getOrderStatusText (status) {
  // console.log(typeof status)
  if(status == null) return ''
  const textMap = {
    '10': '待支付',
    '20': '待发货',
    '30': '待收货',
    '40': '已完成',
    '5': '已取消',
  }
  return textMap[status.toString()] || '未知状态'
}
