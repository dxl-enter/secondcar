import axios from "axios"
import Element from "element-ui"
import store from "./store"
import router from "./router"

/*
* 全局配置异常页面，提示信息
*/
// export const baseUrl = "http://47.98.130.198:8090/api";
export const baseUrl = "http://localhost:8090/api";
// export const baseUrl = "http://yuechi.com/:8090/api";

// 前缀
// axios.defaults.baseURL = "http://localhost:8090/";
axios.defaults.baseURL = baseUrl;
// axios.defaults.baseURL = "http://yuechi.com/:8090/";

// 解决session中验证码为空的问题
axios.defaults.withCredentials = true;

// 前置拦截
// 后置拦截
axios.interceptors.response.use(
  // 成功时
  success => {
        // let res = response.data;
        console.log("后置拦截==========");
        // this.$message({
        //     showClose: true,
        //     type: 'info',
        //     message: '文件上传失败'
        // });
        console.log(success);
        console.log("后置拦截==========");
        if (success.data.status === 200) {
            // this.$message({
            //     showClose: true,
            //     type: 'success',
            //     message: success.data.msg,
            //     duration: 1.5 * 1000
            // });
            Element.Message.error({message: success.data.msg, duration: 1.5 * 1000, showClose: true});
            return success;
        } else if (success.data.code === 3002) {
            Element.Message.error({message: success.data.msg, duration: 1.5 * 1000, showClose: true});
            return success;
        }
        // 成功
        else if (success.data.code === 200) {
            Element.Message.success({message: success.data.msg, duration: 1.5 * 1000, showClose: true});
            return success;
        } else if (success.status === 200) {
            Element.Message.success({message: success.data.msg, duration: 1.5 * 1000, showClose: true});
            return success;
        }
        // 失败，状态码非两百
        else
        // 过期时间 d+uration，弹窗自动消失
            Element.Message.error({message: success.data.msg, duration: 1.5 * 1000, showClose: true});
        // 请求结束，返回信息，这样就不会进入到login的逻辑中
        return Promise.reject(success.data.msg)


    },
    // 错误时，除了状态码非200，区别400 401等异常
    error => {
        console.log("error.response : ", error.response);
        console.log("error : ", error);
        // if (error.response.data) {
        //     error.message = error.response.data.msg;
        // }
        if (error.response.data.status === 500) {
            console.log(500);
            Element.Message.error({message: error.response.data.msg, duration: 1.5 * 1000, showClose: true});
        }

        if (error.response.data.status === 401) {
            // 全局参数清空
            store.commit("REMOVE_INFO");
            Element.Message.error({message: error.response.data.msg, duration: 1.5 * 1000, showClose: true});
            // 跳转到登录页面
            router.push("/sysUser/login");
        }

        if (error.response.data.status === 3002) {
            console.log(500);
            Element.Message.error({message: error.response.data.msg, duration: 1.5 * 1000, showClose: true});
        } else {
            Element.Message.error({message: error.response.data.msg, duration: 1.5 * 1000, showClose: true});
        }

        // return;
        // 跳转
        return Promise.reject(error)
    }
);


// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
    config => {
        console.log("前置拦截====拿到token: ", localStorage.getItem('token'));
        // 先获取localStorage中存的token,再在请求头添加token
        if (localStorage.getItem('token') != null) {

            config.headers.Authorization = localStorage.getItem('token');
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    });


// 封装请求api方式======================

// let base = 'http://localhost:8090/';
let base = '';

//传送json格式的post请求
export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
};

//传送json的put请求
export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
};

//传送json的get请求
export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
};


//传送json的delete请求
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
};
