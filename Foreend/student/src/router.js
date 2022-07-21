import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)
const router = new Router({
  routes: [
    { path: '/login', name: 'Login', component: () => import('@/views/login/index'),
      meta: { title: '登录', bodyBackground: '#fbfbfb', requireAuth:true} },
    { path: '/register', name: 'Register', component: () => import('@/views/register/index'),
      meta: { title: '注册', bodyBackground: '#fbfbfb',requireAuth:true} },
    {
      path: '/',
      component: Layout,
      redirect: '/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/dashboard/index'),
          name: 'Dashboard',
          meta: { title: '首页',requireAuth:true }
        }
      ]
    },
    {
      path: '/user',
      component: Layout,
      children: [
        {
          path: 'index',
          component: () => import('@/views/user-info/index'),
          name: 'UserInfo',
          meta: { title: '个人中心',requireAuth:true }
        }
      ]
    },

    { path: '/do', name: 'ExamPaperDo', component: () => import('@/views/exam/paper/do'),
      meta: { title: '试卷答题',requireAuth:true } },
    { path: '*', component: () => import('@/views/error-page/404'), meta: { title: '404' }
    }
  ]
})


// token:设置路由拦截
// 运行next(false)就阻止跳转
router.beforeEach((to, from, next) => {
  const token=localStorage.getItem('token')
  if (to.name!=='Login' && !token) {
    next({
      path: '/login', // 返回登陆页面
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})
// end

export { router }
