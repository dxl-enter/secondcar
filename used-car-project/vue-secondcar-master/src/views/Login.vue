<template>
    <div>
        <Header/>
        <el-container>
            <el-header>
                <span>请先登录 </span>
                <img class="mlogo" :src="avatarDef" alt="头像"/>
            </el-header>
            <el-main>
                <el-form
                        :model="userInfo"
                        :rules="rules"
                        ref="userInfo"
                        label-width="100px"
                        class="demo-ruleForm"
                >
                    <el-form-item label="用户名" prop="username">
                        <el-input placeholder="请输入用户名" prefix-icon="el-icon-user" clearable
                                  v-model="userInfo.username"></el-input>
                    </el-form-item>
                    <!--prop属性绑定字段名name，表单验证时，就会验证el-input元素绑定的变量-->
                    <el-form-item label="密码" prop="password">
                        <el-input placeholder="请输入密码" prefix-icon="el-icon-lock" clearable type="password"
                                  show-password="true" v-model="userInfo.password"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="code">
                        <el-input placeholder="请输入验证码" prefix-icon="el-icon-warning-outline" style="width:170px"
                                  clearable v-model="userInfo.code"
                                  @keyup.enter.native="loginForm('userInfo')"
                        ></el-input>
                        <!--alt 属性可以为图像提供替代的信息。-->
                        <img :src="verCode" class="verCode" @click="newVerCode" alt="验证码加载中...">
                    </el-form-item>

                    <el-form-item prop="rememberMe">
                        <el-checkbox label="记住我" v-model="userInfo.rememberMe" name="rememberMe"></el-checkbox>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="loginForm('userInfo')">登录</el-button>
                        <el-button type="primary" @click="registeredDialogVisible = true">注册</el-button>
                        <el-button @click="resetForm('userInfo')">重置</el-button>
                    </el-form-item>
                </el-form>

                <!--<el-button @click="goIndex()"> 去首页</el-button>-->
            </el-main>


            <!-- <div>
                 <el-dialog  :visible.sync="registeredDialogVisible"  @open="onOpen" @close="onClose" title="注册">
                     <el-form ref="registerFrom" :model="registerFrom" :rules="rules" size="medium" label-width="100px"
                              label-position="left">
                         <el-row>
                             <el-col :span="12">
                                 <el-form-item label="用户名" prop="username">
                                     <el-input v-model="registerFrom.username" placeholder="请输入用户名" clearable
                                               prefix-icon='el-icon-user' :style="{width: '100%'}"></el-input>
                                 </el-form-item>
                             </el-col>
                         </el-row>
                         <el-row>
                             <el-col :span="12">
                                 <el-form-item label="密码" prop="password">
                                     <el-input v-model="registerFrom.password" placeholder="请输入密码" clearable
                                               prefix-icon='el-icon-user-solid' show-password :style="{width: '100%'}"></el-input>
                                 </el-form-item>
                             </el-col>
                         </el-row>
                         <el-row>
                             <el-col :span="12">
                                 <el-form-item label="身份" prop="role">
                                     <el-radio-group v-model="registerFrom.role" size="medium">
                                         <el-radio v-for="(item, index) in roleOptions" :key="index" :label="item.value"
                                                   :disabled="item.disabled">{{item.label}}</el-radio>
                                     </el-radio-group>
                                 </el-form-item>
                             </el-col>
                         </el-row>
                     </el-form>
                     <div slot="footer">
                         <el-button @click="close">取消</el-button>
                         <el-button type="primary" @click="handelConfirm">确定</el-button>
                     </div>
                 </el-dialog>
             </div>
 -->

            <!--注册-->
            <el-dialog
                    class="registered-dialog"
                    title="注册"
                    :visible.sync="registeredDialogVisible"
                    width="60%"
                    :before-close="handleClose"
                    @colse="editDialogClosed"
            >
                <!--<span>这是一段信息</span>-->

                <el-form ref="registerFrom" :model="registerFrom" :rules="rules" size="medium" label-width="100px"
                         label-position="right">
                    <el-row>
                        <el-col>
                            <el-form-item label="用户名" prop="username">
                                <el-input v-model="registerFrom.username" placeholder="请输入用户名" clearable
                                          prefix-icon='el-icon-user' :style="{width: '50%'}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-form-item label="密码" prop="password">
                                <el-input v-model="registerFrom.password" placeholder="请输入密码" show-password
                                          prefix-icon="el-icon-lock" clearable
                                          :style="{width: '50%'}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col>
                            <el-form-item label="确认密码" prop="passwordConfirm">
                                <el-input v-model="registerFrom.passwordConfirm" placeholder="请输入密码" show-password
                                          prefix-icon="el-icon-lock" clearable
                                          :style="{width: '50%'}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-form-item label="身份" prop="role">
                            <el-radio-group v-model="registerFrom.roleId" @change="showSelectRole">
                                <el-radio :label="2">普通用户</el-radio>
                                <el-radio :label="1">管理员</el-radio>
                                <el-radio :label="3">卖家</el-radio>
                            </el-radio-group>

                            <!--<el-radio-group v-model="registerFrom.role" size="medium">-->
                            <!--    <el-radio v-for="(item, index) in roleOptions" :key="index" :label="item.value"-->
                            <!--              :disabled="item.disabled">{{item.label}}</el-radio>-->
                            <!--</el-radio-group>-->
                        </el-form-item>
                    </el-row>
                    <el-form-item>
                        <el-button @click="registeredDialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="registered">注册</el-button>
                    </el-form-item>
                </el-form>

            </el-dialog>

        </el-container>

        <Footer/>
    </div>
