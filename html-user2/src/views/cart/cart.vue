<template>
  <div style="padding: 10px; min-height: 360px;">
    <!-- 结算栏（非吸顶） -->
    <div
        v-if="cartItems.length"
        style="max-width: 800px; margin: 0 auto; background: #fff;
        padding: 0 24px 16px 24px; display: flex; justify-content: space-between; align-items: center;"
    >
      <div style="display: flex; align-items: center;">
        <a-checkbox
            v-if="!isEditMode"
            :model-value="isAllSelected"
            :indeterminate="isIndeterminate"
            @change="handleSelectAll"
        >全选
        </a-checkbox>
      </div>
      <div style="display: flex; align-items: center; gap: 24px;">
        <span style="font-size: 18px; font-weight: bold;">
          <!--总计: -->
          <span class="price-color"> ¥{{ total }}</span>
        </span>
        <a-button type="primary" size="medium" @click="handleCheckout">
          去结算({{ totalQuantity }}件)
        </a-button>
      </div>
    </div>


    <!-- 空购物车提示 -->
    <div
        v-if="!cartItems.length"
        style="text-align: center; color: var(--color-primary-hover); padding: 32px 0;"
    >购物车是空的
    </div>


    <!-- 商品列表 -->
    <div v-else >
      <div
          v-for="item in cartItems"
          :key="item.id"
          style="width: 800px; margin-bottom: 16px; border: 1px solid #e8e8e8; border-radius: 4px; padding: 10px; transition: all 0.3s; height: 70px; box-sizing: border-box;"
      >
        <div style="display: flex; align-items: center; justify-content: space-between;">
          <a-checkbox
              v-model="selectedItems"
              :value="item.id"
              style="margin-right: 16px;"
          />
          <img :src="item.image" alt="商品图片"
               style="width: 50px; height: 50px; object-fit: cover;
          "/>
          <div
              style="flex: 1; text-align: left; display: flex; align-items: center; justify-content: space-between; gap: 24px;">
            <h4 style="color: #000; margin: 0; width: 200px; padding: 0 0 0 10px;">{{ item.name }}</h4>
            <p style=" font-size: 16px; margin: 0; width: 100px;"  class="price-color">¥{{ item.price }}</p>
            <div style="display: flex; align-items: center; gap: 16px;">
              <span style="font-size: 14px; width: 80px;" class="gray-text">库存: {{ item.stock }}</span>
              <a-input-number
                  style="width: 150px;"
                  mode="button"
                  v-model="item.quantity"
                  :min="1"
                  :max="1000000"
                  size="small"
                  @change="(value) => handleQuantityChange(item, value)"
              />
            </div>
          </div>
          <a-button style="margin-left: 10px;" v-if="!isEditMode" type="text" status="danger" @click="removeItem(item)">
            删除
          </a-button>
        </div>
      </div>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <div class="pagination-section">
          <a-pagination
              v-model:page-size="querySearch.pageSize"
              v-model:current="querySearch.pageNum"
              :total="total"
              show-total
              show-jumper
              @change="handlePageChange"
          />
        </div>
      </div>
    </div>


    </div>
</template>

<script>
export default {
  name: 'Cart',
  data () {
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
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
        {
          id: 2,
          name: '时尚休闲运动鞋',
          price: 299.00,
          quantity: 2,
          stock: 5,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667',
        },
      ],
      querySearch: null,
      total: 0,
    }
  },
  computed: {
    isAllSelected () {
      return this.cartItems.length === this.selectedItems.length
    },
    isIndeterminate () {
      return this.selectedItems.length > 0 && !this.isAllSelected
    },
    total () {
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)
    },
    totalQuantity () {
      return this.cartItems.reduce((sum, item) => sum + item.quantity, 0)
    },
  },
  created () {
    this.resetQuerySearch()
  },
  methods: {
    resetQuerySearch(){
      this.querySearch = {
        pageNum: 1,
        pageSize: 10,
      }
    },
    handlePageChange(){

    },
    toggleEditMode () {
      this.isEditMode = !this.isEditMode
      this.selectedItems = []
    },
    handleSelectAll (checked) {
      this.selectedItems = checked
          ? this.cartItems.map(item => item.id)
          : []
    },
    handleBatchDelete () {
      this.cartItems = this.cartItems.filter(
          item => !this.selectedItems.includes(item.id),
      )
      this.selectedItems = []
    },
    removeItem (item) {
      const index = this.cartItems.findIndex(cartItem => cartItem.id === item.id)
      if (index !== -1) {
        this.cartItems.splice(index, 1)
      }
    },
    handleQuantityChange (item, value) {
      item.quantity = value
    },
    handleCheckout () {
      // TODO: 实现结算逻辑
      console.log('去结算', this.cartItems)
    },
  },
}
</script>
