<template>
    <div class="home">
        <div class="logo">
            <!--<img  alt="logo" :src="logo">-->
        </div>
        <h1>登录成功!</h1>

        <h1>
            <span >
                {{count}}秒后
            </span>
            即将自动跳转到首页
        </h1>
        <div class="go_home">
            <el-button type="" @click="toHome"> 去首页 </el-button>
        </div>

        <div class="exit_btn">
            <el-button type="" @click="logout">安全退出</el-button>
        </div>
    </div>
</template>

<script>
    // @ is an alias to /src
    // import HelloWorld from '@/components/HelloWorld.vue'

    export default {
        name: 'Success',
        data() {
            return {
                count: '',//倒计时
                logo: 'http://qrg5og3v4.hn-bkt.clouddn.com/logo-main.jpg', // 默认图标
            }
        },
        created() {
            this.clickJump();
        },

        methods: {

            //几秒后进入跳转页面
            clickJump() {
                const timejump = 1 ;
                if (!this.timer) {
                    this.count = timejump;
                    this.show = false;
                    this.timer = setInterval(() => {
                        if (this.count > 0 && this.count <= timejump) {
                            this.count--;
                        } else {
                            this.show = true;
                            clearInterval(this.timer);
                            this.timer = null;
                            //跳转的页面写在此处
                            this.$router.push({path: '/carInfo/carInfoIndex'});
                        }
                    }, 100)
                }
            },

            // 退出， localStorage 清除
            logout() {
                let _this = this;
                this.$axios.get("/yueChi/sysUser/logout", {
                    // localStorage 清除
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(resp => {

                    console.log("delete token: ", localStorage.getItem("token"));
                    // 清除全局的state
                    this.$store.commit("REMOVE_INFO");

                    // 当前页面就是路由。则刷新页面
                    if (this.$route.path === '/carInfo/carInfoIndex') {
                        this.$router.go(0);
                    }
                    // 否则跳转页面
                    else this.$router.push('/carInfo/carInfoIndex');
                })
            },

            toHome() {
                this.$router.push('/carInfo/carInfoIndex');
            },
        }
    }
</script>

<style lang="less" scoped>

</style>
