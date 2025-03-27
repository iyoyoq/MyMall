import request from '@/utils/request.js'

const base = '/auth/user'

// 示例
export function demoApi () {
  return request({
    url: `${base}/demo`,
    method: 'post',
    data: {},
    params: {},
  })
}


export function getSelfDetailApi () {
  return request({
    url: `${base}/user/self/detail`,
    method: 'get'
  })
}

export async function sendLoginCodeApi (phone) {
  return request({
    url: `${base}/login/sendLoginCode`,
    method: 'get',
    params: {phone:phone},
  })
}

export function codeLoginApi (data) {
  return request({
    url: `${base}/login/codeLogin`,
    method: 'post',
    data: data
  })
}

export function updateUserInfoApi(data){
  return request({
    url: `${base}/user/self/update`,
    method: 'put',
    data: data
  })
}



