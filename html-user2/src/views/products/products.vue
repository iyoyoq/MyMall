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
        <a-tabs v-model:activeKey="activeCategory" @change="changeActiveCategory">
          <a-tab-pane key="all" title="全部"></a-tab-pane>
          <a-tab-pane v-for="category in categories"
                      :key="category.id.toString()"
                      :title="category.name"
          ></a-tab-pane>
        </a-tabs>
      </div>
    </div>

    <!-- 商品列表 -->
    <div class="products-grid">
      <div class="products-wrapper">
        <div class="product-item" @click="clickProductCard(product)" v-for="product in products" :key="product.id">
          <a-card class="product-card" :bordered="false">
            <div class="product-image-wrapper">
              <img
                  :src="product.mainImage"
                  alt="产品图片"
                  class="product-image"
              />
            </div>
            <div class="product-info">
              <div class="product-title">{{ product.name }}</div>
              <div class="product-bottom">
                <div class="product-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-value">{{ product.startingPrice }}</span>
                </div>
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
</template>

<script>
import { IconSearch } from '@arco-design/web-vue/es/icon'
import { productCategoryListApi, productListApi } from '@/api/product.js'
import router from '@/router/index.js'
import { hasValue } from '@/utils/check.js'

export default {
  name: 'Products',
  components: {
    IconSearch,
  },

  data () {
    return {
      searchKeyword: '',
      activeCategory: 'all',
      pageSize: 24,
      products: [],
      categories: [],
      total: 0,
      querySearch: null,
    }
  },
  created () {
    this.resetQuerySearch()
    this.fetchCategory()
    this.fetchProductList()
  },
  methods: {
    resetQuerySearch () {
      this.querySearch = {
        pageNum: 1,
        pageSize: 25,
        name: null,
        categoryId: null,
      }
    },
    changeActiveCategory (categoryId) {

      if (categoryId === 'all') this.querySearch.categoryId = null
      else this.querySearch.categoryId = categoryId

      this.fetchProductList()
    },
    clickProductCard (product) {
      router.push({
        path: '/product',
        query: {
          productId: product.id,
        },
      })
    },
    async fetchProductList () {
      const resp = await productListApi(this.querySearch)
      const result = resp.data.result
      this.products = result.records
      this.total = result.total
    },
    async fetchCategory () {
      const resp = await productCategoryListApi()
      const result = resp.data.result
      this.categories = result
    },

    handleSearch (value) {
      if (!hasValue(value)) {
        this.searchKeyword = ''
        this.resetQuerySearch()
        this.fetchProductList()
        return
      }
      // console.log('搜索关键词：', value)
      this.resetQuerySearch()
      this.querySearch.name = value
      this.fetchProductList()
    },

    handlePageChange (page) {
      // console.log(page)
      this.querySearch.pageNum = page
      this.fetchProductList()
    },

  },

  mounted () {
  },
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
  grid-template-columns: repeat(5, 200px);
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
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.product-image-wrapper {
  width: 100%;
  height: 120px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-image {
  max-width: 100%;
  max-height: 100%;
  width: auto;
  height: auto;
  object-fit: contain;
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
  height: 50px;
  /* 隐藏滚动条但保持功能 */
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.pagination-wrapper::-webkit-scrollbar {
  display: none;
}

.pagination-section {
  display: flex;
  justify-content: center;
  padding: 0 20px;
}
</style>
