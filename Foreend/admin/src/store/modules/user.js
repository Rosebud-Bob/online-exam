import Cookies from 'js-cookie'
import userApi from '@/api/user'
// initial state
const state = {
  username: Cookies.get('adminusername'),
  userInfo: Cookies.get('adminUserInfo')
}

// actions
const actions = {
  initUserInfo ({ commit }) {
    userApi.getCurrentUser().then(re => {
      commit('setUserInfo', re.response)
    })
  }
}

// mutations
const mutations = {
  setusername (state, username) {
    state.username = username
    Cookies.set('adminusername', username, { expires: 30 })
  },
  setUserInfo: (state, userInfo) => {
    state.userInfo = userInfo
    Cookies.set('adminUserInfo', userInfo, { expires: 30 })
  },
  clearLogin (state) {
    Cookies.remove('adminusername')
    Cookies.remove('adminUserInfo')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
