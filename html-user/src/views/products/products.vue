<template>
 <div>
   商品页
 </div>
</template>

<script>

export default {
  name: 'Products',


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

  },

  mounted() {

  }
}
</script>

<style scoped>

</style>
