// 用户数据
export const users = Array.from({ length: 20 }, (_, index) => ({
  id: index + 1,
  username: `user${index + 1}`,
  email: `user${index + 1}@example.com`,
  status: Math.random() > 0.5 ? 1 : 0,
  createTime: new Date(Date.now() - Math.floor(Math.random() * 90) * 24 * 60 * 60 * 1000).toLocaleString()
}))

// 商品数据
export const products = Array.from({ length: 20 }, (_, index) => ({
  id: index + 1,
  name: `商品${index + 1}`,
  price: Number((Math.random() * 1000 + 100).toFixed(2)),
  stock: Math.floor(Math.random() * 1000),
  status: Math.random() > 0.5 ? 1 : 0,
  description: `这是商品${index + 1}的详细描述`,
  createTime: new Date(Date.now() - Math.floor(Math.random() * 90) * 24 * 60 * 60 * 1000).toLocaleString()
}))

// 评论数据
export const comments = Array.from({ length: 20 }, (_, index) => ({
  id: index + 1,
  productId: Math.floor(Math.random() * 20) + 1,
  productName: `商品${Math.floor(Math.random() * 20) + 1}`,
  userId: Math.floor(Math.random() * 20) + 1,
  username: `user${Math.floor(Math.random() * 20) + 1}`,
  content: `这是一条测试评论内容，评论ID为${index + 1}，描述了对商品的使用感受。`,
  rating: Math.floor(Math.random() * 3) + 3, // 3-5星评分
  status: Math.random() > 0.3 ? 1 : 0, // 70%概率显示
  createTime: new Date(Date.now() - Math.floor(Math.random() * 90) * 24 * 60 * 60 * 1000).toLocaleString()
}))

// 模拟分页查询
export const getPageData = (data, page, pageSize) => {
  const start = (page - 1) * pageSize
  const end = start + pageSize
  return {
    list: data.slice(start, end),
    total: data.length
  }
} 