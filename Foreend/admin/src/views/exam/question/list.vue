<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="题目ID：">
        <el-input v-model="queryParam.id" clearable  placeholder="请输入数字"></el-input>
      </el-form-item>
      <el-form-item label="专业分类：">
        <el-select v-model="queryParam.profession" placeholder="专业分类"
                   @change="levelChange" clearable >
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业方向：">
        <el-input v-model="queryParam.direction" clearable placeholder="请输入专业分类"></el-input>
      </el-form-item>
      <el-form-item label="题型：">
        <el-select v-model="queryParam.type" clearable>
          <el-option v-for="item in type" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
          <router-link :to="{path:'/exam/question/edit/singleChoice'}" class="link-left">
            <el-button type="primary" plain>添加单选</el-button>
          </router-link>
          <router-link :to="{path:'/exam/question/edit/multipleChoice'}" class="link-left">
            <el-button type="primary" plain>添加多选</el-button>
          </router-link>
         <router-link :to="{path:'/exam/question/edit/trueFalse'}" class="link-left">
            <el-button type="primary" plain>添加判断</el-button>
          </router-link>
          <router-link :to="{path:'/exam/question/edit/shortAnswer'}" class="link-left">
            <el-button type="primary" plain>添加简答</el-button>
          </router-link>
      </el-form-item>
    </el-form>
    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="questionID" label="Id" width="90px"/>
      <el-table-column prop="direction.profession.professionName" label="专业分类"  width="120px"/>
      <el-table-column prop="direction.directionName" label="专业方向"  width="120px"/>
      <el-table-column prop="type" label="题型"  width="70px"/>
      <el-table-column prop="body" label="题干" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" width="220px">
        <template slot-scope="{row}">
          <el-button size="mini"  @click="editQuestion(row)">编辑</el-button>
          <el-button size="mini"  type="danger" @click="deleteQuestion(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import QuestionShow from './components/Show'
import questionApi from '@/api/question'

export default {
  components: { Pagination, QuestionShow },
  data () {
    return {
      queryParam: {
        id: null,
        type: null,
        profession: null,
        direction: null,
        //分页
        pageIndex: 1,
        pageSize: 10,
      },
      subjectFilter: null,
      listLoading: true,
      tableData: [],
      total: 0,
      questionShow: {
        qType: 0,
        dialog: false,
        question: null,
        loading: false
      }
    }
  },
  created () {
    this.initSubject()
    this.search()
    this.queryParam.pageIndex = 1
  },
  methods: {
    submitForm () {
      this.queryParam.id=parseInt(this.queryParam.id)
      // this.queryParam.pageIndex = 1
      this.search()
    },
    search () {
      this.listLoading = true
      this.initSubject()
      questionApi.selectQuestionByCondition(this.queryParam).then(data => {
        if(data.data &&　data.data!==[]){
          this.tableData = data.data
          this.total = data.data.length
          // this.queryParam.pageIndex = 1
          this.listLoading = false
        }
        else {
          alert('题目不存在！')
          location.reload();
        }
      })
    },
    levelChange () { // 专业分类改变
      this.queryParam.direction = null
      this.subjectFilter = this.subjects.filter(data => data.profession === this.queryParam.profession)
    },
    editQuestion (row) {
      let url = this.enumFormat(this.editUrlEnum, parseInt(row.type))
      this.$router.push({ path: url, query: { id: row.questionID } })
    },
    deleteQuestion (row) {
      let _this = this
      questionApi.deleteQuestion(row.questionID).then(re => {
        if (re.code === 200) {
          _this.search()
          _this.$message.success(re.data)
        } else {
          _this.$message.error(re.data)
        }
      })
    },
    typeFormatter (row, column, cellValue, index) {
      return this.enumFormat(this.type, cellValue)
    },
    subjectFormatter (row, column, cellValue, index) {
      return this.subjectEnumFormat(cellValue)
    },
    ...mapActions('exam', { initSubject: 'initSubject' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      type: state => state.exam.question.typeEnum,
      editUrlEnum: state => state.exam.question.editUrlEnum,
      levelEnum: state => state.exam.question.levelEnum
    }),
    ...mapGetters('exam', ['subjectEnumFormat']),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
