import {post, postWithLoadTip, postFormat, form} from '@/utils/request'
// utils文件夹放工具类函数，代码块
// 指定模块的默认输出，login和logout是向外暴露的成员，原来的函数在request.js里面
// 传入的参数是query是请求的data

export default {
   login: query => post(`/api/login`, query)
}
