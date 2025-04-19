import request from '@/utils/request.js'
const base = '/auth'

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
    method: 'post',
    data: data
  })
}

export function addressPageApi(data){
  return request({
    url: `${base}/address/list`,
    method: 'post',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize
    },
    data: data
  })
}

export function addressAddApi(data){
  return request({
    url: `${base}/address/save`,
    method: 'post',
    data: data
  })
}

export function addressEditApi(data){
  return request({
    url: `${base}/address/update`,
    method: 'post',
    data: data
  })
}

export function addressRemoveApi(idList){
  return request({
    url: `${base}/address/remove`,
    method: 'post',
    data: idList
  })
}





