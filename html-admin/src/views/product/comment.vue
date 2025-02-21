<template>
  <div class="app-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>评论管理</span>
          <el-button type="primary" @click="handleAdd">新增评论</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
        <el-table-column prop="rating" label="评分" width="100">
          <template #default="{ row }">
            <el-rate v-model="row.rating" disabled />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="评论时间" width="180" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已显示' : '已隐藏' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
          class="pagination"
          :total="total"
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.pageSize"
          @current-change="getList"
      />
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
        :title="dialog.title"
        v-model="dialog.visible"
        width="500px"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="商品" prop="productId">
          <el-select v-model="form.productId" placeholder="请选择商品">
            <el-option
                v-for="item in productOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择用户">
            <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.username"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input
              v-model="form.content"
              type="textarea"
              :rows="3"
              placeholder="请输入评论内容"
          />
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-rate v-model="form.rating" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">显示</el-radio>
            <el-radio :value="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { comments, getPageData } from '@/mock/data'

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const productOptions = ref([]) // 商品选项
const userOptions = ref([]) // 用户选项

const queryParams = reactive({
  page: 1,
  pageSize: 10
})

const dialog = reactive({
  visible: false,
  title: '',
  type: 'add'
})

const form = reactive({
  id: undefined,
  productId: '',
  userId: '',
  content: '',
  rating: 5,
  status: 1
})

const rules = {
  productId: [
    { required: true, message: '请选择商品', trigger: 'change' }
  ],
  userId: [
    { required: true, message: '请选择用户', trigger: 'change' }
  ],
  content: [
    { required: true, message: '请输入评论内容', trigger: 'blur' },
    { min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: 'blur' }
  ],
  rating: [
    { required: true, message: '请选择评分', trigger: 'change' }
  ]
}

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    // 模拟API调用延迟
    await new Promise(resolve => setTimeout(resolve, 300))
    const res = getPageData(comments, queryParams.page, queryParams.pageSize)
    tableData.value = res.list
    total.value = res.total
  } finally {
    loading.value = false
  }
}

// 获取商品和用户选项
const getOptions = async () => {
  try {
    // 获取商品列表
    // const productRes = await getProductOptions()
    // productOptions.value = productRes.data

    // 获取用户列表
    // const userRes = await getUserOptions()
    // userOptions.value = userRes.data
  } catch (error) {
    console.error('获取选项失败:', error)
  }
}

// 新增
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增评论'
  dialog.visible = true
  form.id = undefined
  form.productId = ''
  form.userId = ''
  form.content = ''
  form.rating = 5
  form.status = 1
}

// 编辑
const handleEdit = (row) => {
  dialog.type = 'edit'
  dialog.title = '编辑评论'
  dialog.visible = true
  Object.assign(form, row)
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除��评论吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    // 调用删除API
    // await deleteComment(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

// 提交表单
const handleSubmit = async () => {
  // 调用新增或编辑API
  // const api = dialog.type === 'add' ? addComment : updateComment
  // await api(form)
  ElMessage.success(dialog.type === 'add' ? '新增成功' : '更新成功')
  dialog.visible = false
  getList()
}

// 初始化
getList()
getOptions()
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
