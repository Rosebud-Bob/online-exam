import { post } from '@/utils/request'

export default {
  // 创建用户
  // createUser: query => post('/api/student/user/edit', query),
  // 获得当前用户
  getCurrentUser: () => post('/api/student/user/current'),
  // 更新用户信息
  update: query => post('/api/student/user/update', query),
  // 更改密码
  changePassword: query => post('/api/student/user/update', query)

}
