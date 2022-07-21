import Cookies from 'js-cookie'
import userApi from '@/api/user'
// initial state
const state = {
  username: Cookies.get('studentUserName'),
  userInfo: Cookies.get('studentUserInfo'),
  imagePath: Cookies.get('studentImagePath'),
  messageCount: 0
}

// actions
const actions = {
  initUserInfo ({ commit }) {
    userApi.getCurrentUser().then(re => {
      commit('setUserInfo', re.response)
    })
  },
  getUserMessageInfo ({ commit }) {
  }
}

// mutations
const mutations = {
  setUserName (state, username) {
    state.username = username
    Cookies.set('studentUserName', username, { expires: 30 })
  },
  setUserInfo: (state, userInfo) => {
    state.userInfo = userInfo
    Cookies.set('studentUserInfo', userInfo, { expires: 30 })
  },
  setImagePath: (state, imagePath) => {
    state.imagePath = imagePath
    Cookies.set('studentImagePath', imagePath, { expires: 30 })
  },
  setMessageCount: (state, messageCount) => {
    state.messageCount = messageCount
  },
  messageCountSubtract: (state, num) => {
    state.messageCount = state.messageCount - num
  },
  clearLogin (state) {
    Cookies.remove('studentUserName')
    Cookies.remove('studentUserInfo')
    Cookies.remove('studentImagePath')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
