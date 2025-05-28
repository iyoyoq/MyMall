<template>
  <div style="padding: 24px; background: #fff; min-height: 360px; border-radius: 4px;width: 600px ">
    <div style="display: flex; justify-content: start; align-items: center; margin-bottom: 24px;">
      <h4 style="color: black;">我的订单</h4>
      <a-radio-group
        type="button"
        v-model="activeStatus"
        size="small"
        style="margin-left: 10px;"
      >
        <a-radio value="all">全部</a-radio>
        <a-radio value="10">待支付</a-radio>
        <a-radio value="20">待发货</a-radio>
        <a-radio value="30">待收货</a-radio>
        <a-radio value="40">已完成</a-radio>
        <a-radio value="5">已取消</a-radio>
      </a-radio-group>
    </div>

    <div>
      <a-list
        :data="filteredOrders"
        :pagination="{ pageSize: 5 }"
      >
        <template #item="{ item }">
          <a-list-item>
            <a-card style="width: 100%;">
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <div style="display: flex; gap: 24px;">
                  <span style="font-weight: bold;">订单号：{{ item.orderNo }}</span>
                  <span style="color: #999;">下单时间：{{ item.createTime }}</span>
                </div>
                <a-tag :color="getStatusColor(item.status)">{{ item.status }}</a-tag>
              </div>
              <div style="border-top: 1px solid #f0f0f0; border-bottom: 1px solid #f0f0f0; padding: 16px 0;">
                <div v-for="product in item.products" :key="product.id" style="display: flex; align-items: center; margin-bottom: 12px;">
                  <img :src="product.image" :alt="product.name" style="width: 60px; height: 60px; object-fit: cover; margin-right: 12px; border-radius: 4px;"/>
                  <div style="flex: 1;">
                    <div style="margin-bottom: 4px;">{{ product.name }}</div>
                    <div style="color: #999;">¥{{ product.price }} × {{ product.quantity }}</div>
                  </div>
                </div>
              </div>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 16px;">
                <div>实付款：<span style="color: var(--color-primary-6); font-size: 16px; font-weight: bold;">¥{{ item.total }}</span></div>
                <div style="display: flex; gap: 8px;">
                  <a-button
                    v-if="item.status === '待发货'"
                    type="primary"
                    size="small"
                  >提醒发货</a-button>
                  <a-button
                    v-if="item.status === '待收货'"
                    type="primary"
                    size="small"
                  >确认收货</a-button>
                  <a-button
                    v-if="item.status === '已完成'"
                    type="primary"
                    size="small"
                  >再次购买</a-button>
                </div>
              </div>
            </a-card>
          </a-list-item>
        </template>
        <template #empty>
          <a-empty description="暂无订单" />
        </template>
      </a-list>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Orders',
  data() {
    return {
      activeStatus: 'all',
      orderList: [
        {
          id: 1,
          orderNo: 'DD20240101001',
          status: '待发货',
          total: 398.00,
          createTime: '2024-01-01 12:30:45',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            },
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 2,
          orderNo: 'DD20240101002',
          status: '待收货',
          total: 598.00,
          createTime: '2024-01-02 14:20:30',
          products: [
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 2,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 3,
          orderNo: 'DD20240101003',
          status: '已完成',
          total: 199.00,
          createTime: '2024-01-03 09:15:20',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 4,
          orderNo: 'DD20240101004',
          status: '待发货',
          total: 897.00,
          createTime: '2024-01-04 16:45:12',
          products: [
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 3,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 5,
          orderNo: 'DD20240101005',
          status: '待收货',
          total: 398.00,
          createTime: '2024-01-05 10:30:00',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 2,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 6,
          orderNo: 'DD20240101006',
          status: '已完成',
          total: 498.00,
          createTime: '2024-01-06 11:20:15',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            },
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 7,
          orderNo: 'DD20240101007',
          status: '待发货',
          total: 398.00,
          createTime: '2024-01-01 12:30:45',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            },
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 8,
          orderNo: 'DD20240101008',
          status: '待收货',
          total: 598.00,
          createTime: '2024-01-02 14:20:30',
          products: [
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 2,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 9,
          orderNo: 'DD20240101009',
          status: '已完成',
          total: 199.00,
          createTime: '2024-01-03 09:15:20',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 10,
          orderNo: 'DD20240101010',
          status: '待发货',
          total: 897.00,
          createTime: '2024-01-04 16:45:12',
          products: [
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 3,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 11,
          orderNo: 'DD20240101011',
          status: '待收货',
          total: 398.00,
          createTime: '2024-01-05 10:30:00',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 2,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        },
        {
          id: 12,
          orderNo: 'DD20240101012',
          status: '已完成',
          total: 498.00,
          createTime: '2024-01-06 11:20:15',
          products: [
            {
              id: 1,
              name: '2024新款连衣裙',
              price: 199.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            },
            {
              id: 2,
              name: '时尚休闲运动鞋',
              price: 299.00,
              quantity: 1,
              image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
            }
          ]
        }
      ]
    }
  },
  computed: {
    filteredOrders() {
      if (this.activeStatus === 'all') {
        return this.orderList
      }
      const statusMap = {
        'pending': '待发货',
        'shipping': '待收货',
        'completed': '已完成'
      }
      return this.orderList.filter(order => order.status === statusMap[this.activeStatus])
    }
  },
  methods: {
    getStatusColor(status) {
      const colorMap = {
        '待发货': 'orange',
        '待收货': 'blue',
        '已完成': 'green'
      }
      return colorMap[status] || 'default'
    }
  }
}
</script>

<style scoped>
/* 移除所有类选择器的样式 */
</style>
