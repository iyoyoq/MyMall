<template>
  <div class="container">
    <div class="header">
      <a-row>
        <a-col :span="12">
          <a-button type="primary" @click="openAddModal(null)">
            <template #icon><icon-plus /></template>
            添加分类
          </a-button>
        </a-col>
        <a-col :span="12" style="text-align: right">
          <a-input-search
            v-model="searchKeyword"
            placeholder="请输入分类名称"
            style="width: 300px"
            @search="handleSearch"
          />
        </a-col>
      </a-row>
    </div>

    <a-table
      :loading="loading"
      :data="tableData"
      :pagination="false"
      row-key="id"
      :expand-all-rows="true"
    >
      <template #columns>
        <a-table-column title="分类名称" data-index="name" />
        <a-table-column title="状态" data-index="status">
          <template #cell="{ record }">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '上架' : '下架' }}
            </a-tag>
          </template>
        </a-table-column>
        <a-table-column title="创建时间" data-index="createTime">
          <template #cell="{ record }">
            {{ formatDate(record.createTime) }}
          </template>
        </a-table-column>
        <a-table-column title="操作" width="200">
          <template #cell="{ record }">
            <a-space>
              <a-button type="text" size="small" @click="openAddModal(record)">
                <icon-edit />
                编辑
              </a-button>
              <a-button type="text" size="small" @click="openAddModal(null, record.id)">
                <icon-plus />
                添加子分类
              </a-button>
              <a-popconfirm
                content="确定要删除该分类吗？"
                @ok="handleDelete(record)"
              >
                <a-button type="text" status="danger" size="small">
                  <icon-delete />
                  删除
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>

    <!-- 新增/编辑分类弹窗 -->
    <a-modal
      v-model:visible="modalVisible"
      :title="modalTitle"
      @cancel="resetForm"
      @before-ok="handleSubmit"
    >
      <a-form :model="form" ref="formRef" :rules="rules" label-align="right">
        <a-form-item field="parentId" label="父级分类">
          <a-tree-select
            v-model="form.parentId"
            :data="categoryTreeData"
            placeholder="请选择父级分类"
            allow-clear
          />
        </a-form-item>
        <a-form-item field="name" label="分类名称" required>
          <a-input v-model="form.name" placeholder="请输入分类名称" />
        </a-form-item>
        <a-form-item field="status" label="状态">
          <a-radio-group v-model="form.status">
            <a-radio :value="1">上架</a-radio>
            <a-radio :value="0">下架</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { ref, reactive, onMounted, computed } from 'vue'
import { Message } from '@arco-design/web-vue'
import {
  IconPlus,
  IconEdit,
  IconDelete
} from '@arco-design/web-vue/es/icon'

export default {
  name: 'ProductCategory',
  components: {
    IconPlus,
    IconEdit,
    IconDelete
  },
  setup() {
    const loading = ref(false)
    const tableData = ref([])
    const searchKeyword = ref('')
    const modalVisible = ref(false)
    const modalTitle = ref('添加分类')
    const formRef = ref(null)
    const currentParentId = ref(null)

    // 表单数据
    const form = reactive({
      id: null,
      parentId: null,
      name: '',
      status: 1
    })

    // 表单验证规则
    const rules = {
      name: [
        { required: true, message: '请输入分类名称' }
      ]
    }

    // 转换为树形结构数据
    const categoryTreeData = computed(() => {
      const treeData = tableData.value.map(item => ({
        key: item.id,
        title: item.name,
        children: []
      }))
      return [{ key: 0, title: '顶级分类', children: treeData }]
    })

    // 获取分类列表
    const fetchCategoryList = async () => {
      loading.value = true
      try {
        // TODO: 替换为实际的API调用
        // const res = await getCategoryListApi()
        // tableData.value = handleTreeData(res.data)

        // 模拟数据
        setTimeout(() => {
          tableData.value = [
            {
              id: 1,
              parentId: 0,
              name: '电子产品',
              status: 1,
              createTime: new Date(),
              children: [
                {
                  id: 3,
                  parentId: 1,
                  name: '手机',
                  status: 1,
                  createTime: new Date()
                },
                {
                  id: 4,
                  parentId: 1,
                  name: '电脑',
                  status: 1,
                  createTime: new Date()
                }
              ]
            },
            {
              id: 2,
              parentId: 0,
              name: '服装',
              status: 1,
              createTime: new Date(),
              children: [
                {
                  id: 5,
                  parentId: 2,
                  name: '男装',
                  status: 0,
                  createTime: new Date()
                }
              ]
            }
          ]
          loading.value = false
        }, 500)
      } catch (error) {
        console.error('获取分类列表失败', error)
        loading.value = false
      }
    }

    // 将平铺数据转换为树形结构
    const handleTreeData = (data) => {
      const result = []
      const map = {}

      data.forEach(item => {
        map[item.id] = { ...item, children: [] }
      })

      data.forEach(item => {
        const parent = map[item.parentId]
        if (parent) {
          parent.children.push(map[item.id])
        } else {
          result.push(map[item.id])
        }
      })

      return result
    }

    // 打开新增/编辑弹窗
    const openAddModal = (record, parentId = null) => {
      resetForm()

      if (record) {
        // 编辑模式
        modalTitle.value = '编辑分类'
        Object.keys(form).forEach(key => {
          if (key in record) {
            form[key] = record[key]
          }
        })
      } else {
        // 新增模式
        modalTitle.value = '添加分类'
        form.parentId = parentId || 0
        currentParentId.value = parentId
      }

      modalVisible.value = true
    }

    // 重置表单
    const resetForm = () => {
      Object.keys(form).forEach(key => {
        form[key] = key === 'status' ? 1 : null
      })
      formRef.value?.clearValidate()
    }

    // 提交表单
    const handleSubmit = async (done) => {
      formRef.value?.validate(async (errors) => {
        if (errors) {
          done(false)
          return
        }

        try {
          const isEdit = !!form.id

          // TODO: 替换为实际的API调用
          // if (isEdit) {
          //   await updateCategoryApi(form)
          // } else {
          //   await addCategoryApi(form)
          // }

          // 模拟API调用
          await new Promise(resolve => setTimeout(resolve, 500))

          Message.success(`${isEdit ? '更新' : '添加'}分类成功`)
          fetchCategoryList()
          done()
        } catch (error) {
          console.error(`${isEdit ? '更新' : '添加'}分类失败`, error)
          Message.error(`${isEdit ? '更新' : '添加'}分类失败`)
          done(false)
        }
      })
    }

    // 删除分类
    const handleDelete = async (record) => {
      try {
        // TODO: 替换为实际的API调用
        // await deleteCategoryApi(record.id)

        // 模拟API调用
        await new Promise(resolve => setTimeout(resolve, 500))

        Message.success('删除分类成功')
        fetchCategoryList()
      } catch (error) {
        console.error('删除分类失败', error)
        Message.error('删除分类失败')
      }
    }

    // 搜索
    const handleSearch = () => {
      fetchCategoryList()
    }

    // 格式化日期
    const formatDate = (date) => {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    }

    onMounted(() => {
      fetchCategoryList()
    })

    return {
      loading,
      tableData,
      searchKeyword,
      modalVisible,
      modalTitle,
      form,
      formRef,
      rules,
      categoryTreeData,
      openAddModal,
      resetForm,
      handleSubmit,
      handleDelete,
      handleSearch,
      formatDate
    }
  }
}
</script>

<style scoped>
.container {
  margin: 20px 0 0 0;
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  width: 1200px;
}

.header {
  margin-bottom: 20px;
}
</style>
