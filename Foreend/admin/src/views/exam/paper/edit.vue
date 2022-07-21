<!--<template>-->
<!--  <div class="app-container">-->
<!--    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">-->
<!--      <el-form-item label="专业分类：" prop="profession" required>-->
<!--        <el-select v-model="form.profession" placeholder="专业分类"  @change="levelChange">-->
<!--          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="专业方向：" prop="direction" required>-->
<!--        <el-input v-model="form.direction" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="试卷类型：" prop="paperType" required>-->
<!--        <el-select v-model="form.paperType" placeholder="试卷类型">-->
<!--          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="时间限制：" required v-show="form.paperType===4">-->
<!--        <el-date-picker v-model="form.limitDateTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"-->
<!--                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="试卷名称："  prop="name" required>-->
<!--        <el-input v-model="form.name"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item :key="index" :label="'标题'+(index+1)+'：'" required v-for="(titleItem,index) in form.titleItems">-->
<!--        <el-input v-model="titleItem.name" style="width: 80%"/>-->
<!--        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="addQuestion(titleItem)">-->
<!--          添加题目-->
<!--        </el-button>-->
<!--        <el-button type="text" class="link-left" size="mini" @click="form.titleItems.splice(index,1)">删除</el-button>-->
<!--        <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length!==0">-->
<!--          <el-form-item :key="questionIndex" :label="'题目'+(questionIndex+1)+'：'"-->
<!--                        v-for="(questionItem,questionIndex) in titleItem.questionItems" style="margin-bottom: 15px">-->
<!--            <el-row>-->
<!--              <el-col :span="23">-->
<!--                <QuestionShow :qType="questionItem.type" :question="questionItem"/>-->
<!--              </el-col>-->
<!--              <el-col :span="1">-->
<!--                <el-button type="text" size="mini" @click="titleItem.questionItems.splice(questionIndex,1)">删除-->
<!--                </el-button>-->
<!--              </el-col>-->
<!--            </el-row>-->
<!--          </el-form-item>-->
<!--        </el-card>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="建议时长：" prop="suggestTime" required>-->
<!--        <el-input v-model="form.suggestTime" placeholder="分钟"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="submitForm">提交</el-button>-->
<!--        <el-button @click="resetForm">重置</el-button>-->
<!--        <el-button type="success" @click="addTitle">添加标题</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
<!--    <el-dialog :visible.sync="questionPage.showDialog"  width="70%">-->
<!--      <el-form :model="questionPage.queryParam" ref="queryForm" :inline="true">-->
<!--        <el-form-item label="ID：">-->
<!--          <el-input v-model="questionPage.queryParam.id"  clearable></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="题型：">-->
<!--          <el-select v-model="questionPage.queryParam.type" clearable>-->
<!--            <el-option v-for="item in typeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <el-button type="primary" @click="queryForm">查询</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <el-table v-loading="questionPage.listLoading" :data="questionPage.tableData"-->
<!--                @selection-change="handleSelectionChange" border fit highlight-current-row style="width: 100%">-->
<!--        <el-table-column type="selection" width="35"></el-table-column>-->
<!--        <el-table-column prop="id" label="Id" width="60px"/>-->
<!--        <el-table-column prop="type" label="题型" :formatter="typeFormatter" width="70px"/>-->
<!--        <el-table-column prop="shortTitle" label="题干" show-overflow-tooltip/>-->
<!--      </el-table>-->
<!--      <pagination v-show="questionPage.total>0" :total="questionPage.total"-->
<!--                  :page.sync="questionPage.queryParam.pageIndex" :limit.sync="questionPage.queryParam.pageSize"-->
<!--                  @pagination="search"/>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--          <el-button @click="questionPage.showDialog = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="confirmQuestionSelect">确定</el-button>-->
<!--     </span>-->
<!--    </el-dialog>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->

<!--import { mapGetters, mapState, mapActions } from 'vuex'-->
<!--import Pagination from '@/components/Pagination'-->
<!--import QuestionShow from '../question/components/Show'-->
<!--import examPaperApi from '@/api/examPaper'-->
<!--import questionApi from '@/api/question'-->

