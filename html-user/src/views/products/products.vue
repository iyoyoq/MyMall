<template>
  <div class="products-container">
    <!-- 搜索框 -->
    <div class="product-header">
      <div class="search-box">
        <var-form @submit="handleSearch">
          <var-input
              v-model="dto.name"
              placeholder="搜索商品"
              clearable
              native-type="submit"
          >
            <template #append-icon>
              <var-icon name="magnify"/>
            </template>
          </var-input>
        </var-form>
      </div>

      <!-- 分类选择 -->
      <div class="category-tabs">
        <var-tabs v-model:active="activeCategory">
          <var-tab>全部</var-tab>
          <var-tab v-for="category in categories" :key="category.id">
            {{ category.name }}
          </var-tab>
        </var-tabs>
      </div>
    </div>

    <div class="product-content">
      <!-- 商品列表 -->
      <div class="products-grid">
        <var-card
            v-for="product in products"
            :key="product.id"
            class="product-card"
        >
          <img :src="product.mainImage" class="product-image"/>
          <div class="product-title">{{ product.name }}</div>
          <div class="product-price">¥{{ product.price }}</div>
        </var-card>
      </div>

      <!-- 分页 -->
      <div class="foot">
        <var-pagination
            v-model:current="pageNum"
            :total="total"
            :size="pageSize"
            :show-size-changer="false"
            @change="handlePageChange"
            :show-total="total => `共 ${total} 条`"
        />
      </div>
    </div>
  </div>
</template>

<script>

import { productCategoryListApi, productListApi } from '@/api/product.js'

export default {
  name: 'Products',
  created () {
    this.fetchList()
  },
  data () {
    return {
      activeCategory: 0,
      pageNum: 1,
      total: 0,
      pageSize: 25,
      products: [],
      categories: [],
      dto: {
        name: '',  //商品名称
        categoryId: '',  //分类id
      },
    }
  },
  methods: {
    async fetchList () {
      productListApi({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        dto: this.dto,
      }).then((resp) => {
        this.products = resp.data.result.records
        this.total = resp.data.result.total
      })
      productCategoryListApi().then((resp) => {
        this.categories = resp.data.result
      })
    },
    handleSearch () {
      this.fetchList()
    },
    handlePageChange (page) {
      this.currentPage = page
      const start = (page - 1) * this.pageSize
      const end = start + this.pageSize
    },
  },
}
</script>


<style lang="scss" scoped>
.products-container {
  padding: 0;

  .product-header {
    display: flex;
    justify-content: start;

    .search-box {
      margin: 0 16px 16px 0;
      width: 200px;
    }

    .category-tabs {
      margin: 8px 0 16px 0;
      width: 600px;
    }
  }

  .product-content {
    margin-top: 20px;

    .products-grid {
      display: grid;
      grid-template-columns: repeat(5, 1fr); // 改为5列
      gap: 24px;
      margin-bottom: 24px;

      .product-card {
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: none;
        border: 1px solid transparent;
        // 移除 aspect-ratio: 1
        padding: 12px;
        display: flex;
        flex-direction: column;

        &:hover {
          transform: scale(1.05);
          border-color: var(--color-primary);
        }

        .product-image {
          width: 100%;
          aspect-ratio: 1; // 只让图片保持1:1
          object-fit: cover;
          margin-bottom: 8px;
        }

        .product-title {
          margin-bottom: 8px;
          font-size: 14px;
          line-height: 1.4;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .product-price {
          margin-top: auto; // 价格固定在底部
          font-size: 16px;
          font-weight: bold;
          color: #f44336;
        }
      }
    }

    .foot {
      display: flex;
      justify-content: center;
      margin: 0 0 20px 0;
    }
  }
}
</style>

