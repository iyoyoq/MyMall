import request from '@/utils/request.js'


export function selfDetailApi () {
  return request({
    url: '/auth/user/self/detail',
    method: 'get',
  })
}
export function updateSelfDetailApi (data) {
  return request({
    url: '/auth/user/self/update',
    method: 'post',
    data: data
  })
}


