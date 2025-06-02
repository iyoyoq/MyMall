<template>
  <div class="container">
    <div class="header">
      <a-row>
        <a-col :span="12">
          <!-- 订单管理通常不需要添加按钮，如果需要可以自行添加 -->
        </a-col>
        <a-col :span="12" style="text-align: right">
          <a-space>
            <a-input-search
                v-model="orderListApiParam.userId"
                placeholder="请输入用户ID"
                style="width: 200px"
                @search="handleSearch"
            />
            <a-select
                v-model="orderListApiParam.status"
                placeholder="请选择订单状态"
                style="width: 120px"
                allow-clear
                @change="handleSearch"
            >
              <a-option :value="10">待支付</a-option>
              <a-option :value="20">待发货</a-option>
              <a-option :value="30">待收货</a-option>
              <a-option :value="40">已完成</a-option>
              <a-option :value="5">已取消</a-option>
            </a-select>
          </a-space>
        </a-col>
      </a-row>
    </div>

    <a-table
        :loading="loading"
        :data="tableData"
        :pagination="false"
    >
      <template #columns>
        <a-table-column title="订单号" data-index="order.id"/>
        <a-table-column title="用户ID" data-index="order.userId"/>
        <a-table-column title="总金额">
          <template #cell="{ record }">
            {{ priceShowDecimalUtil(record.order.totalAmount) }}
          </template>
        </a-table-column>
        <a-table-column title="订单状态">
          <template #cell="{ record }">
            {{ getOrderStatusText(record.order.status) }}
          </template>
        </a-table-column>
        <a-table-column title="创建时间" data-index="order.createTime"/>
        <a-table-column title="操作" :width="200">
          <template #cell="{ record }">
            <a-space>
              <a-button type="text" size="small" @click="viewOrderDetails(record)">
                <icon-eye/>
                查看详情
              </a-button>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>

    <div style="display: flex; justify-content: center; margin-top: 24px;">
      <a-pagination
          v-model:current="orderListApiParam.pageNum"
          v-model:pageSize="orderListApiParam.pageSize"
          :total="total"
          :show-size-changer="true"
          :show-total="true"
          @change="handlePageChange"
      />
    </div>

    <a-modal
        v-model:visible="modalVisible"
        title="订单详情"
        @cancel="resetModalData()"
        :footer="false"
        width="800px"
    >
      <div v-if="selectedOrder">
        <p><strong class="order-detail-left">订单号:</strong> {{ selectedOrder.order.id }}</p>
        <p><strong class="order-detail-left">用户ID:</strong> {{ selectedOrder.order.userId }}</p>
        <p><strong class="order-detail-left">总金额:</strong> {{
            priceShowDecimalUtil(selectedOrder.order.totalAmount)
          }}</p>
        <p><strong class="order-detail-left"> 状态:</strong> {{ getOrderStatusText(selectedOrder.order.status) }}</p>
        <p><strong class="order-detail-left">收货人:</strong> {{ selectedOrder.address.receiverName }}</p>
        <p><strong class="order-detail-left">联系电话:</strong> {{ selectedOrder.address.receiverPhone }}</p>
        <p><strong class="order-detail-left">收货地址:</strong> {{
            selectedOrder.address.province
          }}{{ selectedOrder.address.city }}{{ selectedOrder.address.district }}{{
            selectedOrder.address.detailAddress
          }}</p>
        <h3>商品列表</h3>
        <a-table :data="selectedOrder.orderDetailList" :pagination="false">
          <template #columns>
            <a-table-column title="商品名称" data-index="sku.productName"/>
            <a-table-column title="规格" data-index="sku.skuAttrValues"/>
            <a-table-column title="单价">
              <template #cell="{ record }">
                {{ priceShowDecimalUtil(record.orderDetail.price) }}
              </template>
            </a-table-column>
            <a-table-column title="运费">
              <template #cell="{ record }">
                {{ priceShowDecimalUtil(record.orderDetail.shippingFee) }}
              </template>
            </a-table-column>
            <a-table-column title="数量" data-index="orderDetail.quantity"/>
            <a-table-column title="当前库存" data-index="sku.stockQuantity"/>
          </template>
        </a-table>

        <div>
          <h3>物流信息</h3>
          <div style="display: flex;">
            <div style="width: 100px">物流单号</div>
            <a-input v-model="inputLogisticsCode" :disabled="selectedOrder.order.status!==20"/>
          </div>
          <div style="display: flex; justify-content: center;">
            <div style="margin: 10px 0 0 0">
              <a-button v-if="selectedOrder.order.status===20" @click="clickDelivery()">确认发货</a-button>
            </div>
          </div>
        </div>

      </div>
    </a-modal>
  </div>
</template>

<script>
import { deliveryApi, detailOrderApi, getOrderStatusText, orderListsApi } from '@/api/order.js'
import {
  IconEye,
} from '@arco-design/web-vue/es/icon'
import { Message } from '@arco-design/web-vue'
import { priceShowDecimalUtil } from '@/utils/price.js' // 导入 priceShowDecimalUtil

export default {
  name: 'OrderList',
  components: {
    IconEye,
  },
  data () {
    return {
      loading: false,
      tableData: [],
      modalVisible: false,
      selectedOrder: null,
      orderListApiParam: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        status: null,
      },
      total: 0,
      inputLogisticsCode: '',
    }
  },
  created () {
    this.fetchOrderList()
  },
  methods: {
    getOrderStatusText,
    priceShowDecimalUtil, // 注册 priceShowDecimalUtil 方法
    handlePageChange () {
      this.fetchOrderList()
    },
    async fetchOrderList () {
      this.loading = true
      try {
        const resp = await orderListsApi(this.orderListApiParam)
        this.tableData = resp.data.result.page.records
        // console.log('tableData', this.tableData)
        this.total = resp.data.result.page.total
      } finally {
        this.loading = false
      }
    },
    handleSearch () {
      this.orderListApiParam.pageNum = 1
      this.fetchOrderList()
    },
    async viewOrderDetails (record) {
      // console.log('查看订单详情', record)
      const resp = await detailOrderApi(record.order.id)
      const result = resp.data.result
      this.inputLogisticsCode = result.order.logisticsCode
      // console.log('result', result)
      this.selectedOrder = result // 将返回的 result 对象直接赋值给 selectedOrder
      this.modalVisible = true
    },
    async clickDelivery () {
      const resp = await deliveryApi({
        orderId: this.selectedOrder.order.id,
        logisticsCode: this.inputLogisticsCode,
      })
      // console.log('resp', resp)
      await this.viewOrderDetails(this.selectedOrder)
      Message.success('成功更新订单状态：已发货')
    },
    resetModalData () {
      this.modalVisible = false
      this.selectedOrder = null
      this.inputLogisticsCode = ''
    },
  },
}
</script>

<style scoped>
.header {
  margin-bottom: 20px;
}

.order-detail-left {
  width: 65px;
  display: inline-block;
}
</style>