<!--export default {-->
<!--  components: { Pagination, QuestionShow },-->
<!--  data () {-->
<!--    return {-->
<!--      form: {-->
<!--        id: null,-->
<!--        profession: null,-->
<!--        direction: null,-->
<!--        paperType: 1,-->
<!--        limitDateTime: [],-->
<!--        name: '',-->
<!--        suggestTime: null,-->
<!--        titleItems: []-->
<!--      },-->
<!--      subjectFilter: null,-->
<!--      formLoading: false,-->
<!--      rules: {-->
<!--        profession: [-->
<!--          { required: true, message: '请选择专业分类', trigger: 'change' }-->
<!--        ],-->
<!--        direction: [-->
<!--          { required: true, message: '请选择专业方向', trigger: 'change' }-->
<!--        ],-->
<!--        paperType: [-->
<!--          { required: true, message: '请选择试卷类型', trigger: 'change' }-->
<!--        ],-->
<!--        name: [-->
<!--          { required: true, message: '请输入试卷名称', trigger: 'blur' }-->
<!--        ],-->
<!--        suggestTime: [-->
<!--          { required: true, message: '请输入建议时长', trigger: 'blur' }-->
<!--        ]-->
<!--      },-->
<!--      questionPage: {-->
<!--        multipleSelection: [],-->
<!--        showDialog: false,-->
<!--        queryParam: {-->
<!--          id: null,-->
<!--          type: null,-->
<!--          direction: 1,-->
<!--          pageIndex: 1,-->
<!--          pageSize: 10-->
<!--        },-->
<!--        listLoading: true,-->
<!--        tableData: [],-->
<!--        total: 0-->
<!--      },-->
<!--      currentTitleItem: null-->
<!--    }-->
<!--  },-->
<!--  created () {-->
<!--    let id = this.$route.query.id-->
<!--    let _this = this-->
<!--    this.initSubject(function () {-->
<!--      _this.subjectFilter = _this.subjects-->
<!--    })-->
<!--    if (id && parseInt(id) !== 0) {-->
<!--      _this.formLoading = true-->
<!--      examPaperApi.select(id).then(re => {-->
<!--        _this.form = re.response-->
<!--        _this.formLoading = false-->
<!--      })-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    submitForm () {-->
<!--      let _this = this-->
<!--      this.$refs.form.validate((valid) => {-->
<!--        if (valid) {-->
<!--          this.formLoading = true-->
<!--          examPaperApi.edit(this.form).then(re => {-->
<!--            if (re.code === 200) {-->
<!--              _this.$message.success(re.data)-->
<!--              _this.delCurrentView(_this).then(() => {-->
<!--                _this.$router.push('/exam/paper/list')-->
<!--              })-->
<!--            } else {-->
<!--              _this.$message.error(re.data)-->
<!--              this.formLoading = false-->
<!--            }-->
<!--          }).catch(e => {-->
<!--            this.formLoading = false-->
<!--          })-->
<!--        } else {-->
<!--          return false-->
<!--        }-->
<!--      })-->
<!--    },-->
<!--    addTitle () {-->
<!--      this.form.titleItems.push({-->
<!--        name: '',-->
<!--        questionItems: []-->
<!--      })-->
<!--    },-->
<!--    addQuestion (titleItem) {-->
<!--      this.currentTitleItem = titleItem-->
<!--      this.questionPage.showDialog = true-->
<!--      this.search()-->
<!--    },-->
<!--    removeTitleItem (titleItem) {-->
<!--      this.form.titleItems.remove(titleItem)-->
<!--    },-->
<!--    removeQuestion (titleItem, questionItem) {-->
<!--      titleItem.questionItems.remove(questionItem)-->
<!--    },-->
<!--    queryForm () {-->
<!--      this.questionPage.queryParam.pageIndex = 1-->
<!--      this.search()-->
<!--    },-->
<!--    confirmQuestionSelect () {-->
<!--      let _this = this-->
<!--      this.questionPage.multipleSelection.forEach(q => {-->
<!--        questionApi.select(q.id).then(re => {-->
<!--          _this.currentTitleItem.questionItems.push(re.response)-->
<!--        })-->
<!--      })-->
<!--      this.questionPage.showDialog = false-->
<!--    },-->
<!--    levelChange () {-->
<!--      this.form.direction = null-->
<!--      this.subjectFilter = this.subjects.filter(data => data.profession === this.form.profession)-->
<!--    },-->
<!--    search () {-->
<!--      this.questionPage.queryParam.direction = this.form.direction-->
<!--      this.questionPage.listLoading = true-->
<!--      questionApi.selectDirectionByCondition(this.questionPage.queryParam.id).then(data => {-->
<!--        const re = data.response-->
<!--        this.questionPage.tableData = re.list-->
<!--        this.questionPage.total = 10-->
<!--        this.questionPage.queryParam.pageIndex = 1-->
<!--        this.questionPage.listLoading = false-->
<!--      })-->
<!--    },-->
<!--    handleSelectionChange (val) {-->
<!--      this.questionPage.multipleSelection = val-->
<!--    },-->
<!--    typeFormatter (row, column, cellValue, index) {-->
<!--      return this.enumFormat(this.typeEnum, cellValue)-->
<!--    },-->
<!--    subjectFormatter (row, column, cellValue, index) {-->
<!--      return this.subjectEnumFormat(cellValue)-->
<!--    },-->
<!--    resetForm () {-->
<!--      let lastId = this.form.id-->
<!--      this.$refs['form'].resetFields()-->
<!--      this.form = {-->
<!--        id: null,-->
<!--        profession: null,-->
<!--        direction: null,-->
<!--        paperType: 1,-->
<!--        limitDateTime: [],-->
<!--        name: '',-->
<!--        suggestTime: null,-->
<!--        titleItems: []-->
<!--      }-->
<!--      this.form.id = lastId-->
<!--    },-->
<!--    ...mapActions('exam', { initSubject: 'initSubject' })-->
<!--  },-->
<!--  computed: {-->
<!--    ...mapGetters('enumItem', ['enumFormat']),-->
<!--    ...mapState('enumItem', {-->
<!--      typeEnum: state => state.exam.question.typeEnum,-->
<!--      paperTypeEnum: state => state.exam.examPaper.paperTypeEnum,-->
<!--      levelEnum: state => state.exam.question.levelEnum-->
<!--    }),-->
<!--    ...mapState('exam', { subjects: state => state.subjects })-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="scss">-->
<!--  .exampaper-item-box {-->
<!--    .q-title {-->
<!--      margin: 0px 5px 0px 5px;-->
<!--    }-->
<!--    .q-item-content {-->
<!--    }-->
<!--  }-->
<!--</style>-->
