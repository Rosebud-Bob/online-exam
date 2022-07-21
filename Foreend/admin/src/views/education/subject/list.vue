<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="专业分类：">
        <el-select v-model="queryParam.professionID" placeholder="专业分类" clearable="">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/education/subject/edit'}" class="link-left">
          <el-button type="primary">添加</el-button>
        </router-link>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="directionID" label="Id" />
      <el-table-column prop="directionName" label="专业方向"/>
      <el-table-column prop="profession.professionName" label="专业分类" />
      <el-table-column width="220px" label="操作" align="center">
        <template slot-scope="{row}">
          <router-link :to="{path:'/education/subject/edit', query:{id:row.directionID}}" class="link-left">
            <el-button size="mini">编辑</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import Pagination from '@/components/Pagination'
import subjectApi from '@/api/subject'

export default {
  components: { Pagination },
  // 分页
  data () {
    return {
      queryParam: {
        professionID: null,
        // pageIndex: 1,
        // pageSize: 10
      },
      listLoading: true,
      tableData: [],
      total: 0
    }
  },
  created () {
    this.searchAll()
  },
  methods: {
    searchAll(){
      subjectApi.list().then(data => {
        this.tableData = data.data
        this.total = 10
        this.queryParam.pageIndex =1
        //this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    search () {
      this.listLoading = true
      subjectApi.selectDirectionByCondition(this.queryParam.professionID).then(data => {
        this.tableData = data.data
        this.total = 10
        this.queryParam.pageIndex =1
        //this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    submitForm () {
      this.queryParam.pageIndex = 1
      this.search()
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      levelEnum: state => state.exam.question.levelEnum
    })
  }
}
</script>
