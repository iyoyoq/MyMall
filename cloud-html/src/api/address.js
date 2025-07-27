import request from '@/utils/request'

export const addressApi = {
  // 获取地址列表
  setDefault: (addressId, data = {}) => {
    return request({
      url: '/auth/address/setDefault',
      method: 'post',
      params: {
        addressId: addressId,
      },
      data: data,
    })
  },

  // 取消默认
  cancelDefault: (item) => {
    return request({
      url: '/auth/address/update',
      method: 'post',
      data: {
        ...item,
        isDefault: 0,
      },
    })
  },
  // 获取地址列表
  list: (params = { pageNum: 1, pageSize: 100 }, data = {}) => {
    return request({
      url: '/auth/address/list',
      method: 'post',
      params: {
        pageNum: params.pageNum,
        pageSize: params.pageSize,
      },
      data: data,
    })
  },
  // 新增地址
  save: (data) => {
    return request({
      url: '/auth/address/save',
      method: 'post',
      data,
    })
  },
  // 更新地址
  update: (data) => {
    return request({
      url: '/auth/address/update',
      method: 'post',
      data,
    })
  },
  // 删除地址
  remove: (idList) => {
    return request({
      url: '/auth/address/remove',
      method: 'post',
      data: idList,
    })
  },
}
