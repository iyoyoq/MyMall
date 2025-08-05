import request from '@/utils/request.js'

export function payPointsAdminApi(data) {
  // 解构赋值：提取 pageNum 和 pageSize，剩下的属性存入 requestData
  const { pageNum, pageSize, ...requestData } = data;
  return request({
    url: `/pay/points/query`,
    method: 'post',
    params: { pageNum, pageSize },      // 直接使用解构出的变量
    data: requestData,                 // 不包含 pageNum 和 pageSize 的新对象
  });
}


export function addPointsAdminApi (data) {
  return request({
    url: `/pay/points/addPoints`,
    method: 'post',
    params:{
      userId: data.userId,
      points: data.points,
    },
  })
}


