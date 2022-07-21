<template>
  <el-container>
    <el-header height="61" class="student-header">
      <div class="head-user">
        <el-dropdown trigger="click" placement="bottom">
          <el-avatar  class="el-dropdown-avatar" size="medium">U</el-avatar>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push({path:'/user/index'})">修改资料</el-dropdown-item>
            <el-dropdown-item @click.native="logout" divided>退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-menu class="el-menu-title" mode="horizontal" :default-active="defaultUrl" :router="true">
        <el-menu-item index="/index">首页</el-menu-item>
        <el-menu-item index="/user/index">个人中心</el-menu-item>
      </el-menu>
      <div>
        <a href="/"><img src="@/assets/logo.png" height="56"/></a>
      </div>
    </el-header>
    <el-main class="student-main">
      <router-view/>
    </el-main>
    <el-footer height="340" class="student-footer">
      <div class="container">
        <div class="footer-main">
          <img src="@/assets/code/1.jpg" style="width: 150px;height: 150px;"/>
        </div>
        <div class="footer-main">
          <img src="@/assets/code/3.jpg" style="width: 150px;height: 150px;margin-left: 10px"/>
        </div>
        <div class="footer-main">
          <img src="@/assets/code/7.jpg" style="width: 150px;height: 150px;margin-left: 10px"/>
        </div>
        <div class="footer-main">
          <img src="@/assets/4.jpg" style="width: 150px;height: 150px;"/>
        </div>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import loginApi from '@/api/login'
import userApi from '@/api/user'
export default {
  name: 'Layout',
  data () {
    return {
      defaultUrl: '/index',
      userInfo: {
        imagePath: null
      }
    }
  },
  created () {
    let _this = this
    this.defaultUrl = this.routeSelect(this.$route.path)
    this.getUserMessageInfo()
    userApi.getCurrentUser().then(re => {
      _this.userInfo = re.response
    })
  },
  watch: {
    $route (to, from) {
      this.defaultUrl = this.routeSelect(to.path)
    }
  },
  methods: {
    routeSelect (path) {
      let topPath = ['/', '/index', '/paper/index', '/record/index', '/question/index']
      if (topPath.indexOf(path)) {
        return path
      }
      return null
    },
    logout () {
      let _this = this
      _this.clearLogin()
      _this.$router.push({ path: '/login' })
    },
    ...mapActions('user', ['getUserMessageInfo']),
    ...mapMutations('user', ['clearLogin'])
  },
  computed: {
    ...mapState('user', {
      messageCount: state => state.messageCount
    })
  }
}
</script>

<style lang="scss" scoped>

</style>
