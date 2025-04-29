<template>
  <div class="container">
    <div>
      <!--第一行-->
      <div>
        <a-button  type="outline" @click="openAddAttrModal(null)">
          <template #icon>
            <icon-plus/>
          </template>
          添加SKU属性
        </a-button>
        <a-button type="outline" status="danger"  style="margin-left: 12px;" @click="clearAllSku">
          清除所有SKU
        </a-button>
      </div>
      <!--第二行 sku 列表-->
      <div v-if="tableData.length > 0" style="display: flex; justify-content: center; margin: 20px 0 0 0 ;min-height: 400px  ">
        <a-table class="my-mall-table-width" :columns="tableColumns" :data="tableData" :pagination="false">
          <template #priceInput="{ record, rowIndex }">
            <a-input-number
                v-model="tableData[rowIndex].priceYuan"
                :min="0"
                :step="0.01"
                style="width: 120px"
                :precision="2"
                @change="val => handlePriceYuanChange(val, rowIndex)"
            />
          </template>
          <template #stockInput="{ record, rowIndex }">
            <a-input-number v-model="tableData[rowIndex].stockQuantity" :min="0" :step="1" style="width: 120px"/>
          </template>
        </a-table>
      </div>
      <!--第三行提交 sku-->
      <div v-if="tableData.length > 0" style="display: flex; justify-content: center; margin: 20px">
        <a-button type="primary" @click="submitSku()">
          提交SKU
        </a-button>
      </div>
    </div>


    <!--添加SKU属性-->
    <a-modal v-model:visible="showAddSkuAttrModal" title="添加SKU属性" @cancel="closeAttrModal"
             :width="600"
             @ok="submitAddAttrModal">
      <a-form :model="formAddSkuAttr">
        <a-form-item field="name" label="属性名">
          <a-input v-model="formAddSkuAttr.skuAttrName" allow-clear/>
        </a-form-item>
        <a-form-item field="name" label="属性值">
          <a-input v-model="formAddSkuAttr.skuAttrValueInput" show-word-limit :max-length="30" allow-clear/>
          <a-button type="primary" @click="addAttrValue()">
            <template #icon>
              <icon-plus/>
            </template>
            添加属性值
          </a-button>
        </a-form-item>
        <a-form-item>
          <div>
            <div style="margin: 0 0 6px 14px" v-show="formAddSkuAttr.skuAttrValueList.length > 0">
              已添加{{ formAddSkuAttr.skuAttrValueList.length }}个：
            </div>
            <div>
              <a-tag :key="idx" v-for="(item, idx) in formAddSkuAttr.skuAttrValueList"
                     style="margin: 6px 4px;display: inline-block">{{ item }}
              </a-tag>
            </div>
          </div>
        </a-form-item>
      </a-form>
    </a-modal>

  </div>
</template>
<script>

import { productSkuCreateApi, productSkuQueryApi } from '@/api/product.js'
import { hasValue } from '@/utils/check.js'
import router from '@/router/index.js'
import { Message } from '@arco-design/web-vue'

