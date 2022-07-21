import { post } from '@/utils/request'

export default {
  selectQuestionByCondition: query => post('/api/admin/question/page', query),
  editQuestion: query => post('/api/admin/question/edit', query),
  select: id => post('/api/admin/question/select/' + id),
  deleteQuestion: id => post('/api/admin/question/delete/' + id),
  selectAll: query => post('/api/admin/question/page/selectAll' ),
  addOne: query => post('/api/admin/question/add',query),
  // 根据方向名称得到方向ID
  getDirectionIDByNAME: query =>post(
    '/api/admin/question/searchID',query)
}
