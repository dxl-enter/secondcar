import Vue from "vue"
import VueRouter from "vue-router"
import Login from "../views/Login";


Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        redirect: '/carInfo/carInfoIndex',
        children:[

        ]
    },
    {
        path: '/yueChi/sysUser/login',
        name: 'Login',
        component: Login
    },

    // 这个路由相当于没用了、
    {
        path: '/car/carIndex',
        name: 'CarIndex',
        component: () => import(/* webpackChunkName: "about" */ '../views/CarIndex.vue')
    },

    // 测试登录失败的页面页面
    {
        path: '/yueChi/sysUser/fail',
        name: 'Fail',
        component: () => import(/* webpackChunkName: "about" */ '../views/Fail.vue')
    },

    // 退出登录的路由
    {
        path: '/yueChi/sysUser/logout',
    },

    // 登录成功的路由
    {
        path: '/yueChi/sysUser/success',
        name: 'Success',
        // redirect: '/carInfo/carInfoIndex',
        component: () => import('../views/Success.vue')
    },

    // 测试
    {
        path: '/test',
        name: 'Test',
        component: () => import('../views/Test')
    },

    // 汽车主页
    {
        path: '/carInfo/carInfoIndex',
        name: 'CarInfoIndex',
        component: () => import("../views/car/CarInfoIndex.vue")
    },

    // 汽车检索路由
    {
        path: '/carInfoScreen',
        name: 'CarInfoScreen',
        component: () => import("../views/car/CarInfoScreen.vue")
    },

    // 汽车展示路由
    {
        path: '/carListShow',
        name: 'CarListShow',
        component: () => import("../views/car/CarListShow.vue")
    },

    // 汽车详情页路由
    {
        path: '/carDetail/:carId',
        name: 'CarDetail',
        component: () => import("../views/car/CarDetail.vue")
    },

    // 测试路由
    {
        path: '/testScreen',
        name: 'TestScreen',
        component: () => import("../views/car/TestScreen.vue")
    },

    // 测试汽车信息路由
    {
        path: '/carInfo',
        name: 'CarInfo',
        component: () => import("../views/generate/carinfo.vue")
    },

    // 个人中心路由
    {
        path: '/yueChi/sysUser/myInfoCenter',
        name: 'MyInfoCenter',
        component: () => import("../views/my/MyInfoCenter.vue"),
        children: [
            {
                path: '/yueChi/sysUser/myInfo',
                name: 'MyInfo',
                component: () => import("../views/my/MyInfo.vue")
            },
            {
                path: '/yueChi/sysUser/mySellerCarInfo',
                name: 'MySellerCarInfo',
                component: () => import("../views/my/MySellerCarInfo.vue")
            },
            {
                path: '/yueChi/sysUser/mydel',
                name: 'Mydel',
                component: () => import("../views/my/Mydel.vue")
            },
            {
                path: '/yueChi/sysUser/MyAllCollectCars',
                name: 'MyAllCollectCars',
                component: () => import("../views/my/MyAllCollectCars.vue")
            },

            /*{
                path: '/sysOrder/myOrder',
                name: 'MyAllCollectCar',
                component: () => import("../views/my/MyAllCollectCars.vue")
            },*/

        ]
    },

    // 支付宝支付页面
    {
        path: '/alipay',
        name: 'PayPage',
        component: () => import("../views/pay/PayPage.vue")
    },

    // 测试支付成功页面
    {
        path: '/paySuccess',
        name: 'PaySuccess',
        component: () => import("../views/pay/PaySuccess.vue")
    },
    // 测试支付失败页面
    {
        path: '/payFail',
        name: 'PayFail',
        component: () => import("../views/pay/PayFail.vue")
    },

];

// 使用history路由
const router = new VueRouter({
    mode: 'history',
    base: process.env.BABEL_ENV,
    routes
});

// 设置登录过期时间（一天）
let EXPIRESTIME = 86400000

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {

    if (to.path === "/yueChi/sysUser/login" || to.path === "/carInfo/carInfoIndex"
      || to.name === "CarDetail"
      || to.path === "/yueChi/sysUser/register") {
        next();
    } else {
        let token = localStorage.getItem("token");

        if (token === null || token === "") {
            next("/yueChi/sysUser/login");
        } else {
            next();
        }

        /*           //页面是否登录，本地存储中是否有token(uid)数据，否：跳转登录页面
        if (token === null || token === '') {
                    next('/yueChi/sysUser/login');
                } else {// token不为空
                    let date = new Date().getTime();
                    // 如果大于就是过期了，如果小于或等于就还没过期
                    if (date - token.startTime > EXPIRESTIME) {
                        localStorage.removeItem('token');
                        localStorage.removeItem('userInfo');
                        next('/yueChi/sysUser/login');
                    } else {
                        next();
                    }
                }*/


    }
});


// router.beforeEach((to, from, next) => {
//     if (localStorage.getItem('token')) {
//         // initMenu(router, store);
//         if (!window.sessionStorage.getItem('userInfo')) {
//             //判断用户信息是否存在
//             return getRequest('/userInfo/info').then(resp => {
//                 if (resp) {
//                     //存入用户信息
//                     window.sessionStorage.setItem('userInfo', JSON.stringify(resp));
//                     store.commit('INIT_ADMIN', resp);
//                     next();
//                 }
//             })
//         }
//         next();
//     } else {
//         if (to.path == '/') {
//             next();
//         } else {
//             next('/?redirect=' + to.path);
//         }
//     }
// })


export default router
