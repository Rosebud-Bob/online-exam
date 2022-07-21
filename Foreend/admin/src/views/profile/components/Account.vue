<template>
  <el-form>
    <el-form-item label="真实姓名">
      <el-input v-model.trim="userInfo.name" />
    </el-form-item>
    <el-form-item label="手机号">
      <el-input v-model.trim="userInfo.mobile_number" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">更新</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import userApi from '@/api/user'
export default {
  props: {
    userInfo: {
      type: Object,
      default: () => {
        return {
          name: '',
          mobile_number: ''
        }
      }
    }
  },
  methods: {
    submit () {
      let _this = this
      userApi.updateUser(this.userInfo).then(re => {
        if (re.code === 200) {
          _this.$message.success(re.data)
        } else {
          _this.$message.error(re.data)
        }
      })
    }
  }
}
</script>
