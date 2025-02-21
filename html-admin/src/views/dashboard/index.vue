<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>访问量</span>
            </div>
          </template>
          <div class="card-body">
            <el-statistic :value="1234">
              <template #title>
                <div style="display: inline-flex; align-items: center">
                  今日访问
                  <el-icon style="margin-left: 4px">
                    <TrendCharts />
                  </el-icon>
                </div>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>用户数</span>
            </div>
          </template>
          <div class="card-body">
            <el-statistic :value="789">
              <template #title>
                <div style="display: inline-flex; align-items: center">
                  总用户数
                  <el-icon style="margin-left: 4px">
                    <User />
                  </el-icon>
                </div>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>订单数</span>
            </div>
          </template>
          <div class="card-body">
            <el-statistic :value="432">
              <template #title>
                <div style="display: inline-flex; align-items: center">
                  今日订单
                  <el-icon style="margin-left: 4px">
                    <ShoppingCart />
                  </el-icon>
                </div>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>收入</span>
            </div>
          </template>
          <div class="card-body">
            <el-statistic :value="9876" prefix="¥">
              <template #title>
                <div style="display: inline-flex; align-items: center">
                  今日收入
                  <el-icon style="margin-left: 4px">
                    <Money />
                  </el-icon>
                </div>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>欢迎使用</span>
        </div>
      </template>
      <div class="welcome-content">
        <h2>后台管理系统</h2>
        <p>这是一个基于 Vue 3 + Element Plus 的后台管理系统</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { TrendCharts, User, ShoppingCart, Money } from '@element-plus/icons-vue'
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-body {
  text-align: center;
  padding: 20px 0;
}

.welcome-content {
  text-align: center;
  padding: 40px;
}

.welcome-content h2 {
  margin-bottom: 20px;
  color: #409EFF;
}
</style>
