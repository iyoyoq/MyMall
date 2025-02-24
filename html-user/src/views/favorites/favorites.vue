<template>
  <div class="favorites-container">
    <div class="header">
      <span>
        共<span class="highlight">{{ favoritesList.length }}</span>件商品
      </span>
      <button class="edit-button" >编辑</button>
    </div>
    <div v-if="!favoritesList.length" class="empty-tip">暂无收藏</div>
    <div v-else class="favorites-list">
      <div v-for="item in favoritesList" :key="item.id" class="favorite-item">
        <div class="favorite-content">
          <img :src="item.image" alt="商品图片" class="product-image" />
          <div class="product-info">
            <h4>{{ item.title }}</h4>
            <p class="price">¥{{ item.price }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Favorites',
  data() {
    return {
      favoritesList: [
        {
          id: 1,
          title: '2024新款连衣裙',
          price: 199.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 2,
          title: '时尚休闲运动鞋',
          price: 299.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 3,
          title: '韩版时尚背包',
          price: 159.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 4,
          title: '纯棉休闲T恤',
          price: 89.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        }
      ]
    }
  },
  created() {
    console.log('收藏列表数据:', this.favoritesList)
  }
}
</script>

<style scoped>
.favorites-container {
  padding: 25px;
  min-height: 360px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.header span {
  color: #333;
}

.highlight {
  color: var(--color-primary) !important;
}

.edit-button {
  background-color: var(--color-primary);
  color: #fff;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: var(--color-primary-hover);
}

.empty-tip {
  text-align: center;
  color: var(--color-primary-hover);
  padding: 32px 0;
}

.favorite-item {
  width: 800px;
  margin-bottom: 16px;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 10px;
  transition: all 0.3s;
  height: 70px;
}

.favorite-item:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.favorite-content {
  display: flex;
  align-items: center;
  justify-content: flex-start;
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
}

.product-info h4 {
  color: #000;
  margin: 0;
}

.price {
  color: var(--color-primary-6);
  font-size: 16px;
  margin-top: 8px;
  text-align: left;
}

.favorites-container h2 {
  color: #000;
  margin-top: 0px;
  margin-left: 10px;
  text-align: left;
}
</style>
