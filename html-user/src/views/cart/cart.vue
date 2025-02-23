<template>
  <div class="cart-container">
    <h2>购物车</h2>
    <a-empty v-if="!cartItems.length" description="购物车是空的" />
    <template v-else>
      <a-table :columns="columns" :data-source="cartItems" :pagination="false">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-button type="link" danger @click="removeItem(record)">删除</a-button>
          </template>
        </template>
      </a-table>
      <div class="cart-footer">
        <span class="total">总计: ¥{{ total }}</span>
        <a-button type="primary" size="large">结算</a-button>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: 'Cart',
  data() {
    return {
      cartItems: [],
      columns: [
        { title: '商品', dataIndex: 'name', key: 'name' },
        { title: '单价', dataIndex: 'price', key: 'price' },
        { title: '数量', dataIndex: 'quantity', key: 'quantity' },
        { title: '操作', key: 'action' }
      ]
    }
  },
  computed: {
    total() {
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0)
    }
  },
  methods: {
    removeItem(record) {
      // 实现删除购物车商品的逻辑
    }
  }
}
</script>

<style scoped>
.cart-container {
  padding: 24px;
  background: #fff;
  min-height: 360px;
  border-radius: 4px;
}
.cart-footer {
  margin-top: 24px;
  text-align: right;
}
.total {
  margin-right: 24px;
  font-size: 16px;
  font-weight: bold;
}
</style> 