</template>

<script>
// 使用qs进行数据转换，qs在安装axios时会替换安装，不用再重新安装
import qs from "qs";
import { baseUrl, postRequest } from "../my-axios";
import Header from "./Header";
import Footer from "./Footer";
// import {postRequest} from "../my-axios";

export default {

    data () {
        //=====判断输入密码是否一致====
        var validatePass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入密码"));
            } else {
                if (this.registerFrom.passwordConfirm !== "") {
                        this.$refs.registerFrom.validateField('passwordConfirm');
                    }
                    callback();
                }
            };

            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.registerFrom.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            //=====判断输入密码是否一致====
            return {
                // 默认头像
                avatarDef: 'http://r8vi8vkpw.hn-bkt.clouddn.com/672lj5MPfoXNonjj.jpg',
                userInfo: {
                    username: "admin",
                    password: "admin",
                    code: '',
                    rememberMe: false
                },

                registeredDialogVisible: false,
                registerFrom: {
                    username: '',
                    password: '',
                    roleId: null,
                    passwordConfirm: '',
                },

                // 验证码
                verCode: "",
                // 效验规则
                rules: {
                    username: [
                        {required: true, message: "请输入用户名", trigger: "blur"},
                        {min: 3, max: 15, message: "长度在 3 到 15 个字符", trigger: "blur"}
                    ],
                    password: [
                        {required: true, message: "请输入密码", trigger: "blur"},
                        {min: 3, max: 12, message: "长度在 3 到 12 个字符", trigger: "blur"},
                        {required: true, validator: validatePass, trigger: 'blur'}
                    ],
                    code: [
                        {required: true, message: "请输入验证码", trigger: "blur"},
                        {min: 5, max: 5, message: "长度为 5 个字符", trigger: "blur"}
                    ],
                    passwordConfirm: [
                        {required: true, validator: validatePass2, trigger: 'blur'}
                    ]
                }
            };
        },
        created() {
            let _this = this;

        },
        // mounted:html加载完成后执行。执行顺序：子组件-父组件
        mounted() {
            this.newVerCode();
        },
        methods: {


            // ===============注册分割=============
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {
                    });
            },
            // 关闭窗口
            editDialogClosed() {
                this.$refs.editFormRef.resetFields();
            },
            // 注册
            registered() {
                this.$confirm('确认注册？')
                    .then(() => {
                        postRequest('/yueChi/sysUser/register', this.registerFrom).then(resp => {
                            console.log("注册请求返回的resp == ", resp);
                            this.registeredDialogVisible = false;
                        }).catch(error => {
                            console.log("注册请求失败的error", error)
                        });

                        done();
                    })
                    .catch(() => {

                    });
            },

            //显示选中的角色
            showSelectRole(data) {
                // console.log(data);
            },

            // ===============注册分割=============

            // // 去首页
            // async goIndex() {
            //     await postRequest("/carInfo/searchListPage");
            //     this.$router.push("/carInfo/carInfoIndex");
            // },


            // 登录
            loginForm(formName) {
                // 表单提交
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let _this = this;

                        //登录,先把数据转换成字符串
                        // 使用qs进行数据转换，qs在安装axios时会替换安装，不用再重新安装：
                        // qs.stringify()	转换成查询字符串，qs.parse()   转换成json对象

                        // ,{
                        //     // 设置成表单提交
                        //     headers: {
                        //         "Content-Type": "application/x-www-form-urlencoded",
                        //         // "Access-Control-Allow-Origin": "http://localhost:8080/"
                        //     },
                        // }


                        this.$axios.post("/yueChi/sysUser/login", qs.stringify(this.userInfo), {
                                // 设置成表单提交
                                headers: {
                                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                                    // "Access-Control-Allow-Origin": "http://localhost:8080/"
                                },
                            }
                        ).then(resp => {
                            // if (resp.data.)
                            //判断登录是否成功，成功则转跳到home页面
                            console.log("resp.message :", resp.data.message);
                            console.log("resp.data.username : ", resp.data.data.username);
                            console.log("resp.data : ", resp.data);

                            // 拿到请求头中的jwt
                            const jwt = resp.headers["authorization"];
                            // 拿到date信息
                            const userInfo = resp.data.data;
                            // 从localStorage获取信息
                            this.$store.commit("SET_TOKEN", jwt);
                            this.$store.commit("SET_USERINFO", userInfo);
                            // 获取用户信息
                            console.log("_this.$store.getters.getUser", this.$store.getters.getUser);
                            // 成功页面跳转到成功页面
                            this.$router.push("/yueChi/sysUser/success");
                            /*.catch(error =>{
                                console.log("跳转到/yueChi/sysUser/success 页面失败 error :" ,error)
                            });*/
                        })
                        /* .catch(error => {
                             console.log("失败");
                             console.log(error);
                             // 失败，跳到失败页面
                             this.$router.push("/yueChi/sysUser/fail");
                         })*/
                        ;
                    } else {
                        this.$message.error("请正确输入所有信息！！！");
                        return false;
                    }
                });
            },

            // 刷新验证码
            newVerCode() {
                //刷新验证码后面加上随机数防止缓存导致刷新验证码失败。也可以加时间戳
                this.verCode = baseUrl + "/captcha.jpg?m=" + Math.random();

            },

            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },

        components: {Footer, Header},

        computed: {}
    };
</script>
<style lang="less" scoped>
    .el-header,
    .el-footer {
        background-color: #b3c0d1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #d3dce6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #e9eef3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .mlogo {
        height: 90%;
        padding-left: 5px;
        margin-top: 2px;
    }

    .demo-ruleForm {
        max-width: 400px;
        margin: 0 auto;
    }

    .verCode {
        width: 100px;
        height: 40px;
        object-fit: fill;
        margin-left: 30px;
    }

    .registered-dialog {
        /*背景、圆角*/
        font-weight: bold;
        border-radius: 17px;

        box-shadow: 10px 10px 20px #adc4c9,
            -10px -10px 20px #e9ffff;
    }
</style>
