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
