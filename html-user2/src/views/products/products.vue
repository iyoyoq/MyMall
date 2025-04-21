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
          <icon-search/>
        </template>
      </a-input-search>
    </div>

    <!-- 分类导航 -->
    <div class="category-wrapper">
      <div class="category-section">
        <a-tabs v-model:activeKey="activeCategory">
          <a-tab-pane key="all" title="全部"></a-tab-pane>
          <a-tab-pane v-for="category in categories"
                      :key="category.id"
                      :title="category.name"
          ></a-tab-pane>
        </a-tabs>
      </div>
    </div>

    <!-- 商品列表 -->
    <div class="products-grid">
      <div class="products-wrapper">
        <div class="product-item" v-for="product in displayedProducts" :key="product.id">
          <a-card class="product-card" :bordered="false">
            <div class="product-image-wrapper">
              <img
                  :src="product.image"
                  alt="产品图片"
                  class="product-image"
              />
            </div>
            <div class="product-info">
              <div class="product-title">{{ product.name }}</div>
              <div class="product-bottom">
                <div class="product-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-value">{{ product.price }}</span>
                </div>
                <a-button size="mini"  type="outline">加入购物车</a-button>
              </div>
            </div>
          </a-card>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div class="pagination-wrapper">
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
  </div>
</template>

<script>
import { IconSearch } from '@arco-design/web-vue/es/icon'

export default {
  name: 'Products',
  components: {
    IconSearch
  },

  data() {
    return {
      searchKeyword: '',
      activeCategory: 'all',
      currentPage: 1,
      total: 100,
      pageSize: 24,
      products: [],
      displayedProducts: [],
      categories: [
        { id: 1, name: '手机数码' },
        { id: 2, name: '电脑办公' },
        { id: 3, name: '家用电器' },
        { id: 4, name: '服装鞋包' },
        { id: 5, name: '食品生鲜' },
      ]
    }
  },

  methods: {
    generateProducts(count) {
      const productTypes = ['智能手机', '笔记本电脑', '平板电脑', '智能手表', '蓝牙耳机']
      const brands = ['小米', '华为', '苹果', '三星', '联想']
      const products = []

      for (let i = 1; i <= count; i++) {
        const typeIndex = Math.floor(Math.random() * productTypes.length)
        const brandIndex = Math.floor(Math.random() * brands.length)
        const price = Math.floor(Math.random() * (9999 - 99)) + 99

        products.push({
          id: i,
          name: `${brands[brandIndex]} ${productTypes[typeIndex]} ${i}号`,
          price: price,
          image: `https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667`,
        })
      }
      return products
    },

    handleSearch(value) {
      console.log('搜索关键词：', value)
      // 实现搜索逻辑
    },

    handlePageChange(page) {
      this.currentPage = page
      this.updateDisplayedProducts()
    },

    updateDisplayedProducts() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      this.displayedProducts = this.products.slice(start, end)
    }
  },

  mounted() {
    this.products = this.generateProducts(50)
    this.total = this.products.length
    this.updateDisplayedProducts()
  }
}
</script>

<style scoped>
.products-container {
  padding: 20px;
}

.search-section {
  margin: 0 0 10px 20px;
  width: 250px;
}

.category-wrapper {
  overflow-x: auto;
  margin-bottom: 20px;
  /* 隐藏滚动条但保持功能 */
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.category-wrapper::-webkit-scrollbar {
  display: none; /* Chrome, Safari and Opera */
}

.category-section {
  min-width: min-content; /* 确保内容不会被压缩 */
}

.products-grid {
  margin-bottom: 20px;
  padding: 0 10px;
}

.products-wrapper {
  display: grid;
  gap: 16px;
  /* 默认8列布局 (最宽屏幕) */
  grid-template-columns: repeat(8, 200px);
}

/* 屏幕宽度小于1800px时，切换为6列布局 */
@media screen and (max-width: 1800px) {
  .products-wrapper {
    grid-template-columns: repeat(6, 200px);
  }
}

/* 屏幕宽度小于1400px时，切换为4列布局 */
@media screen and (max-width: 1400px) {
  .products-wrapper {
    grid-template-columns: repeat(4, 200px);
  }
}

/* 屏幕宽度小于1000px时，切换为3列布局 */
@media screen and (max-width: 1000px) {
  .products-wrapper {
    grid-template-columns: repeat(3, 200px);
  }
}

/* 屏幕宽度小于  px时，切换为 2列布局 */
@media screen and (max-width: 800px) {
  .products-wrapper {
    grid-template-columns: repeat(2, 200px);
  }
}

@media screen and (max-width: 550px) {
  .products-wrapper {
    grid-template-columns: repeat(1, 200px);
  }
}

.product-item {
  width: 200px;
  margin: 0 auto;
}

.product-card {
  width: 220px;
  height: 200px;
  transition: all 0.3s;
  background: #fff;
  padding: 0;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.product-image-wrapper {
  width: 100%;
  height: 120px;
  overflow: hidden;
  background: #f5f5f5;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info {
  padding: 8px 12px;
}

.product-title {
  font-size: 14px;
  color: #333;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.product-price {
  display: flex;
  align-items: baseline;
}

.price-symbol {
  font-size: 14px;
  font-weight: bold;
  color: var(--mymall-price-color);
}

.price-value {
  font-size: 16px;
  font-weight: bold;
  color: var(--mymall-price-color);
}

.pagination-wrapper {
  overflow-x: auto;
  margin-top: 20px;
  /* 隐藏滚动条但保持功能 */
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.pagination-wrapper::-webkit-scrollbar {
  display: none;
}

.pagination-section {
  min-width: min-content;
  display: flex;
  justify-content: center;
  padding: 0 20px;
}
</style>
