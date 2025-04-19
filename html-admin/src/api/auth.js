import request from '@/utils/request.js'

export function passwordLoginApi (data) {
  return request({
    url: `/auth/admin/passwordLogin`,
    method: 'post',
    data: data,
  })
}
