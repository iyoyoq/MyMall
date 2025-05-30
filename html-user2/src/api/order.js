import request from '@/utils/request.js'

// 示例
export function createOrderApi (data) {
  return request({
    url: '/order/create',
    method: 'post',
    data: {
      skuIdAndQuantity: data.skuIdAndQuantity,
    },
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

export function goPayApi (data) {
  return request({
    url: '/order/goPay',
    method: 'post',
    data: {
      id: data.id,  // 订单id
      addressId: data.addressId, // 地址id
      userRemark: data.userRemark, // 用户备注
      payMethod: data.payMethod,// 支付方式（ 10积分 20微信 30支付宝 100银行卡）
    },
  })
}


export function userPointsApi () {
  return request({
    url: `/pay/points/user`,
    method: 'get'
  })
}


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

// utils
export function getOrderStatusText (status) {
  console.log(typeof status)
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
