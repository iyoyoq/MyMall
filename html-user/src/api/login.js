import request from '@/utils/request.js'

// 示例
export function demoApi () {
  return request({
    url: '/demo',
    method: 'post',
    data: {},
    params: {},
  })
}

export function sendLoginCodeApi (phone) {
  return request({
    url: '/auth/user/login/sendLoginCode',
    method: 'get',
    params: phone,
  })
}

export function codeLoginApi (data) {
  return request({
    url: '/auth/user/login/sendLoginCode',
    method: 'get',
    data: data
  })
}





