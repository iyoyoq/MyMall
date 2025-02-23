<template>
  <div class="orders-container">
    <h2>订单管理</h2>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="all" tab="全部订单">
        <a-list
          v-if="orderList.length"
          :data-source="orderList"
          :pagination="{ pageSize: 10 }"
        >
          <template #renderItem="{ item }">
            <a-list-item>
              <a-card style="width: 100%">
                <template #title>
                  订单号：{{ item.orderNo }}
                </template>
                <template #extra>
                  <a-tag :color="item.status === '待付款' ? 'red' : 'green'">
                    {{ item.status }}
                  </a-tag>
                </template>
                <p>订单金额：¥{{ item.total }}</p>
                <p>创建时间：{{ item.createTime }}</p>
              </a-card>
            </a-list-item>
          </template>
        </a-list>
        <a-empty v-else description="暂无订单" />
      </a-tab-pane>
      <a-tab-pane key="pending" tab="待付款" />
      <a-tab-pane key="processing" tab="处理中" />
      <a-tab-pane key="completed" tab="已完成" />
    </a-tabs>
  </div>
</template>

<script>
export default {
  name: 'Orders',
  data() {
    return {
      activeKey: 'all',
      orderList: []
    }
  }
}
</script>

<style scoped>
.orders-container {
  padding: 24px;
  background: #fff;
  min-height: 360px;
  border-radius: 4px;
}
</style> 