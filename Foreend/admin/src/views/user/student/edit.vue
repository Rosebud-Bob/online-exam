<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名："  prop="username" required>
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码："  required>
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="name" required>
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="性别：">
        <el-select v-model="form.sex" placeholder="性别" clearable>
          <el-option v-for="item in sexEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出生日期：">
        <el-date-picker v-model="form.birthDay" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
      </el-form-item>
      <el-form-item label="手机：">
        <el-input v-model="form.mobile_number"></el-input>
      </el-form-item>
      <el-form-item label="年级：" prop="grade" required>
        <el-select v-model="form.grade" placeholder="年级">
          <el-option v-for="item in gradeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import userApi from '@/api/user'

export default {
  data () {
    return {
      form: {
        id: null,
        username: '',
        password: '',
        name: '',
        user_type: 'U',
        sex: '',
        birthDay: null,
        mobile_number: null,
        grade: null
      },
      formLoading: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        grade: [
          { required: true, message: '请选择年级', trigger: 'change' }
        ]
      }
    }
  },
  // 初始化，显示原来的信息
  created () {
    let username = this.$route.query.username
    let _this = this
    if (username) {
      _this.formLoading = true
      // 更新表单
      userApi.selectUser(username).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.createUser(this.form).then(data => {
            if (data.code === 200) {
              _this.$message.success(data.data)
              _this.delCurrentView(_this).then(() => {
                _this.$router.push('/user/student/list')
              })
            } else {
              _this.$message.error(data.data)
              _this.formLoading = false
            }
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,  // id
        username: '', // 用户名
        password: '', // 密码
        name: '', // 真实姓名
        user_type: 'U', // 角色类型
        sex: '', // 性别
        birthDay: null, // 出生日期
        mobile_number: null, // 手机号码
        grade: null // 年级
      }
      this.form.id = lastId
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      sexEnum: state => state.user.sexEnum,
      roleEnum: state => state.user.roleEnum,
      gradeEnum: state => state.user.gradeEnum
    })
  }
}
</script>
