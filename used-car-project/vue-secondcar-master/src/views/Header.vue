<template>
    <div>
        <!--头部布局-->
        <el-header class="main_header">
            <!--logo和项目名称-->
            <a href="/carInfo/carInfoIndex" class="left_img" title="悦驰二手车市场">
                <img class="logo" :src="logo" alt="logo"/>
            </a>
            <span class="title">悦驰二手车交易平台</span>

            <div class="avatar_header">
                <!--其他链接-->
                <div class="other_header">
                    <span><el-link icon="el-icon-house" type="primary"
                                   href="/carInfo/carInfoIndex">汽车主页</el-link></span>

                <!--    &lt;!&ndash;消息提示&ndash;&gt;
                    <span><el-link type="success" href="/blog/add">我的消息</el-link></span>

                    <el-badge :value="12" class="item">
                           <el-button size="small" @click="chat" >消息</el-button>
                       </el-badge>
                    &lt;!&ndash;聊天页面 抽屉&ndash;&gt;
                    <el-drawer
                            title="聊天咨询"
                            :visible.sync="chatDrawer"
                            size="50%"
                            :with-header="false">
                        <Chat/>
                    </el-drawer>-->

                    <span v-if="user.userId === null ">
                        <el-link type="primary" icon="el-icon-user" href='/yueChi/sysUser/login'>登录</el-link>
                    </span>
                    <span v-if=" user.userId !== null">
                    <el-link type="danger" icon="el-icon-close"
                             @click="handleCommand('logout')">退出
                    </el-link>
                    </span>
                    <span >
                        <el-buttono type="primary" icon="el-icon-user" @click="clickInitCarInfo">初始化ES信息</el-buttono>
                    </span>
                    <span v-if=" user.userId !== null">
                        <el-link type="primary" icon="el-icon-user"
                                 href="/yueChi/sysUser/myInfo">
                            {{user.username}} 欢迎您
                            <!--<span v-if="!this.user.username">游客您好</span>-->
                        </el-link>
                    </span>
                </div>
                <!--头像-->
                <div class="avatar">
                    <el-dropdown @command="handleCommand">
                        <el-avatar :src="user.avatar" icon="el-icon-user-solid"></el-avatar>
                        <!--下拉菜单-->
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-house" command="carInfoIndex">汽车首页</el-dropdown-item>
                            <el-dropdown-item icon="el-icon-user" v-if="user.userId === null " command="login">
                                登录
                            </el-dropdown-item>
                            <el-dropdown-item icon="el-icon-user" command="myInfo"
                            >个人中心
                            </el-dropdown-item>
                            <el-dropdown-item icon="el-icon-close" command="logout"
                                              v-if="user.userId !== null ">
                                退出
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>

            </div>
        </el-header>
        <el-divider></el-divider>
    </div>
</template>

<script>
import Chat from "./chat/Chat";

export default {
    name: "Header",
    data () {
        return {
            logo: "http://qt3ee54kl.hn-bkt.clouddn.com/logo-main.jpg",
            user: {
                // 默认用户名
                username: "",
                userId: null,
                // 默认头像
                avatar: "http://qt3ee54kl.hn-bkt.clouddn.com/%E9%BB%98%E8%AE%A4%E7%94%A8%E6%88%B7%E5%A4%B4%E5%83%8F.png"
                },
                chatDrawer: false,// 聊天弹窗
            };
        },
        created() {

            if (this.$store.getters.getUser != null) {
                this.user.username = this.$store.getters.getUser.name;
                this.user.userId = this.$store.getters.getUser.userId;
                this.user.avatar = this.$store.getters.getUser.avatar
            }

        },
        methods: {

            // 初始化ES信息
            clickInitCarInfo () {
                this.$axios.post("/esInitDate/initAllCarInfoToEsData").then(resp => {
                    console.log(resp);
                })
            },
            // 聊天
            chat() {
                this.chatDrawer = true;
                // 连接ws
                this.$store.dispatch('connect')
            },

            // 头像那里的el-dropdown-menu
            handleCommand(command) {
                // 点击退出、
                if (command === 'logout') {
                    this.$confirm('此操作将注销登录, 是否继续?', '退出', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        //注销登录
                        this.$axios.get("/yueChi/sysUser/logout", {
                            // localStorage 清除
                            headers: {
                                "Authorization": localStorage.getItem("token")
                            }
                        }).then(resp => {

                            console.log("delete token: ", localStorage.getItem("token"));
                            // localStorage.getItem("token")
                            // 清除全局的state
                            this.$store.commit("REMOVE_INFO");

                            // 退出登录后跳转到carInfoIndex页面

                            // 当前页面就是路由。则刷新页面
                            if (this.$route.path === '/carInfo/carInfoIndex') {
                                this.$router.go(0);
                            }
                            // 否则跳转页面
                            // else this.$router.push('/carInfo/carInfoIndex');
                            else this.$router.push({path: '/carInfo/carInfoIndex'});

                        });
                        //跳转到登录页

                    }).catch(() => {
                        /*this.$message({
                            showClose: true,
                            type: 'info',
                            message: '已取消操作'
                        });*/
                    });
                }
                ;
                // 点击个人中心
                if (command === 'myInfo') {
                    if (this.$route.path === '/yueChi/sysUser/myInfoCenter') {
                        this.$message({
                            showClose: true,
                            message: '当前页面就是个人中心页',
                            duration: 1000
                        });
                    } else this.$router.push('/yueChi/sysUser/myInfo');
                }
                ;

                // 点击首页
                if (command === 'carInfoIndex') {
                    // 当前页面就是路由。则刷新页面
                    if (this.$route.path === '/carInfo/carInfoIndex') {
                        this.$router.go(0);
                    }
                    // 否则跳转页面
                    else this.$router.push('/carInfo/carInfoIndex');
                }
                ;

                if (command === 'login') {
                    this.$router.push('/yueChi/sysUser/login')
                }
            },

            // // 头像上传
            // uploadAvg() {
            //
            //     console.log("点击了");
            //     this.$axios.post("/yueChi/sysuser/uploadAvg", null, {
            //         'Content-Type': 'multipart/form-data'
            //     }).then(resp => {
            //         console.log("文件上传请求后", resp)
            //     })
            // }
        },
        computed: {},
        components: {Chat},
    }
</script>

<style lang="less" scoped>
    /*头部布局*/
    .main_header {
        display: flex;
        flex-direction: row; /*让头像和标题在一行*/
    }

    /*logo*/
    .left_img {
        display: flex;
        flex-direction: row;
        width: 70px;
        height: 70px;
        /*http://qlivtwvef.hn-bkt.clouddn.com/logo.jpg*/
        background: url('http://qn9ywuoas.hn-bkt.clouddn.com/logo.jpg') no-repeat 0 0;
        background-size: 100% 100%;
        margin-top: 2px;
    }

    /*项目标题*/
    .title {
        text-align: center;
        margin-left: 10px;
        margin-top: 15px;
    }

    /*用户头像*/
    .avatar_header {
        display: flex;
        flex-direction: row;
        margin-left: 200px;
        margin-top: 10px;
    }

    .other_header span{

            line-height: 40px;
            margin: 0 20px;
            font-size: 20px;

    }

    /*消息*/
    .item {
        margin-top: 10px;
        margin-right: 40px;
    }

    /*头部下拉菜单样式*/
    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }
</style>
