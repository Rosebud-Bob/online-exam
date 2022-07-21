<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading"  :rules="rules">
      <el-form-item label="专业分类："  required>
        <el-select v-model="form.direction.profession.professionID" placeholder="专业分类">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业方向：" prop="direction" required>
        <el-input v-model="form.direction.directionName"  class="question-item-content-input"
                  placeholder="请输入专业方向"/>
      </el-form-item>
      <el-form-item label="题干：" prop="body" required>
<!--        当元素获得焦点时，发生 focus 事件-->
        <el-input v-model="form.body"    class="question-item-content-input"
                  placeholder="请输入题干"/>
      </el-form-item>
      <el-form-item label="正确答案：" prop="answer" required>
        <el-input v-model="form.answer" class="question-item-content-input"
                  placeholder="请输入正确答案"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="firstSubmit">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="提交" :visible.sync="dialogVisible" width="30%">
      <span>确定提交吗？</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable*/
import QuestionShow from '../components/Show'
import Ueditor from '@/components/Ueditor'
import { mapGetters, mapState, mapActions } from 'vuex'
import questionApi from '@/api/question'

const form =
  {
    direction:{
      directionID: null,
      directionName:'',
      profession:{
        professionID: '',
        professionName:'',
      }
    },
    questionID: 1,
    type: 4,
    body: '',
    selection: [
      { prefix: 'T', content: '正确' },
      { prefix: 'F', content: '错误' }
    ],
    answer: '',
  }

export default {
  components: {
    Ueditor, QuestionShow
  },
  data () {
    return {
      form: {
        direction:{
          directionID: null,
          directionName:'',
          profession:{
            professionID: null,
            professionName:'',
          }
        },
        questionID: null,
        type: 3,
        body: '',
        selection: [
          { prefix: 'T', content: '正确' },
          { prefix: 'F', content: '错误' }
        ],
        answer: ''
      },
      returnSearch:{
        professionID:'',
        directionName:''
      },
      subjectFilter: null,
      formLoading: false,
      rules: {
        profession: [
          { required: true, message: '请选择专业分类', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请输入题干', trigger: 'blur' }
        ],
        answer: [
          { required: true, message: '请选择正确答案', trigger: 'change' }
        ]
      },
      dialogVisible:false
    }
  },
  created () {
    let _this = this
    this.formLoading = false
    if(this.$route.query.id){
      let id = this.$route.query.id
      _this.formLoading = true
      questionApi.select(id).then(re => {
        _this.form = re.data
        _this.formLoading = false
      })
    }
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    _this.formLoading = false
  },
  methods: {
    firstSubmit(){
      this.dialogVisible=true
      let _this = this
      // 根据方向名称查找方向id
      this.returnSearch.professionID=this.form.direction.profession.professionID.toString()
      this.returnSearch.directionName=this.form.direction.directionName
      questionApi.getDirectionIDByNAME(this.returnSearch).then(re => {
        if (re.data) {
          this.form.direction.directionID = re.data
        }
      }).catch(e => {
        if(e=='directionID not found'){
          alert('专业方向不存在，请先添加！')
          _this.$router.push('/education/subject/list')
        }
        this.formLoading = false
      })
      // 根据方向名称查找方向id end
    },
    submitForm () {
      this.dialogVisible = false
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.direction.directionID){
            this.formLoading = true
            // 根据专业名称查找专业id
            let levelEnum = [{key: 1, value: '毛概'}, {key: 2, value: '马原'}, {key: 3, value: '军理'}, {key: 4, value: '思修'}]
            for (let i in levelEnum) {
              if (levelEnum[i].key === parseInt(this.form.direction.profession.professionID)) {
                this.form.direction.profession.professionName = levelEnum[i].value
              }
            }
            // 如果没有id，则选择添加题目
            if (!this.form.questionID || this.form.questionID === null) {
              questionApi.addOne(this.form).then(re => {
                if (re.code === 200) {
                  this.$message.success(re.data)
                  this.delCurrentView(this).then(() => {
                    this.$router.push('/exam/question/list')
                  })
                } else {
                  this.$message.error(re.data)
                  this.formLoading = false
                }
              }).catch(e => {
                this.formLoading = false
              })
            }
            // 如果有id，则编辑题目
            else {
              questionApi.editQuestion(this.form).then(re => {
                if (re.code === 200) {
                  this.$message.success(re.data)
                  this.delCurrentView(this).then(() => {
                    this.$router.push('/exam/question/list')
                  })
                } else {
                  this.formLoading = false
                }
              }).catch(e => {
                if(e=='directionID not found'){
                  alert('专业方向不存在，请先添加！')
                  this.$router.push('/education/subject/list')
                }
                this.formLoading = false
              })
            }
          }
          else{
            alert('请先检查专业方向！')
            return false
          }
        }
        else {
          return false
        }
      })
    },
    resetForm () {
      let lastId = this.form.questionID
      this.$refs['form'].resetFields()
      this.form = {
        direction:{
          directionID: null,
          directionName:'',
          profession:{
            professionID: '',
            professionName:'',
          }
        },
        questionID: null,
        type: 3,
        body: '',
        selection: [
          { prefix: 'T', content: '正确' },
          { prefix: 'F', content: '错误' }
        ],
        answer: ''
      }
      this.form.questionID = lastId
      this.dialogVisible=false
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      typeEnum: state => state.exam.question.typeEnum,
      levelEnum: state => state.exam.question.levelEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
