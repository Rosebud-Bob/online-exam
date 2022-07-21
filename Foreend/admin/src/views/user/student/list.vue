<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="用户名：">
        <el-input v-model="username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchByName">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="name" label="真实姓名" />
      <el-table-column prop="grade" label="年级"/>
      <el-table-column prop="sex" label="性别" width="60px;"/>
      <el-table-column prop="mobileNumber" label="手机号"/>
      <el-table-column prop="birthday" label="出生日期" />
      <el-table-column width="270px" label="操作" align="center">
<!--        table绑定了:data,作用域插槽中定义一个对象 (这里对象被定义为 row)-->
<!--        query传参-->
        <template slot-scope="{row}">
          <el-button  size="mini" type="danger" @click="deleteUser(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.page" :limit.sync="queryParam.size"
                @pagination="search"/>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import Pagination from '@/components/Pagination'
import userApi from '@/api/user'
import Qs from "qs";

export default {
  components: { Pagination },
  data () {
    return {
      queryParam: {
        page: 1,
        size: 10
      },
      username: '',
      user_type: 'A',
      listLoading: true,
      tableData: [],
      total: 0
    }
  },
  created () {
    this.search()
  },
  methods: {
    searchByName(){
      this.listLoading = true
      userApi.getUserByName(this.username).then(data => {
        if(data.data){
          let temp=[]
          temp.push(data.data)
          this.tableData=temp
          this.total = 1
          this.queryParam.page = 1
          this.listLoading = false
        }
        else{
          alert('用户不存在！')
          this.search()
        }
      })
    },
    search () {
      this.listLoading = true
      userApi.getUserPageList(Qs.stringify(this.queryParam)).then(data => {
        this.tableData = data.data
        this.total = data.data.length
        this.queryParam.page = 1
        this.listLoading = false
      })
    },
    changeStatus (row) {
      let _this = this
      userApi.changeStatus(row.id).then(re => {
        if (re.code === 200) {
          row.status = re.response
          _this.$message.success(re.data)
        } else {
          _this.$message.error(re.data)
        }
      })
    },
    // 删除用户
    deleteUser (row) {
      let _this = this
      userApi.deleteUser(row.username).then(re => {
        if (re.code === 200) {
          _this.search()
          _this.$message.success(re.data)
        } else {
          _this.$message.error(re.data)
        }
      })
    },
    submitForm () {
      this.queryParam.page = 1
      this.search()
    },
    gradeFormatter(row, column, cellValue, index) {
      return this.enumFormat(this.gradeEnum, cellValue)
    },
    sexFormatter  (row, column, cellValue, index) {
      return this.enumFormat(this.sexEnum, cellValue)
    },
    statusFormatter (status) {
      return this.enumFormat(this.statusEnum, status)
    },
    statusTagFormatter (status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusBtnFormatter (status) {
      return this.enumFormat(this.statusBtn, status)
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      gradeEnum:state => state.user.gradeEnum,
      sexEnum: state => state.user.sexEnum,
      statusEnum: state => state.user.statusEnum,
      statusTag: state => state.user.statusTag,
      statusBtn: state => state.user.statusBtn,
    })
  }
}
</script>
