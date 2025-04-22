import request from '@/utils/request.js'

// 示例
export function selfDetailApi () {
  return request({
    url: '/auth/user/self/detail',
    method: 'get',
  })
}
