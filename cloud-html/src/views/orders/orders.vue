<template>
  <div style="padding: 24px; background: #fff; min-height: 360px; border-radius: 4px;width: 600px ">
    <div style="display: flex; justify-content: start; align-items: center; margin-bottom: 24px;">
      <h4 style="color: black;">我的订单</h4>
      <a-radio-group
          type="button"
          v-model="activeStatus"
          size="small"
          style="margin-left: 10px;"
          @change="changeStatusRadio()"
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
      <div v-for="item in orderList" :key="item.order.id">
        <a-card style="width: 100%; margin: 10px 0 0 0">
          <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
            <div style="display: flex; gap: 24px;">
              <span style="font-weight: bold;">订单号：{{ item.order.id }}</span>
              <span style="color: #999;">创建时间：{{ item.order.createTime }}</span>
            </div>
            <a-tag :color="getStatusColor(item.order.status)">{{ getOrderStatusText(item.order.status) }}</a-tag>
          </div>

          <!--订单商品-->
          <div style="border-top: 1px solid #f0f0f0; border-bottom: 1px solid #f0f0f0; padding: 16px 0;">
            <div v-for="singleDetail in item.orderDetailList" :key="singleDetail.orderDetail.id"
                 style="display: flex; align-items: center; margin-bottom: 12px;">
              <img :src="singleDetail.sku.mainImage" :alt="singleDetail.sku.productName"
                   style="width: 60px; height: 60px; object-fit: cover; margin-right: 12px; border-radius: 4px;"/>
              <div style="flex: 1;">
                <div style="margin-bottom: 4px;">{{ singleDetail.sku.productName }}</div>
                <div style="margin-bottom: 4px;" class="gray-text">{{ singleDetail.sku.skuAttrValues }}</div>
                <div>
                  <span class="price-color">¥{{ priceShowDecimalUtil(singleDetail.orderDetail.price) }}</span>
                  <span> × {{ singleDetail.orderDetail.quantity }}</span>
                </div>
              </div>
            </div>
          </div>
          <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 16px;">
            <div>
              <span>总金额：</span>
              <span style=" font-size: 16px; font-weight: bold;"
                    class="price-color">¥{{ priceShowDecimalUtil(item.order.totalAmount) }}</span>
            </div>
            <div style="display: flex; gap: 8px;">
              <a-button
                  v-if="item.order.status == 30"
                  type="primary"
                  size="small"
                  @click="clickOrderReceiveBtn(item.order.id)"
              >确认收货
              </a-button>
              <a-button
                  type="secondary"
                  size="small"
                  @click="clickOrderDetailBtn(item.order.id)"
              >订单详情
              </a-button>
            </div>
          </div>
        </a-card>
      </div>

      <div style="display: flex; justify-content: center; margin-top: 24px;">
        <a-pagination
            v-model:current="queryParams.pageNum"
            v-model:pageSize="queryParams.pageSize"
            :total="total"
            :show-size-changer="true"
            :page-size-options="[10, 20, 30, 40]"
            :show-total="true"
            @change="handlePageChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getOrderStatusText, orderListsApi, receiveOrderApi } from '@/api/order.js'
import router from '@/router/index.js'
import { priceShowDecimalUtil } from '@/utils/price.js'
import { Message, Modal } from '@arco-design/web-vue'

export default {
  name: 'Orders',
  data () {
    return {
      activeStatus: 'all',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: '',
        status: '',
      },
      total: 0,
      orderList: [],
    }
  },
  async mounted () {
    await this.fetchOrders()
  },
  methods: {
    getOrderStatusText,
    priceShowDecimalUtil,
    changeStatusRadio(){
      // console.log('activeStatus', this.activeStatus)
      this.queryParams.status = this.activeStatus.toString() === 'all' ? '' : this.activeStatus
      this.fetchOrders()
    },
    clickOrderReceiveBtn(orderId){
      Modal.open({
        title: '提示',
        content: '确定收货？',
        okText: '确定',
        cancelText: '取消',
        width: 400,
        onOk: async () => {
          await receiveOrderApi({
             orderId: orderId
          })
          await this.fetchOrders()
          Message.success('确认收货成功')
        },
        onCancel: () => {
        },
      })
    },
    clickOrderDetailBtn(orderId){
      router.push({
        path: '/order',
        query: {
          orderId: orderId,
        },
      })
    },
    async fetchOrders () {
      const resp = await orderListsApi(this.queryParams)
      const result = resp.data.result.page
      this.total = result.total
      this.orderList = result.records
      // console.log('orderList', this.orderList)
      // console.log('total', this.total)
    },
    handlePageChange (page, pageSize) {
      this.queryParams.pageNum = page
      this.queryParams.pageSize = pageSize
      this.fetchOrders()
    },
    getStatusColor (status) {
      const colorMap = {
        '10': 'red',       // 待支付
        '20': 'orange',    // 待发货
        '30': 'blue',      // 待收货
        '40': 'green',     // 已完成
        '5': 'gray',        // 已取消
      }
      return colorMap[status.toString()] || 'default'
    },
  },
  computed: {},
}
</script>

<style scoped>
/* 移除所有类选择器的样式 */
</style>
