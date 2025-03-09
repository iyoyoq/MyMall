import axios from 'axios'
import router from '@/router'
import JSONBig from 'json-bigint'
import { Message } from '@arco-design/web-vue'

const baseURL = 'http://localhost:45000/api' // 后端api
const localStorageTokenName = 'amity-mall-token' // 存到localStorage 里面的 TokenName

const instance = axios.create({
  // 1.基础地址，超时时间 /ms
  baseURL,
  timeout: 10000,
  // withCredentials: true,
  // 处理 bigint
  transformResponse: [
    function (data) {
      try {
        // 如果转换成功则返回转换的数据结果
        return JSONBig.parse(data)
      } catch (err) {
        // 如果转换失败，则包装为统一数据格式并返回
        return {
          data,
        }
      }
    }],
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 2.携带 token
    const token = localStorage.getItem(localStorageTokenName)
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (err) => Promise.reject(err),
)

// 响应拦截器
instance.interceptors.response.use(
  (res) => {
    const code = res.data.code
    const msg = res.data.msg
    // 3. 处理业务失败
    //  4.摘取核心响应数据
    if (code === undefined || code === null) {
      Message.error('响应格式异常')
    }
    switch (code) {
      case 1:
        return res
      case 10003: {
        Message.error('身份无效,请重新登录')
        router.push('/login')
        return
      }
      case 10002: {
        // 业务异常
        Message.error(msg)
        return Promise.reject(res.data)
      }
      default: {
        Message.error('服务异常')
        return Promise.reject(res.data)
      }
    }
  },
  (err) => {
    // console.log(err)
    // // 错误特殊 401
    // if (err.response?.status === 401) {
    //   router.push('/login')
    // }
    // if(err.response?.status === 403){
    //   router.push('/login')
    //   MSG_Err('身份无效,请重新登录')
    //   return Promise.reject(err)
    // }
    // 错误默认情况
    // ElMessage.error(err.response.data.message || '服务异常')
    router.push('/login')
    return Promise.reject(err)
  })
// const request = instance

export default instance

export { baseURL,localStorageTokenName }


