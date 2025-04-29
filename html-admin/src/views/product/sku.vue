<template>
  <div class="container">
    <div>
      <!--第一行-->
      <div>
        <a-button type="primary" @click="openAddAttrModal(null)">
          <template #icon>
            <icon-plus/>
          </template>
          添加SKU属性
        </a-button>
      </div>
      <!--第二行 sku 列表-->
      <div style="display: flex; justify-content: center; margin: 20px 0 0 0 ;min-height: 400px  ">
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
      <div style="display: flex; justify-content: center; margin: 20px">
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

export default {
  name: 'sku',
  data () {
    return {
      skuId: this.$route.query.skuId,
      skuAttrList: [
        // {
        //   skuAttrName: '颜色',
        //   skuAttrValueList: ['白', '黑', '棕'],
        // }, {
        //   skuAttrName: '尺码',
        //   skuAttrValueList: ['40', '41', '42', '43'],
        // },
      ],
      skuList: [],
      showAddSkuAttrModal: false,
      formAddSkuAttr: {},
      tableColumns: [
        // {
        //   title: '',
        //   dataIndex: '颜色',
        // },
      ],
      tableData: [
        // {
        //   颜色: '白'
        // }
      ],
    }
  },
  created () {
    this.resetFormAddAttrValue()
  },
  methods: {
    // 提交
    submitSku () {

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
        this.$message.warning('请填写属性名和至少一个属性值')
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
      // 增加销售价格和库存数量列（带输入框）
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
          },
      )

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
        row.priceYuan = 0 // 展示元
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
  },
}
</script>


<style scoped>

</style>

