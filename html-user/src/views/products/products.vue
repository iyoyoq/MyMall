<template>
  <div class="products-container">
    <!-- 搜索区域 -->
    <div class="search-section">
      <a-input-search
        v-model="searchKeyword"
        placeholder="请输入商品名称"
        search-button
        @search="handleSearch"
      >
        <template #button-icon>
          <icon-search />
        </template>
      </a-input-search>
    </div>

    <!-- 分类导航 -->
    <div class="category-section">
      <a-tabs v-model:activeKey="activeCategory">
        <a-tab-pane key="all" title="全部"></a-tab-pane>
        <a-tab-pane v-for="category in categories" 
          :key="category.id" 
          :title="category.name"
        ></a-tab-pane>
      </a-tabs>
    </div>

    <!-- 商品列表 -->
    <div class="products-grid">
      <a-row :gutter="[16, 16]">
        <a-col :span="6" v-for="product in products" :key="product.id">
          <a-card class="product-card" :bordered="false">
            <img :src="product.image" class="product-image" />
            <div class="product-title">{{ product.name }}</div>
            <div class="product-price">¥{{ product.price }}</div>
          </a-card>
        </a-col>
      </a-row>
    </div>

    <!-- 分页 -->
    <div class="pagination-section">
      <a-pagination
        v-model:current="currentPage"
        :total="total"
        show-total
        show-jumper
        @change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { IconSearch } from '@arco-design/web-vue/es/icon'

// 数据
const searchKeyword = ref('')
const activeCategory = ref('all')
const currentPage = ref(1)
const total = ref(100)

// 模拟数据
const categories = ref([
  { id: 1, name: '手机数码' },
  { id: 2, name: '电脑办公' },
  { id: 3, name: '家用电器' },
  { id: 4, name: '服装鞋包' },
  { id: 5, name: '食品生鲜' }
])

const products = ref([
  {
    id: 1,
    name: '示例商品1',
    price: 999,
    image: 'https://placeholder.com/300x300'
  },
  // 添加更多示例商品...
])

// 方法
const handleSearch = (value) => {
  console.log('搜索关键词：', value)
  // 实现搜索逻辑
}

const handlePageChange = (page) => {
  currentPage.value = page
  // 实现分页加载逻辑
}

onMounted(() => {
  // 初始化加载数据
})
</script>

<style scoped>
.products-container {
  padding: 20px;
}

.search-section {
  margin-bottom: 20px;
}

.category-section {
  margin-bottom: 20px;
}

.products-grid {
  margin-bottom: 20px;
}

.product-card {
  transition: all 0.3s;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.product-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
}

.product-title {
  margin: 8px 0;
  font-size: 14px;
  color: var(--color-text-1);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-price {
  color: var(--color-primary-6);
  font-size: 16px;
  font-weight: bold;
}

.pagination-section {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style> 