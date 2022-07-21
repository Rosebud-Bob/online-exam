// initial state
const state = {
  user: {
    sexEnum: [{ key: 1, value: '男' }, { key: 2, value: '女' }],
    gradeEnum: [{ key: 1, value: '一年级' }, { key: 2, value: '二年级' }, { key: 3, value: '三年级' }, { key: 4, value: '四年级' }],
    roleEnum: [{ key: 1, value: '学生' },  { key: 2, value: '管理员' }],
    statusTag: [{ key: 1, value: 'success' }, { key: 2, value: 'danger' }]
  },
  exam: {
    examPaper: {
      paperTypeEnum: [{ key: 1, value: '固定试卷' }]
    },
    question: {
      // 学科
      levelEnum: [{ key: 1, value: '毛概' }, { key: 2, value: '马原' }, { key: 3, value: '军理' }, { key: 4, value: '思修' }],
      typeEnum: [{ key: 1, value: '单选题' }, { key: 2, value: '多选题' }, { key: 3, value: '判断题' },  { key: 4, value: '简答题' }],
      editUrlEnum: [{ key: 1, value: '/exam/question/edit/singleChoice', name: '单选题' },
        { key: 2, value: '/exam/question/edit/multipleChoice', name: '多选题' },
        { key: 3, value: '/exam/question/edit/trueFalse', name: '判断题' },
        { key: 4, value: '/exam/question/edit/shortAnswer', name: '简答题' }]
    }
  }
}

// getters
const getters = {
  enumFormat: (state) => (arrary, key) => {
    return format(arrary, key)
  }
}

// actions
const actions = {}

// mutations
const mutations = {}

const format = function (array, key) {
  for (let item of array) {
    if (item.key === key) {
      return item.value
    }
  }
  return null
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
