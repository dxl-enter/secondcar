import Vue from "vue"
import Vuex from "vuex"
import { getRequest } from "../my-axios";
import SockJs from "sockjs-client";
import Stomp from "stompjs";
// 1.安装插件
Vue.use(Vuex);

// 2. 创建对象
// const store = new Vuex.Store({})

// 3. 导入对象。之后在main.js中挂机即import, 之后再其他页面可以用$store来获取这个对象。
// export default store

const now = new Date();

// 此处2。 3. 都一起写了
const store = new Vuex.Store({
    // state放置属性，类似于实体类
    // 一般可以放用户基本信息，token。及多个页面的公共数据
    state: {
        token: '',
        userInfo: window.JSON.parse(localStorage.getItem("userInfo")),
        carInfoModel: [],

        // 聊天
        /*sessions: [
            {
                id: 1,
                user: {
                    name: '示例介绍',
                    img: '../src/assets/images/2.png'
                },
                messages: [{
                    content: 'Hello，这是一个基于Vue + Vuex + Webpack构建的简单chat示例，聊天记录保存在localStorge, 有什么问题可以通过Github Issue问我。',
                    date: now
                }, {
                    content: '项目地址(原作者): https://github.com/coffcer/vue-chat',
                    date: now
                }, {
                    content: '本项目地址(重构): https://github.com/is-liyiwei',
                    date: now
                }]
            }, {
                id: 2,
                user: {
                    name: 'webpack',
                    img: '../src/assets/images/3.jpg'
                },
                messages: [{
                    content: 'Hi，我是webpack哦',
                    date: now
                }]
            }],*/
        sessions: {},
        currentUser: window.JSON.parse(localStorage.getItem("userInfo")),
        chatList: [],
        currentSession: null,
        filterKey: '',
        // 连接
        stomp: null,
    },
    // set,修改state的值
    mutations: {
        // 默认有个参数state
        /*
        mutations: 里面的方法必须是同步方法
        actions： 里面的方法必须是异步方法

        更新操作：
            vue页面调用方法时，方法体内
                    1. 普通的提交封装
                    this.$store.commit("方法名"，参数)
                    2. 特殊的提交封装，提交的是对象，
                    this.$store.commit({
                        type: mutations定义的方法
                        payload
                    })
            在mutations定义， 方法体内
                    参数为payload,可以是一个参数，一个对象，一个键值对，json
                    1. state.属性名 +
                        state.属性名.payload
                    2. state.属性名.push（参数） |  state.属性名.push（payload）
         */

        // 给token赋值
        SET_TOKEN: (state, token) => {
            state.token = token;
            // token存入localStorage中，反正浏览器关闭后token失效

            // let paramss = {name: 'token', value: token};
            // var datas = Object.assign(paramss, { startTime: new Date().getTime() });
            // localStorage.setItem("token", JSON.stringify(datas));
            localStorage.setItem("token", token)
        },
        SET_USERINFO: (state, userInfo) => {
            state.userInfo = userInfo;
            // token存入localStorage中，反正浏览器关闭后token失效
            localStorage.setItem("userInfo", window.JSON.stringify(userInfo))
        },
        SET_CAR_INFO_MODEL: (state, carInfoModel) => {
            state.carInfoModel = carInfoModel;
            localStorage.setItem("carInfoModel", JSON.stringify(carInfoModel));
        },
        // 移除
        REMOVE_INFO: (state) => {
            state.token = '';
            state.userInfo = {};
            // token存入localStorage中，反正浏览器关闭后token失效
            localStorage.removeItem('token');
            localStorage.removeItem('userInfo');
            // localStorage
            // localStorage.setItem("token", '');
            // localStorage.setItem("userInfo", window.JSON.stringify(''))
        },

        // 聊天
        changeCurrentSession(state, currentSession) {
            state.currentSession = currentSession;
        },
        addMessage(state, msg) {
            let mss = state.sessions[state.currentUser.name + '#' + msg.to];
            if (!mss) {
                // state.sessions[state.currentUser.name + '#' + msg.to] = [];
                Vue.set(state.sessions,state.currentUser.name + '#' + msg.to,{})
            }
            state.sessions[state.currentUser.name + '#' + msg.to].push({
                content: msg.content,
                date: new Date(),
                self: !msg.notSelf
            })
        },
        INIT_DATA(state) {
            // 浏览器本地的历史记录
            let data = localStorage.getItem('vue-chat-session');
            //console.log(data)
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        // 初始化聊天列表
        INIT_CHAT_LIST(state, data) {
            state.chatList = data.data;
            console.log(state.chatList);
        }

    },
    // get
    getters: {
        getUser: state => {
            return state.userInfo;
        },
        getStoreCarInfoMode: state => {
            return state.carInfoModel;
        },
        getToken: state => {
            return state.token;
        }
    },
    // 异步操作时使用，
    actions: {
        // 连接ws
        connect(context) {
            context.state.stomp = Stomp.over(new SockJs('/ws/ep'));
            // 获取token
            let token = window.localStorage.getItem("token");
            context.state.stomp.connect({'authorization': token},
                success => {
                    // 发送消息
                    context.state.stomp.subscribe("/user/queue/chat", msg => {
                        console.log("msg", msg);
                        console.log(msg.body);
                        let receiveMSg = JSON.parse(msg.body);
                        receiveMSg.notSelf = true;
                        receiveMSg.to = receiveMsg.from;
                        context.commit('addMessage',receiveMSg);
                    })
                },
                error => {

                });
        },

        /*
        actions： 里面的方法必须是异步方法
            默认的参数context: 上下文，可以类似为state
                传递的参数： payload
            方法体内： context.commit('同步里的方法名',)
        异步回调，
            一、
            异步方法名（context,payload）{
                方法（
                context.commit('同步里的方法名')
                payload.message(参数名称)
                payload.success()
                ）

            }
             二、
              异步方法名（context,payload）{
                return new Promise(resolve,reject) =>{
                    方法名(
                        ()=>{
                            context.commit('同步里的方法名')

                            resolve(修改)
                        }
                    )
                }

            }


            vue页面内使用：  this.$store.dispatch('异步里的方法名'，{参数})

            异步回调，
                在异步里改了数据后通知别人，

                实现方案：
                一、
                this.$store.dispatch('同步里的方法名',{
                        message(参数名称): "我是携带的信息",
                        success: ()->{
                            .....
                        }
                })

                二、
                 this.$store.dispatch('同步里的方法名', 参数)
                 .then(res => {
                        .....
                 })


         */
        initData(context) {
            context.commit('INIT_DATA');
            // 获取聊天列表
            console.log(this.getters.getUser);
            console.log(window.location.pathname);
            // 当前url
            let url = window.location.pathname;
            // 截取出carId.前11位是/carDetail/
            console.log(url.substring(11));
            let carId = url.substring(11);
            // this.$route.params  /chat/getChatListByUserId/

            getRequest("/sysUserChatMessage/getChatList/" + this.getters.getUser.userId + '/' + carId).then(resp => {
                if (resp) {
                    console.log("当前聊天列表resp ", resp);
                    context.commit('INIT_CHAT_LIST', resp.data);
                }
            })
        }
    },


    modules: {
        // 类似于state,把state的东西模块化，
        /*
            默认参数 state getters rootState


        a模块
        // 方式一
        a{
            state: {},
            mutations: {},
            actions: {},
            getters:{}
        }

        // 方式二
        const moduleA = {
            state: {
                name: ''
            },
            mutations: {},
            actions: {
                默认参数 context: 上下文，只调用自己模块的东西，可以拿到rootState，根的东西
            },
            getters:{}
        }
        store中 modules: {
            a: moduleA
        }


        使用store.a

        $store.state.a.属性名

        this.$store.commit('方法名',{参数})





         */

    }

});


store.watch(function (state) {
    return state.sessions
}, function (val) {
    console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
    deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
});


export default store;
