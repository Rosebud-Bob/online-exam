import axios from 'axios'
import vue from 'vue'

const request = function (loadtip, query) {
  let loading
  if (loadtip) { // 如果是加载提示的请求
    // 添加一个变量到 Vue.prototype
    loading = vue.prototype.$loading({
      lock: false,
      text: '正在加载中…',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.5)'
    })
  }

  // 返回axios请求
  return axios.request(query)
    .then(res => {
      if (loadtip) {
        loading.close() // 关闭加载的动画
      }
      if (res.data.code === 401) { // 用户需要身份验证
        vue.prototype.$$router.push({ path: '/login' })
        return Promise.reject(res.data)
      } else if (res.data.code === 500) { // 服务器内部错误
        return Promise.reject(res.data)
      } else if (res.data.code === 501) { // 服务器不支持请求的功能
        return Promise.reject(res.data)
      } else if (res.data.code === 502) { // 服务器尝试代理执行请求时，接收到无效的响应
        vue.prototype.$$router.push({ path: '/login' })
        return Promise.reject(res.data)
      } else { // 通过
        return Promise.resolve(res.data)
      }
    })
    .catch(e => {
      if (loadtip) {
        loading.close()
      }
      // $ 是在 Vue 所有实例中都可用的 property 的一个简单约定
      vue.prototype.$message.error(e.data)
      return Promise.reject(e.data)
    })
}

// POST方法发送数据给服务器
// post传递的参数实际上是放到了网络请求的data段传递的，不显式暴露在链接上
// data是传入的参数query
// request函数在最前面
const post = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  const token = localStorage.getItem('token')
  if (token) {
    query.headers.token = token
  }
  return request(false, query)
}

// 带负载提示的post
// request函数在最前面
const postWithLoadTip = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  const token = localStorage.getItem('token')
  if (token) {
    query.headers.token = token
  }
  return request(true, query)
}

const postWithOutLoadTip = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/json', 'request-ajax': true }
  }
  const token = localStorage.getItem('token')
  if (token) {
    query.headers.token = token
  }
  return request(false, query)
}

// get方法是获取服务器的数据
const get = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'get',
    withCredentials: true,
    timeout: 30000,
    params: params,
    headers: { 'request-ajax': true }
  }
  const token = localStorage.getItem('token')
  if (token) {
    query.headers.token = token
  }
  return request(false, query)
}


const form = function (url, params) {
  const query = {
    baseURL: process.env.VUE_APP_URL,
    url: url,
    method: 'post',
    withCredentials: true,
    timeout: 30000,
    data: params,
    headers: { 'Content-Type': 'application/form-data', 'request-ajax': true }
  }
  const token = localStorage.getItem('token')
  if (token) {
    query.headers.token = token
  }
  return request(false, query)
}

export {
  post,
  postWithLoadTip,
  postWithOutLoadTip,
  get,
  form
}
