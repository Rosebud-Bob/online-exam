<template>
  <div style="margin-top: 10px" class="app-contain">
    <el-row :gutter="50">
      <el-col :span="7">
        <el-card>
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-row style="text-align: center">
              <el-avatar class="el-dropdown-avatar" :size="100" :src="require('@/assets/avatar.png') "></el-avatar>
          </el-row>
          <el-row class="user-info-username">
            <label>{{form.username}}</label>
          </el-row>
          <el-divider/>
          <el-row class="user-info-fullInfo">
            <label>姓名：{{form.name}}</label><br/>
            <label>年级：{{form.grade}}级</label><br/>
          </el-row>
        </el-card>
      </el-col>

      <el-col :span="17">
        <el-card shadow="hover">
          <el-tabs active-name="update" type="card">
            <el-tab-pane label="个人资料修改" name="update">
              <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
                <el-form-item label="姓名：" prop="name" required>
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="性别：">
                  <el-select v-model="form.sex" placeholder="性别" clearable>
                    <el-option v-for="item in sexEnum" :key="item.value" :value="item.value"
                               :label="item.value"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="出生日期：">
                  <el-date-picker v-model="form.birthday" value-format="yyyy-MM-dd" type="date" placeholder="选择日期"/>
                </el-form-item>
                <el-form-item label="手机：">
                  <el-input v-model="form.mobileNumber"></el-input>
                </el-form-item>
                <el-form-item label="年级：" >
                    <el-input v-model="form.grade"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm">更新</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>

            <el-tab-pane label="密码修改" name="password" >
              <el-form :model="passwordEdit" ref="passwordEdit" label-width="100px" v-loading="formLoading" :rules="rules">
                <el-form-item label="旧密码：" required>
                  <el-input v-model="oldPasswordRaw"></el-input>
                </el-form-item>
                <el-form-item label="新密码："required >
                  <el-input v-model="newPasswordRaw"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitPassword">更新</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>


          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userApi from '@/api/user'
import { mapGetters, mapState } from 'vuex'
import md5 from "md5";

export default {
  data () {
    return {
      event: [],
      passwordEdit:{
        oldPassword: "",
        newPassword: "",
      },
      form: {
        username: '',
        name: '',
        sex: '',
        birthday: null,
        mobileNumber: null,
        grade: null,
        userType: 'U'
      },
      newPasswordRaw:'',
      oldPasswordRaw:'',
      formLoading: false,
      rules: {
        name: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        grade: [
          { required: true, message: '请选择年级', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    let _this = this
    userApi.getCurrentUser().then(re => {
      _this.form = re.data
    })
  },
  methods: {
    uploadSuccess (re, file) {
      if (re.code === 200) {
        window.location.reload()
      } else {
        this.$message.error(re.message)
      }
    },
    submitPassword(){
      let _this = this
      this.passwordEdit.newPassword=md5(this.newPasswordRaw)
      this.passwordEdit.oldPassword=md5(this.oldPasswordRaw)
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.changePassword(this.passwordEdit).then(data => {
            if (data.code === 200) {
              _this.$message.success(data.message)
            } else {
              _this.$message.error(data.message)
            }
            _this.formLoading = false
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.update(this.form).then(data => {
            if (data.code === 200) {
              _this.$message.success(data.message)
            } else {
              _this.$message.error(data.message)
            }
            _this.formLoading = false
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    levelFormatter (level) {
      return this.enumFormat(this.levelEnum, level)
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      sexEnum: state => state.user.sexEnum,
      levelEnum: state => state.user.levelEnum
    })
  }
}
</script>

<style lang="scss" scoped>
</style>
