<template>
  <div class="cart-container">
    <div class="header">
      <span>
        共<span class="highlight">{{ cartItems.length }}</span>件商品
      </span>
      <a-button
        size="small"
        type="primary"
        style="margin-left: 10px"
        @click="toggleEditMode"
      >
        {{ isEditMode ? '完成' : '编辑' }}
      </a-button>
    </div>
    <div v-if="!cartItems.length" class="empty-tip">购物车是空的</div>
    <div v-else class="cart-list">
      <div class="batch-actions" v-if="isEditMode">
        <a-checkbox
          :model-value="isAllSelected"
          :indeterminate="isIndeterminate"
          @change="handleSelectAll"
        >全选</a-checkbox>
        <a-button
          type="primary"
          status="danger"
          @click="handleBatchDelete"
          :disabled="!selectedItems.length"
          size="small"
        >
          删除({{ selectedItems.length }})
        </a-button>
      </div>
      <div v-for="item in cartItems" :key="item.id" class="cart-item">
        <div class="cart-content">
          <a-checkbox
            v-if="isEditMode"
            v-model="selectedItems"
            :value="item.id"
            class="item-checkbox"
          />
          <img :src="item.image" alt="商品图片" class="product-image" />
          <div class="product-info">
            <h4>{{ item.name }}</h4>
            <p class="price">¥{{ item.price }}</p>
            <div class="quantity-control">
              <span class="stock-info">库存: {{ item.stock }}</span>
              <a-input-number
                v-model="item.quantity"
                :min="1"
                :max="999"
                size="small"
                @change="(value) => handleQuantityChange(item, value)"
              />
            </div>
          </div>
          <a-button v-if="!isEditMode" type="text" status="danger" @click="removeItem(item)">
            删除
          </a-button>
        </div>
      </div>
    </div>
    <div class="cart-footer" v-if="cartItems.length">
      <div class="footer-left">
        <a-checkbox
          v-if="!isEditMode"
          :model-value="isAllSelected"
          :indeterminate="isIndeterminate"
          @change="handleSelectAll"
        >全选</a-checkbox>
      </div>
      <div class="footer-right">
        <span class="total">总计: ¥{{ total }}</span>
        <a-button type="primary" size="large" @click="handleCheckout">
          去结算({{ totalQuantity }}件)
        </a-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Cart',
  data() {
    return {
      isEditMode: false,
      selectedItems: [],
      cartItems: [
        {
          id: 1,
          name: '2024新款连衣裙',
          price: 199.00,
          quantity: 1,
          stock: 10,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        }
      ]
    }
  },
  computed: {
    isAllSelected() {
      return this.cartItems.length === this.selectedItems.length
    },
    isIndeterminate() {
      return this.selectedItems.length > 0 && !this.isAllSelected
    },
    total() {
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)
    },
    totalQuantity() {
      return this.cartItems.reduce((sum, item) => sum + item.quantity, 0)
    }
  },
  methods: {
    toggleEditMode() {
      this.isEditMode = !this.isEditMode
      this.selectedItems = []
    },
    handleSelectAll(checked) {
      this.selectedItems = checked
        ? this.cartItems.map(item => item.id)
        : []
    },
    handleBatchDelete() {
      this.cartItems = this.cartItems.filter(
        item => !this.selectedItems.includes(item.id)
      )
      this.selectedItems = []
    },
    removeItem(item) {
      const index = this.cartItems.findIndex(cartItem => cartItem.id === item.id)
      if (index !== -1) {
        this.cartItems.splice(index, 1)
      }
    },
    handleQuantityChange(item, value) {
      item.quantity = value
    },
    handleCheckout() {
      // TODO: 实现结算逻辑
      console.log('去结算', this.cartItems)
    }
  }
}
</script>

<style scoped>
.cart-container {
  padding: 25px;
  min-height: 360px;
}

.header {
  display: flex;
  justify-content: start;
  align-items: center;
  margin-bottom: 16px;
}

.header span {
  color: #333;
}

.highlight {
  color: var(--color-primary) !important;
}

.empty-tip {
  text-align: center;
  color: var(--color-primary-hover);
  padding: 32px 0;
}

.cart-item {
  width: 800px;
  margin-bottom: 16px;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 10px;
  transition: all 0.3s;
  height: 70px;
}

.cart-item:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.batch-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}

.item-checkbox {
  margin-right: 16px;
}

.cart-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.product-image {
  width: 70px;
  height: 70px;
  object-fit: cover;
  margin-right: 24px;
}

.product-info {
  flex: 1;
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
}

.product-info h4 {
  color: #000;
  margin: 0;
  width: 200px;
}

.price {
  color: var(--color-primary-6);
  font-size: 16px;
  margin: 0;
  width: 100px;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stock-info {
  color: #999;
  font-size: 14px;
}

.cart-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: #fff;
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.09);
}

.footer-left {
  display: flex;
  align-items: center;
}

.footer-right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.total {
  font-size: 18px;
  font-weight: bold;
  color: var(--color-primary-6);
}
</style>