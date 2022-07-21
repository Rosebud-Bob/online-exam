import { post,get } from '@/utils/request'

export default {
  getUserPageList: query => get('/api/admin/user/page/list/?'+query), // 获得用户list
  createUser: query => post('/api/admin/user/edit', query), // 创建用户
  getCurrentUser: () => post('/api/admin/user/current'), // 获得现有用户
  updateUser: query => post('/api/admin/user/update', query), // 更新用户
  selectUser: query => post('/api/admin/user/select/' ,query), // 选择用户
  deleteUser: query => get('/api/admin/user/delete/?username='+ query), // 删除用户
  getUserByName: query => get('/api/getUserInfoAdmin/?username='+query), // 获得用户list
}
