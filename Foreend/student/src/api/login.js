import { post, postWithLoadTip } from '@/utils/request'
// 注册部分api
export default {
  login: query => postWithLoadTip(`/api/login`, query),
  //logout: query => post(`/api/user/logout`, query)
}
