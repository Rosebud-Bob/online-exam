import { post } from '@/utils/request'
// 专业方向部分的api
export default {
  list: query => post('/api/student/education/subject/list'),
  select: id => post('/api/student/education/subject/select/' + id)
}
