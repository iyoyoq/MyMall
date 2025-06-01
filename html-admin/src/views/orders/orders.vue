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
              <a-option :value="10">待付款</a-option>
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
        <a-table-column title="订单号"  data-index="order.id"/>
        <a-table-column title="用户ID" data-index="order.userId"/>
        <a-table-column title="总金额" data-index="order.totalAmount"/>
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
        @cancel="modalVisible = false"
        :footer="false"
        width="800px"
    >
      <div v-if="selectedOrder">
        <p><strong>订单号:</strong> {{ selectedOrder.orderNo }}</p>
        <p><strong>用户ID:</strong> {{ selectedOrder.userId }}</p>
        <p><strong>总金额:</strong> {{ selectedOrder.totalAmount }}</p>
        <p><strong>状态:</strong> {{ getStatusText(selectedOrder.status) }}</p>
        <h3>商品列表</h3>
        <a-table :data="selectedOrder.orderItems" :pagination="false">
          <template #columns>
            <a-table-column title="商品名称" data-index="productName"/>
            <a-table-column title="数量" data-index="quantity"/>
            <a-table-column title="单价" data-index="price"/>
          </template>
        </a-table>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { getOrderStatusText, orderListsApi } from '@/api/order.js'

export default {
  name: 'OrderList',
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
    }
  },
  created () {
    this.fetchOrderList()
  },
  methods: {
    getOrderStatusText,
    handlePageChange(){
      this.fetchOrderList()
    },
    async fetchOrderList () {
      this.loading = true
      try {
        const resp = await orderListsApi(this.orderListApiParam)
        this.tableData = resp.data.result.page.records
        console.log('tableData', this.tableData)
        this.total = resp.data.result.page.total
      } finally {
        this.loading = false
      }
    },
    handleSearch () {
      this.orderListApiParam.pageNum = 1
      this.fetchOrderList()
    },
    viewOrderDetails (record) {
      this.selectedOrder = record
      this.modalVisible = true
    },
  },
}
</script>

<style scoped>
.header {
  margin-bottom: 20px;
}
</style>
