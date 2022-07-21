<template>
  <div>

<!--    试卷名字-->
    <el-container  class="app-item-contain">
      <el-header class="align-center">
        <h1>{{form.name}}</h1>
      </el-header>

<!--      考试部分-->
      <el-main>
        <el-form :model="form" ref="form" v-loading="formLoading" label-width="200px" >
          <el-row :key="index"  v-for="(titleItem,index) in form.titleItems">
            <h3>{{titleItem.name}}</h3>
            <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length!==0">
<!--                题目-->
              <el-form-item :key="questionItem.itemOrder" :label="questionItem.itemOrder+'.'+questionItem.title"
                            v-for="questionItem in titleItem.questionItems"
                            class="exam-question-item" label-width="auto" label-position="left"  :id="'question-'+ questionItem.itemOrder">
                <el-form-item :key="questionItem.itemOrder"
                              class="exam-question-item" :id="'question-'+ questionItem.itemOrder">
                </el-form-item>
<!--                选项-->
                <el-form-item :key="Item.itemOrder" :label="Item.prefix+'、'+Item.content"
                              v-for="Item in questionItem.items"
                              class="exam-question-item" label-width="auto" label-position="left" :id="'question-'+ questionItem.itemOrder">
                </el-form-item>
                <!--                回答框-->
                <el-form-item label="答案：" label-position="left" label-width="auto" >
                  <el-input class="question-item-content-input"/>
                </el-form-item>
              </el-form-item>
            </el-card>
          </el-row>
<!--          提交部分-->
           <el-row class="do-align-center">
             <el-button type="primary" @click="submitForm">提交</el-button>
             <el-button>取消</el-button>
           </el-row>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import { formatSeconds } from '@/utils'
import QuestionEdit from '../components/QuestionEdit'
import examPaperApi from '@/api/examPaper'
import examPaperAnswerApi from '@/api/examPaperAnswer'
/* eslint-disable */


export default {
  components: { QuestionEdit },
  data () {
    return {
      form: {},
      formLoading: false,
      answer: {
        questionId: null,
        answerItems: []
      }
    }
  },

  created () {
    // 不知道后端的接口于是写了组本地数据，到时根据后端的响应来改
    const response={"id":126,"level":1,"subjectId":4,"paperType":1,"name":"随机生成试卷", "titleItems":[{"name":"很随便的卷子",
        "questionItems":[{"id":86,"questionType":1,"subjectId":4,"title":"我是第一题，我的问题是blablabla", "gradeLevel":1,
          "items":[{"prefix":"A","content":"这是A","score":null},
            {"prefix":"B","content":"这是B","score":null},{"prefix":"C","content":"这是C","score":null},
            {"prefix":"D","content":"这是D","score":null}],"analyze":null,"correctArray":null,"correct":null,
          "score":"2","difficult":null,"itemOrder":1,"knowledgeIdList":[]}]}],"classes":null}
    // let id = this.$route.query.id
    let _this = this
    _this.form = response

    // 接受后端的数据，并把内容存入form中，API根据后端写的改
    // if (id && parseInt(id) !== 0) {
    //   _this.formLoading = true
    //   examPaperApi.select(id).then(re => {
    //     _this.form=re
    //     _this.formLoading = false
    //   })
    // }
  },
  mounted () {
  },
  beforeDestroy () {
  },
  methods: {
    // 一个假的submit，不过它确实可以跳转页面
    submitForm () {
      let _this = this
      _this.formLoading = true
      _this.$alert('成功提交！', {
        confirmButtonText: '返回首页',
        callback: action => {
          _this.$router.push('/user/index')
        }
      })
      _this.formLoading = false
    }
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      doCompletedTag: state => state.exam.question.answer.doCompletedTag
    })
  }
}
</script>

<style lang="scss" scoped>
  .align-center {
    text-align: center
  }

  .exam-question-item {
    padding: 10px;

    .el-form-item__label {
      font-size: 15px !important;
    }
  }

  .question-title-padding {
    padding-left: 25px;
    padding-right: 25px;
  }
</style>
