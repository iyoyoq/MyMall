<template>
  <a-tree-select
      v-model="selectedValue"
      :data="categoryTreeData"
      placeholder="请选择分类"
      allow-clear
      @change="handleChange"
      style="width: 100%"
  />
</template>

<script>
import { productCategoryListApi } from '@/api/product.js'

export default {
  name: 'ProductCategorySelector',
  props: {
    // 默认选中的分类ID
    modelValue: {
      type: [String, Number],
      default: null,
    },
  },
  emits: ['update:modelValue', 'change'],
  data () {
    return {
      categoryList: [],
      loading: false,
    }
  },
  computed: {
    selectedValue: {
      get () {
        return this.modelValue?.toString()
      },
      set (value) {
        this.$emit('update:modelValue', value)
      },
    },
    categoryTreeData () {
      const treeData = this.categoryList.map(item => ({
        key: item.id.toString(),
        title: item.name,
        children: item.children?.map(child => ({
          key: child.id.toString(),
          title: child.name,
        })) || [],
      }))
      return [{ key: '-1', title: '顶级分类', children: treeData }]
    },
  },
  created () {
    this.fetchCategoryList()
  },
  methods: {
    async fetchCategoryList () {
      this.loading = true
      try {
        const res = await productCategoryListApi()
        this.categoryList = res.data.result
      } finally {
        this.loading = false
      }
    },
    handleChange (value) {
      this.$emit('change', value)
    },
  },
  watch: {
    modelValue: {
      handler() {
        // this.fetchCategoryList()
      }
    }
  },
}
</script>
