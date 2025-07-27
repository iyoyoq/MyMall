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

export async function sendLoginCodeApi (phone) {
  return request({
    url: '/auth/login/sendLoginCode',
    method: 'get',
    params: {phone:phone},
  })
}

export function codeLoginApi (data) {
  return request({
    url: '/auth/login/codeLogin',
    method: 'post',
    data: data
  })
}





