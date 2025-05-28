<template>
  <div style="padding: 20px">
    <div style="margin-bottom: 16px">
      <a-row>
        <a-col :span="24">
          <!--请输入用户昵称/手机号搜索-->
          <a-space>
            <a-input-search
                v-model="fetchDataParams.code"
                placeholder="请输入用户编号搜索"
                style="width: 300px"
                @search="handleSearch"
            />
          </a-space>
        </a-col>
      </a-row>
    </div>

    <a-table
        :data="tableData"
        :loading="loading"
        :pagination="{
        ...pagination,
        showTotal: true
      }"
        @page-change="onPageChange"
    >
      <template #columns>
        <a-table-column title="用户信息">
          <template #cell="{ record }">
            <div style="display: flex; align-items: center; gap: 12px">
              <a-avatar :size="40" :image-url="record.user.avatar"/>
              <div style="display: flex; flex-direction: column">
                <div>{{ record.user.nickName }}</div>
                <div style="font-size: 12px; color: #666">{{ record.user.phone }}</div>
              </div>
            </div>
          </template>
        </a-table-column>
        <a-table-column title="用户编号" data-index="user.code"/>
        <a-table-column title="积分余额">
          <template #cell="{ record }">
            {{ record.balance || 0 }}
          </template>
        </a-table-column>
        <a-table-column title="注册时间" data-index="user.createTime"/>
        <a-table-column title="操作" :width="200">
          <template #cell="{ record }">
            <a-space>
              <!--<a-button type="text" size="small" @click="handlePointsDetail(record)">-->
              <!--  <icon-history/>-->
              <!--  积分明细-->
              <!--</a-button>-->
              <a-button type="text" size="small" @click="handlePointsAdjust(record)">
                <icon-edit/>
                调整积分
              </a-button>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>

    <!-- 积分调整弹窗 -->
    <a-modal v-model:visible="showAdjustModal" title="调整积分" @cancel="closeAdjustModal" @ok="submitAdjust">
      <a-form :model="adjustForm">
        <a-form-item field="points" label="增加积分">
          <a-input-number
              v-model="adjustForm.points"
              placeholder="请输入调整积分数"
              :min="0"
              :max="9999999"
          />
          <!--<div style="font-size: 12px; color: #666; margin-top: 4px">正数为增加积分，负数为扣减积分</div>-->
        </a-form-item>
        <!--<a-form-item field="reason" label="调整原因">-->
        <!--  <a-textarea-->
        <!--    v-model="adjustForm.reason"-->
        <!--    placeholder="请输入调整原因"-->
        <!--    :auto-size="{ minRows: 2, maxRows: 5 }"-->
        <!--  />-->
        <!--</a-form-item>-->
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { addPointsApi, payPointsApi } from '@/api/pay.js'
import { IconHistory, IconEdit } from '@arco-design/web-vue/es/icon'
import { Message } from '@arco-design/web-vue'

export default {
  name: 'points',
  components: {
    IconHistory,
    IconEdit,
  },
  data () {
    return {
      fetchDataParams: {
        pageNum: 1,
        pageSize: 10,
        code: '',
      },
      loading: false,
      tableData: [],
      pagination: {
        total: 0,
        current: 1,
        pageSize: 10,
      },
      showAdjustModal: false,
      adjustForm: {
        userId: '',
        points: 0,
        reason: '',
      },
    }
  },
  async mounted () {
    await this.fetchData()
  },
  methods: {
    async fetchData () {
      this.loading = true
      try {
        const resp = await payPointsApi(this.fetchDataParams)
        const pointPage = resp.data.result.pointPage
        this.tableData = pointPage.records
        this.pagination.total = pointPage.total
        // 确保pagination和fetchDataParams同步
        this.pagination.current = this.fetchDataParams.pageNum
        this.pagination.pageSize = this.fetchDataParams.pageSize
      } catch (error) {
        // console.error('获取积分数据失败:', error)
        Message.info('暂无信息')
      } finally {
        this.loading = false
      }
    },
    handleSearch () {
      this.fetchDataParams.pageNum = 1
      this.pagination.current = 1 // 同步更新pagination
      this.fetchData()
    },
    onPageChange (current) {
      this.fetchDataParams.pageNum = current
      this.pagination.current = current // 更新pagination的current值
      this.fetchData()
    },
    handlePointsDetail (record) {
      // TODO: 查看积分明细
      console.log('查看积分明细', record)
    },
    handlePointsAdjust (record) {
      this.adjustForm.userId = record.user.id
      this.showAdjustModal = true
    },
    closeAdjustModal () {
      this.adjustForm = {
        userId: '',
        points: 0,
        reason: '',
      }
      this.showAdjustModal = false
    },
    async submitAdjust () {
      // console.log('提交积分调整', this.adjustForm)
      const resp = await addPointsApi(this.adjustForm)
      await this.fetchData()
      this.closeAdjustModal()
      Message.success('积分调整成功')
    },
  },
}
</script>
