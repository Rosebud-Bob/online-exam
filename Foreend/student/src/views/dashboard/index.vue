<template>
  <div style="margin-top: 10px">
    <el-row>
    </el-row>

    <el-row class="app-item-contain">
      <h3 class="index-title-h3" style="border-left: solid 10px cornflowerblue;">试卷列表</h3>
<!--      xp:最开始的版本-->
<!--      <div style="padding-left: 15px">-->
<!--        <el-col :span="4" v-for="(item, index) in fixedPaper" :key="index" :offset="index > 0 ? 1 : 0">-->
<!--          <el-card :body-style="{ padding: '0px' }" v-loading="loading">-->
<!--            <img src="@/assets/exam-paper/show1.png" class="image">-->
<!--            <div style="padding: 14px;">-->
<!--              <span>{{item.name}}</span>-->
<!--              <div class="bottom clearfix">-->
<!--                <router-link target="_blank" :to="{path:'/do',query:{id:item.id}}">-->
<!--                  <el-button type="text" class="button">开始做题</el-button>-->
<!--                </router-link>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </div>-->
    </el-row>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import indexApi from '@/api/dashboard'
export default {
  data () {
    return {
      fixedPaper: [],
      timeLimitPaper: [],
      pushPaper: [],
      loading: false,
      taskLoading: false,
      taskList: []
    }
  },
  created () {
    this.loading = true
    this.taskLoading = true
  },
  methods: {
    statusTagFormatter (status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusTextFormatter (status) {
      return this.enumFormat(this.statusEnum, status)
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      statusEnum: state => state.exam.examPaperAnswer.statusEnum,
      statusTag: state => state.exam.examPaperAnswer.statusTag
    })
  }
}
</script>

<style lang="scss" scoped>
  .index-title-h3 {
    font-size: 22px;
    font-weight: 400;
    color: #1f2f3d;
    border-left: solid 10px #2ce8b4;
    padding-left: 10px;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 50%;
    height: 80%;
    display: block;
    margin: 20px auto 10px auto;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>
