/**
 * axios封装
 * 请求拦截、响应拦截、错误统一处理
 */
import axios from 'axios'
import router from './router'
const tokenKey = 'JEECMS-Auth-Token'
const timeout = 10000
const localStorge = require('store');
if (process.env.NODE_ENV === 'development') {
    axios.defaults.baseURL = 'http://localhost:8080'
} else if (process.env.NODE_ENV === 'debug') {
    axios.defaults.baseURL = 'https://www.ceshi.com'
} else if (process.env.NODE_ENV === 'production') {
    axios.defaults.baseURL = 'https://www.production.com'
}
axios.defaults.withCredentials = true
const request = axios.create({
    timeout: timeout,
    headers: {
        'Content-Type': 'application/json',
        'Redirect-Header': false
    }
})

const baseHeader = () => {
    const token = localStorge.get('token');
    return token || 'wwwww';

}
/**
 * 请求拦截器
 * 每次请求前，如果存在token则在请求头中携带token
 */
request.interceptors.request.use(config => {
    config.headers['token'] = baseHeader();
    console.log(config)
    console.log('00000')
    const url = config.url;
    const method = config.method;
    // 判断请求方式为get 并且地址为/house/list 去缓存里拿，拿到后判断是否过期，如果过期就请求接口，没有过期就不请求接口
    if(method=='get' && url =='/house/list') {

    }
    if (!config.url) {
        console.warn(' request url is null')
    } else if (config.url.endsWith('/admin/login')) {
        config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
        // config.data = qs.stringify(config.data)
    }
    return config
}, error => {
    console.log(error)
    return Promise.reject(error)
})
/**
 * 跳转登录页
 * 携带当前页面路由，以期在登录页面完成登录后返回当前页面
 */
const toLogin = () => {
    router.replace({
        path: '/login',
        query: {
            redirect: router.currentRoute.fullPath
        }
    })
}

/**
 * 请求失败后的错误统一处理
 * @param {Number} status 请求失败的状态码
 */
const errorHandle = (status, other) => {
    // 状态码判断
    switch (status) {
        // 401: 未登录状态，跳转登录页
        case 401:
            toLogin()
            break
        // 403 token过期
        // 清除token并跳转登录页
        case 403:
            localStorage.removeItem('token')
            // store.commit('loginSuccess', null)
            setTimeout(() => {
                toLogin()
            }, 1000)
            break
        // 404请求不存在
        case 404:
            // tip('请求的资源不存在')
            console.log('no such resource')
            break
        default:
            console.log(other)
    }
}

// 响应拦截器
request.interceptors.response.use(
    // 请求成功
    res => {
        if( res.status === 200 ) {
            console.log(res)
            console.log('1111')
            return Promise.resolve(res);
        } else {
            Promise.reject(res)
        }
        // res.status === 200 ? Promise.resolve(res) : Promise.reject(res)
    },
    // 请求失败
    error => {
        const { response } = error
        if (response) {
            // 请求已发出，但是不在2xx的范围
            errorHandle(response.status, response.data.message)
            return Promise.reject(response)
        } else {
            // 处理网络没响应的情况
            console.log('no net work ....')
            return Promise.reject(new Error(' No network '))
        }
    })
const syncall = {
    async get(url, data) {
        let res = await request.get(url, { params: data },)
        return res
    },
    async post(url, data) {
        let res = await request.post(url, data)
        return res
    }
}
export { request as axios, syncall as axioscall }