export default {
  name: 'sku',
  data () {
    return {
      // 当前商品ID，从路由参数获取
      productId: this.$route.query.productId,
      // SKU属性列表，每个对象包含属性名和属性值列表
      // 例如：[{ skuAttrName: '颜色', skuAttrValueList: ['白', '黑', '棕'] }, ...]
      skuAttrList: [
        // {
        //   skuAttrName: '颜色',
        //   skuAttrValueList: ['白', '黑', '棕'],
        // }, {
        //   skuAttrName: '尺码',
        //   skuAttrValueList: ['40', '41', '42', '43'],
        // },
      ],
      // SKU列表，存储所有SKU组合及其详细信息（如价格、库存等）
      skuList: [],

      // 控制添加SKU属性弹窗的显示与隐藏
      showAddSkuAttrModal: false,

      // 添加SKU属性表单的数据对象
      // 包含属性名、属性值列表、属性值输入框内容等
      formAddSkuAttr: {},

      // 表格列配置（用于UI展示SKU属性、价格、库存等）
      // 例如：[{ title: '颜色', dataIndex: '颜色' }, ...]
      tableColumns: [
        // {
        //   title: '颜色',
        //   dataIndex: '颜色',
        // },
      ],

      // 表格数据（用于UI展示每一行SKU组合及其价格、库存等）
      // 例如：[{ 颜色: '白', 尺码: '40', priceYuan: 12.34, stockQuantity: 100 }, ...]
      tableData: [
        // {
        //   颜色: '白'
        // }
      ],
    }
  },
  created () {
    if (!hasValue(this.productId)) {
      router.back()
    }
    this.resetFormAddAttrValue()
    this.fetchData()
  },
  methods: {
    addSkuTableColumns() {
      this.tableColumns.push(
        {
          title: '销售价格/元',
          dataIndex: 'priceYuan',
          slotName: 'priceInput',
        },
        {
          title: '库存数量',
          dataIndex: 'stockQuantity',
          slotName: 'stockInput',
        }
      )
    },
    async fetchData () {
      const resp = await productSkuQueryApi(this.productId)
      const result = resp.data.result
      // 如果有sku数据，填充编辑表格
      if (result && result.skuList && result.skuList.length > 0) {
        // 设置表头
        this.skuAttrList = result.skuAttrNameList.map(name => ({
          skuAttrName: name,
          skuAttrValueList: [] // 这里只做表头用
        }))
        this.tableColumns = result.skuAttrNameList.map(name => ({
          title: name,
          dataIndex: name
        }))
        this.addSkuTableColumns()
        // 设置表格数据
        this.tableData = result.skuList.map(item => {
          const row = {}
          result.skuAttrNameList.forEach((name, idx) => {
            row[name] = item.skuAttrValueList[idx]
          })
          row.price = item.price
          row.priceYuan = item.price ? Number((item.price / 100).toFixed(2)) : null
          row.stockQuantity = item.stockQuantity
          row.id = item.id
          return row
        })
      } else {
        // 没有sku数据则不展示
        this.tableColumns = []
        this.tableData = []
      }
    },
    // 提交
    submitSku () {
      // console.log('tableColumns', this.tableColumns)
      // console.log('tableData', this.tableData)
      const col = [...this.tableColumns]
      const dt = [...this.tableData]
      // console.log('col', col)
      // console.log('dt', dt)
      // 欲提交的数据
      const skuAttrNameList = []
      const skuList = []

      // skuAttrNameList
      for (let i = 0; i < col.length - 2; i++) {
        skuAttrNameList.push(col[i].dataIndex)
      }

      // skuList
      for (let i = 0; i < dt.length; i++) {
        const singleSku = {
          ...dt[i],
          skuAttrValueList: [],  // sku 属性列表
        }
        skuList.push(singleSku)
        for (let j = 0; j < skuAttrNameList.length; j++) {
          const singleValue = dt[i][skuAttrNameList[j]]  // 单个属性值
          // console.log(singleValue)
          singleSku.skuAttrValueList.push(singleValue)
        }
      }
      // console.log(skuList)
      productSkuCreateApi({
        productId: this.productId,
        skuAttrNameList: skuAttrNameList,
        skuList: skuList,
      }).then(() => {
        this.fetchData()
        Message.success('SKU更新成功')
      })
    },
    openAddAttrModal () {
      this.showAddSkuAttrModal = true
      this.closeAttrModal()
    },
    closeAttrModal () {
      this.resetFormAddAttrValue()
    },
    submitAddAttrModal () {
      // 获得attr名称以及attr值
      const skuAttrName = this.formAddSkuAttr.skuAttrName
      const skuAttrValueList = this.formAddSkuAttr.skuAttrValueList

      if (!skuAttrName || skuAttrValueList.length === 0) {
        Message.warning('请填写属性名和至少一个属性值')
        return
      }

      // 添加到skuAttrList
      this.skuAttrList.push({
        skuAttrName,
        skuAttrValueList: [...skuAttrValueList],
      })

      // 更新表格列
      this.tableColumns = this.skuAttrList.map(attr => ({
        title: attr.skuAttrName,
        dataIndex: attr.skuAttrName,
      }))
      this.addSkuTableColumns()
      // 生成所有组合（笛卡尔积）
      const cartesian = (arr) => {
        return arr.reduce((a, b) => {
          const ret = []
          a.forEach(aItem => {
            b.forEach(bItem => {
              ret.push(aItem.concat([bItem]))
            })
          })
          return ret
        }, [[]])
      }
      const valueArr = this.skuAttrList.map(attr => attr.skuAttrValueList)
      let combinations = []
      if (valueArr.length > 0) {
        combinations = cartesian(valueArr)
      }

      // 生成表格数据
      this.tableData = combinations.map(comb => {
        const row = {}
        this.skuAttrList.forEach((attr, idx) => {
          row[attr.skuAttrName] = comb[idx]
        })
        row.price = 0 // 实际存储分
        row.priceYuan = null // 展示元
        row.stockQuantity = 0
        return row
      })

      // 表单组件
      this.resetFormAddAttrValue()
      this.showAddSkuAttrModal = false
    },
    addAttrValue () {
      const input = this.formAddSkuAttr.skuAttrValueInput
      this.formAddSkuAttr.skuAttrValueInput = ''
      if (input.trim().length === 0) {
        return
      }
      this.formAddSkuAttr.skuAttrValueList.push(input)
    },
    resetFormAddAttrValue () {
      this.formAddSkuAttr = {
        skuAttrName: '',
        skuAttrValueList: [],
        // 属性值输入框
        skuAttrValueInput: '',
      }
    },
    handlePriceYuanChange (val, rowIndex) {
      // 元转分，四舍五入
      this.tableData[rowIndex].price = Math.round(Number(val) * 100)
    },
    clearAllSku() {
      // 重置所有SKU相关数据
      this.skuAttrList = []
      this.tableColumns = []
      this.tableData = []
      this.skuList = []
      Message.success('已清除所有SKU')
    },
  },
}
</script>


<style scoped>

</style>

