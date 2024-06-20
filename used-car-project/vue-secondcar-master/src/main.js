import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import axios from 'axios';
import store from './store'
import 'font-awesome/css/font-awesome.css'
import './assets/font/iconfont.css' // 阿里巴巴矢量库图标

import Vant from 'vant';
import 'vant/lib/index.css';
// 引入全局样式
import './styles/index.less'
// import './assets/css/index.css'

//引用七牛云
import * as qiniu from 'qiniu-js';
// Vue.use(qiniu);
// 挂载到vue,全局使用$qiniu
Vue.prototype.$qiniu = qiniu;


Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(Vant);

// 导入axios设置
import './my-axios'

// // 挂载，全局使用
Vue.prototype.$axios = axios;
// // axios前缀
// axios.defaults.baseURL = "http://localhost:8090/";
// // 解决session中验证码为空的问题
// axios.defaults.withCredentials = true;

// 引入VueCookie简化操作cookies
import VueCookies from 'vue-cookies'
Vue.use(VueCookies);

import Distpicker from 'v-distpicker'
Vue.component('v-distpicker', Distpicker);


// import distpicker from "distpicker/src/distpicker";


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
