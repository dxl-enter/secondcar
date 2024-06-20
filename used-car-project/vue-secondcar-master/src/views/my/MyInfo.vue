<template>
    <!--个人中心-->
    <div>

        <!--个人信息的表单-->
        <div>
            <el-form ref="sysUserInfo" :model="sysUserInfo" :rules="rules" label-width="80px">
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="sysUserInfo.username" placeholder="请输入用户名" clearable
                                      :style="{width: '100%'}">
                            </el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="8">
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="sysUserInfo.name" placeholder="请输入姓名" clearable
                                      :style="{width: '100%'}">
                            </el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="8">
                        <el-form-item label="性别" prop="sex">
                            <el-radio-group v-model="sysUserInfo.sex" size="medium">
                                <!--<el-radio v-for="(item, index) in sexOptions" :key="index" :label="item.value"-->
                                <!--          :disabled="item.disabled">{{item.label}}</el-radio>-->

                                <el-radio :label="0">男</el-radio>
                                <el-radio :label="1">女</el-radio>

                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="8">
                        <el-form-item label="生日" prop="birthday">
                            <el-date-picker v-model="sysUserInfo.birthday" format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            :style="{width: '100%'}" placeholder="请选择生日" clearable></el-date-picker>
                        </el-form-item>
                    </el-col>

                    <el-col :span="8">
                        <el-form-item label="电话" prop="phone">
                            <el-input v-model="sysUserInfo.phone" placeholder="请输入电话" clearable
                                      :style="{width: '100%'}">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="邮箱" prop="email ">
                            <el-input v-model="sysUserInfo.email" placeholder="请输入邮箱" clearable
                                      :style="{width: '100%'}">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


                <!--
                                &lt;!&ndash;地址管理&ndash;&gt;
                                <el-row class="third" style="">
                                    <el-col :span="6">
                                        &lt;!&ndash;style="width: 20%;float: left;text-align: left"&ndash;&gt;
                                        <el-form-item label="省" prop="province">
                                            <el-input v-model="sysUserInfo.province" placeholder="请输入省份" clearable>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>

                                    <el-col :span="6">

                                        <el-form-item label="市" prop="address">
                                            <el-input v-model="sysUserInfo.detailedAddress" placeholder="请输入城市"
                                                      clearable>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>

                                    <el-col :span="6">
                                        <el-form-item label="区\县" prop="city">
                                            <el-input v-model="sysUserInfo.city" placeholder="请输入区\县" clearable>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>

                                </el-row>

                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="详细地址" prop="area">
                                            <el-input v-model="sysUserInfo.area" placeholder="请输入详细地址" clearable>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                    &lt;!&ndash;<el-col :span="8">&ndash;&gt;
                                    &lt;!&ndash;    <el-form-item size="large">&ndash;&gt;
                                    &lt;!&ndash;        <el-button round>圆角按钮</el-button>&ndash;&gt;
                                    &lt;!&ndash;    </el-form-item>&ndash;&gt;
                                    &lt;!&ndash;</el-col>&ndash;&gt;
                                </el-row>

                -->

                <el-row>
                    <el-col :span="14">
                        <el-form-item label="地址" prop="address" id="address-form-item" :class="wrapper">
                            <v-distpicker
                                    id="address-all"
                                    :province="sysUserInfo.province"
                                    :city="sysUserInfo.city"
                                    :area="sysUserInfo.area"
                                    @province="onChangeProvince"
                                    @city="onChangeCity"
                                    @area="onChangeArea">
                            </v-distpicker>
                        </el-form-item>
                    </el-col>

                    <el-col :span="10">
                        <el-form-item id="address-del-form-item" label="详细地址" prop="detailedAddress ">
                            <el-input id="address-del-input" v-model="sysUserInfo.detailedAddress" placeholder="请输入详细地址"
                                      clearable>
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
                <el-form-item size="large">
                    <el-button icon="el-icon-edit" type="primary" @click="submitForm" round>修改个人信息</el-button>
                    <el-button icon="el-icon-edit" @click="showUpdatePasswordVie" round>修改密码</el-button>
                    <el-button icon="el-icon-edit" @click="resetForm" round>重置</el-button>
                </el-form-item>
            </el-form>

        </div>

        <el-dialog
                title="更新密码"
                :visible.sync="passwordDialogVisible"
                width="35%">
            <el-form :model="passwordRuleForm" status-icon :rules="passwordRules" ref="passwordRuleForm"
                     label-width="100px"
                     class="demo-ruleForm">
                <el-form-item label="请输入旧密码" prop="oldPass">
                    <el-input type="password" v-model="passwordRuleForm.oldPass" clearable
                              autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="请输入新密码" prop="password">
                    <el-input type="password" v-model="passwordRuleForm.password" clearable
                              autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="checkPass">
                    <el-input type="password" v-model="passwordRuleForm.checkPass" clearable
                              autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitPasswordForm('passwordRuleForm')">提交</el-button>
                    <el-button @click="resetPasswordForm('passwordRuleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--

                <el-form ref="address" :model="address" label-position="left" label-width="40px">
                    <el-col :span="16">

                    <el-form-item label="地址" prop="address">
                        <v-distpicker
                                :province="address.province"
                                :city="address.city"
                                :area="address.area"
                                @province="onChangeProvince"
                                @city="onChangeCity"
                                @area="onChangeArea">
                        </v-distpicker>
                    </el-form-item>
                    <el-form-item size="large">
                        <el-button icon="el-icon-edit" round>地址管理</el-button>
                        <el-button icon="el-icon-edit" round>修改密码</el-button>
                        <el-button type="primary" @click="selectAddress">修改地址</el-button>
                        <el-button @click="resetForm">重置</el-button>
                    </el-form-item>
                    </el-col>
                </el-form>
        -->

        <!--<Footer/>-->
    </div>
