<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form"
             auto-complete="on" label-position="left"
             @keyup.enter.native="handleLogin">
      <div class="title-container">
        <h3 class="title">管理系统</h3>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
<!--        Form-Item 的 ref 属性设置为需校验的字段名-->
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
        <el-form-item prop="passwordRaw">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :key="passwordType"
            ref="passwordRaw"
            v-model="passwordRaw"
            :type="passwordType"
            placeholder="密码"
            name="passwordRaw"
            tabindex="2"
            auto-complete="on"
            @keyup.native="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
      </el-tooltip>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">登录</el-button>

    </el-form>
  </div>
</template>

<script>
/* eslint-disable */
import { mapMutations } from 'vuex'
import loginApi from '@/api/login'
import qs from 'qs'

let md5 = require("md5")

export default {
  name: 'Login',
  data () {
    // 表单验证中的rule
    const validateusername = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('用户名不能少于5个字符'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码不能少于5个字符'))
      } else {
        callback()
      }
    }

    return {
      // 登陆表单
      loginForm: {
        username: '',
        password: '', // 加密后的password
      },
      // 表单验证
      // elements 的Form组件提供的表单验证的功能，通过 rules 属性传入约定的验证规则
      // blur:失去焦点时进行验证
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateusername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      passwordRaw:'', //最原始的password
      capsTooltip: false,
      loading: false,
      showDialog: false
    }
  },
  created () {
    // window.addEventListener('storage', this.afterQRScan)
  },
  // 类似提前声明变量 进入页面内容全部渲染完成后自动引函数
  mounted () {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()  // 获取对应的username的dom,自动获得焦点
    } else if (this.passwordRaw === '') {
      this.$refs.passwordRaw.focus() // 获取对应的password的dom，自动获得焦点
    }
  },
  destroyed () {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    // 检查大小锁定
    checkCapslock ({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        // eslint-disable-next-line no-mixed-operators(不考虑加shift下的非字母)
        if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
          this.capsTooltip = true
        } else {
          this.capsTooltip = false
        }
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        // 开了capslock且输入的是字母的时候，提示在输入大写
        this.capsTooltip = false
      }
    },
    // 显示密码
    showPwd () {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      // this.$nextTick()将回调延迟到下次 DOM 更新循环之后执行,在自动获得焦点
      this.$nextTick(() => {
        this.$refs.passwordRaw.focus()
      })
    },
    // 处理登陆
    handleLogin () {
      let _this = this
      this.loginForm.password=md5(this.passwordRaw) //md5加密
      this.$refs.loginForm.validate(valid => {
        if (valid) { // 如果是有效的
          this.loading = true
          // loginApi是从@/api/login所import的
          // 其中的login是调用后端/api/user/login的API，传入的是loginForm表单
          // then() 方法返回一个 Promise。参数：Promise 成功的回调函数
          // console.log(JSON.stringify(this.loginForm))
          loginApi.login(this.loginForm).then(function (result) {
            if (result && result.code === 200) {
              //处理token
              localStorage.setItem('token',result.data)
              //结束token
              _this.setusername(_this.loginForm.username)
              _this.$router.push({ path: '/' }) // 使用路由跳转到下一个页面
            } else { // 如果是无效的
              _this.loading = false
              _this.$message({
                message: result.data, // result的消息
                type: 'error' // 表示错误
              })
            }
          }).catch(function (reason) {
            _this.loading = false
          })
        } else {
          return false
        }
      })
    },
    // 以便在组件中直接使用方法
    ...mapMutations('user', ['setusername'])
  }
}
</script>

<style lang="scss">

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: cornflowerblue !important;
      height: 47px;
      caret-color: cornflowerblue;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px white inset !important;
        -webkit-text-fill-color: cornflowerblue !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: white;
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:lightblue;
$dark_gray:#44a0b3;
$light_gray:#44a0b3;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: white;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding:30px 50px 10px 50px;
    margin:120px auto auto auto;
    overflow: hidden;
    background-color: aliceblue;
    border-width: 3px;
    -webkit-box-shadow: 0 7px 25px rgb(0 0 0 / 8%);
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: cornflowerblue;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: cornflowerblue;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: cornflowerblue;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
