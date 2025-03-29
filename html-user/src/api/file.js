import request from '@/utils/request.js'

export function fileUploadApi (file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: `/file/upload`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: formData,
  })
}