</template>


<script>
    import Header from "../Header";
    import Footer from "../Footer";
    import {postRequest, putRequest, getRequest} from "../../my-axios";

    export default {
        name: "MyInfo",
        data() {
            // ======修改密码的校验规则=====

            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.passwordRuleForm.checkPass !== '') {
                        this.$refs.passwordRuleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.passwordRuleForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            // ======修改密码的校验规则=====

            return {

                // ======修改密码的=====
                passwordDialogVisible: false,
                passwordRuleForm: {
                    password: '',
                    checkPass: '',
                    oldPass: ''
                },
                passwordRules: {
                    password: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                    oldPass: [
                        {validator: validatePass, trigger: 'blur'}
                    ]
                },

                // ======修改密码的=====


                //==================
                //头像上传、个人信息
                sysUserInfo: {
                    // id: 0,
                    username: '', // 用户名

                    name: '',// 姓名
                    sex: '',
                    birthday: '',
                    addressId: '',
                    //地址选择
                    //地址选择
                    address: {
                        //地址选择
                        province: '',
                        city: '',
                        area: '',
                    },

                    //地址选择
                    province: '',
                    city: '',
                    area: '',
                    detailedAddress: '',


                    phone: '',
                    email: '',
                    avatar: "http://qrg5og3v4.hn-bkt.clouddn.com/%E9%BB%98%E8%AE%A4%E7%94%A8%E6%88%B7%E5%A4%B4%E5%83%8F.png",
                },

                // 规则
                rules: {
                    username: [{
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    }],
                    sex: [{
                        required: true,
                        message: '性别不能为空',
                        trigger: 'change'
                    }],
                    birthday: [{
                        required: true,
                        message: '请选择生日',
                        trigger: 'change'
                    }],
                    // address: [{
                    //     required: true,
                    //     message: '请输入地址',
                    //     trigger: 'blur'
                    // }],

                    phone: [
                        {required: true, message: "请输入电话", trigger: "blur"},
                        {
                            pattern: /^1(3|4|5|7|8|9)\d{9}$/,
                            message: '电话格式错误',
                            trigger: 'blur'
                        },

                        {min: 6, max: 11, message: "长度在 6 到 11 个字符", trigger: "blur"},
                    ],
                    email: [{
                        required: true,
                        message: '请输入邮箱',
                        trigger: 'blur'
                    }, {
                        pattern: /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/,
                        message: '邮箱格式错误',
                        trigger: 'blur'
                    }],
                },

                //地址选择
                // address: {
                //     //地址选择
                //     province: '',
                //     city: '',
                //     area: '',
                // },


            };
        },

        mounted() {
            // TODO element-ui只能通过document跳转表单组件的样式
            document.getElementById('address-form-item').style.width = '50px';
            document.getElementById('address-del-form-item').style.width = '500px';
            document.getElementById('address-all').style.height = '120px';
            document.getElementById('address-all').style.width = '500px';
            // document.getElementsByClassName('distpicker-address-wrapper').style.width = '200px'
        },
        created() {
            // var userInfo = localStorage.getItem('userInfo');
            // console.log(userInfo);
            /* if ( !== null) {
                 userInfo = locarStorage.getItem('userInfo');
                 let parse = JSON.parse(userInfo);

                 console.log("parse", parse);

                 // TODO 回显。通过localStorage回显。先把地址存入localStorage JSON.stringify来序列化对象存。取出来的时候也需要JSON.parse一下
                 Object.keys(this.sysUserInfo).forEach(key => {
                     this.sysUserInfo[key] = parse[key]
                 });

                 console.log(this.sysUserInfo);

             }*/

            this.initPage();
        },

        methods: {

            /* // 选择地址
             selectAddress() {
                 this.$refs['address'].validate(valid => {
                     if (!valid) return;

                     this.$confirm('确认修改？')
                         .then(_ => {
                             // 确认提交
                             postRequest('/yueChi/sysUser/getAddress', this.address).then(resp => {

                                 console.log("提交修改个人信息的表单的请求后的resp : ", resp);
                                 localStorage.setItem("address", JSON.stringify(resp.data.data));
                             })


                         })
                         .catch(_ => {

                         });
                 })

             },*/

            // 初始化个人用户的数据
            initPage() {
                // 重vuex状态管理中获取
                let userInfo = this.$store.getters.getUser;
                this.sysUserInfo = this.$store.getters.getUser;

                // this.sysUserInfo = userInfo;
            },


            // =====地址选择与回显======
            /* //选择省市县
             selected(data) {
                 console.log("选择的地址： ", data);

                     this.sysUserInfo.address.province.code = data.province.code;
                     this.sysUserInfo.address.city.code = data.city.code;
                     this.sysUserInfo.address.area.code = data.area.code;
                     console.log("选择赋值后",this.sysUserInfo.address);

                     // 选择后将地址赋值。
                     // TODO 解决问题： 进入页面后地址是未定义的。关键点：先将原先的address清空。
                     // this.sysUserInfo.address = {};
                     this.sysUserInfo.address.province = data.province.code;
                     this.sysUserInfo.address.city = data.city.code;
                     this.sysUserInfo.address.area = data.area.code;
                     console.log("选择地址后的userInfo：", this.sysUserInfo);


             },*/

            onChangeProvince(data) {
                this.sysUserInfo.province = data.value;
                console.log(this.address.province)
            },
            onChangeCity(data) {
                this.sysUserInfo.city = data.value;
                console.log(this.address.city)
            },
            onChangeArea(data) {
                this.sysUserInfo.area = data.value;
                console.log(this.sysUserInfo.area)
            },

            onSelected(data) {
                this.address.province = data.province.value;
                this.address.city = data.city.value;
                this.address.area = data.area.value;
            },


            // =====地址选择与回显======


            // ==============
            // 弹窗
            handleClose(done) {
                this.$confirm('确认修改？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {
                    });
            },
            // 修改个人信息。
            submitForm() {
                this.$refs['sysUserInfo'].validate(valid => {
                    if (!valid) return;

                    // TODO 提交表单
                    this.$confirm('确认修改？')
                        .then(_ => {
                            // 确认提交
                            postRequest('/yueChi/sysUser/updateMyInfo', this.sysUserInfo).then(resp => {
                                console("提交修改个人信息的表单的请求后的resp : ", resp);
                                this.$store.commit("SET_USERINFO", resp.data.data);
                                // this.sysUserInfo = resp.data.data;
                            })


                        })
                        .catch(_ => {

                        });
                })
            },
            resetForm() {
                this.$refs['sysUserInfo'].resetFields()
            },


            // ==============

            //图片上传成功
            handleSuccess(res, file) {
                this.myImageUrl = this.tmpImageUrl
            },


            // ======修改密码的=====

            submitPasswordForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.passwordRuleForm.userId = this.sysUserInfo.userId;
                        putRequest('/yueChi/sysUser/updateSysUserPassword', this.passwordRuleForm).then(resp => {
                            if (resp) {
                                //更新密码成功后退出登录
                                getRequest('/yueChi/sysUser/logout');
                                this.$store.commit("REMOVE_INFO");

                                this.$router.push('/');
                            }
                        })
                        /*.catch(error => {
                                this.$message({
                                    message: error.response.data.msg,
                                    type: 'error',
                                    duration: 1500,
                                })

                            }
                        )*/
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetPasswordForm(formName) {
                this.$refs[formName].resetFields();
            },
            showUpdatePasswordVie() {
                this.passwordDialogVisible = true;
            },

            // ======修改密码的=====

        },
        computed: {},
        components: {Footer, Header},
    }
</script>


<style scoped>

    /*头像上传*/
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        padding: 0px;
        width: 100px;
        min-height: 100px;
        line-height: 0;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader .avatar {
        width: 100%;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }

    /*头像上传*/


</style>






