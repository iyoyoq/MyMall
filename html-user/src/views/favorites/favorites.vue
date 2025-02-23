<template>
  <div class="favorites-container">
    <h2>我的收藏</h2>
    <a-empty v-if="!favoritesList.length" description="暂无收藏" />
    <a-list v-else :data-source="favoritesList" :grid="{ gutter: 16, column: 4 }">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-card hoverable>
            <template #cover>
              <img :src="item.image" alt="商品图片" />
            </template>
            <a-card-meta :title="item.title" :description="`¥${item.price}`" />
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script>
export default {
  name: 'Favorites',
  data() {
    return {
      favoritesList: []
    }
  }
}
</script>

<style scoped>
.favorites-container {
  padding: 24px;
  background: #fff;
  min-height: 360px;
  border-radius: 4px;
}
</style> 