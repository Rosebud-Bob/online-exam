import { post } from '@/utils/request'

export default {
  list: query => post('/api/admin/education/subject/list'),
  // page改成用id传
  selectDirectionByCondition: id => post('/api/admin/education/subject/page/' + id),
  edit: query => post('/api/admin/education/subject/edit', query),
  select: id => post('/api/admin/education/subject/select/' + id),
}
