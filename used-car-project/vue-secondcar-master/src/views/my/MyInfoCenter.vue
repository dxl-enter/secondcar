<template>

    <div>
        <div>
            <Header/>
            <div>
                <div id="magicalDragScene" class="mc-root">
                    <el-row>
                        <el-col :span="6">
                            <el-tooltip class="item" effect="dark" content="点击修改头像" placement="left">
                                <el-upload
                                        action="http://localhost:8090/yueChi/sysUser/upload"
                                        :show-file-list="false"
                                        :headers="this.header"
                                        :before-upload="beforeAvatarUpload"
                                        :on-success="uploadOk"
                                        :on-error="handleError"
                                >
                                    <!--默认头像-->
                                    <el-avatar class="avatar" shape="square" :size="80" :src="sysUserInfo.avatar">

                                    </el-avatar>
                                </el-upload>

                            </el-tooltip>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="6">
                            <el-menu class="el-menu-all" mode="vertical" @select="menuClick">
                                <!--<p>-->
                                <!--    <b>个人中心</b>-->
                                <!--</p>-->
                                <!--个人信息-->
                                <el-menu-item class="person-menu" index="/yueChi/sysUser/myInfo">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">个人信息</span>
                                </el-menu-item>

                                <!--我的车辆-->
                                <el-menu-item class="myCar-menu" index="/yueChi/sysUser/mySellerCarInfo">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我的车辆</span>
                                </el-menu-item>

                                <!--我的收藏的车辆-->
                                <el-menu-item class="person-favorites-menu" index="/yueChi/sysUser/MyAllCollectCars">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我的收藏的车辆</span>
                                </el-menu-item>

                                <el-menu-item class="person-order-menu" index="/sysOrder/myOrder">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我的订单</span>
                                </el-menu-item>

                            </el-menu>
                        </el-col>

                        <!--主要内容展示-->
                        <el-col :span="18">
                            <el-main class="center-main">
                                <!--点击左侧菜单后此处显示不同的内容。-->
                                <router-view/>
                            </el-main>
                        </el-col>

                    </el-row>

                    <!--<div>-->
                    <!--    <Footer/>-->
                    <!--</div>-->


                </div>
            </div>

            <el-dialog title="上传头像" :visible.sync="showUploadDialog">
                <el-upload
                        action="http://localhost:8090/yueChi/sysUser/upload"
                        list-type="picture-card"
                        :data="sysUserInfo"
                        :headers="this.header"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :on-success="uploadOk"
                >
                    <i class="el-icon-plus"></i>
                    <el-dialog :visible.sync="dialogVisible" size="tiny">
                        <img width="100%" :src="dialogImageUrl" alt/>
                    </el-dialog>
                </el-upload>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="showUploadDialog = false">取 消</el-button>
                    <el-button type="primary" @click="addPicture">确 定</el-button>
                </div>
            </el-dialog>

        </div>
        <!--尾部-->
        <Footer/>
    </div>

</template>

<script>
    import Header from "../Header";
    import Footer from "../Footer";

    export default {
        name: "MyInfoCenter",
        data() {
            return {
                header: {},
                dialogImageUrl: "",
                showUploadDialog: false,
                dialogVisible: false,
                sysUserInfo: {
                    userId: '',
                    avatar: '',
                }
            };
        },
        created() {
            const _this = this;

            this.initThisPage();
        },
        methods: {
            // 菜单选中
            menuClick(index) {
                this.$router.push(index);
            },


            initThisPage() {
                console.log("Vuex中的", this.$store.getters.getUser);
                this.sysUserInfo = this.$store.getters.getUser;
                console.log("个人中心初始化后", this.sysUserInfo);
                this.header = {
                    'Authorization': localStorage.getItem('token'),
                };
            },

            // =======头像上传======

            // 上传之前判断
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },


            showUploadDialogToTrue() {
                this.showUploadDialog = true;
            },
            addPicture() {
                this.$axios
                    .post('/yueChi/sysUser/addPicture', this.sysUserInfo)
                    .then((response) => {
                        if (response.data.statusCode == 200) {
                            this.$message("add success");
                            this.showUploadDialog = false;
                        }
                    })
                    .catch((error) => {
                        this.$message(error);
                    });
            },

            // 文件上传成功时的钩子
            uploadOk(response, file, fileList) {
                console.log("上传头像后：", response);
                // 上传成功更新值。
                this.$store.commit("SET_USERINFO", response.data);
                // 刷新页面
                this.$router.go(0);
            },

            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },

            // handleSuccess(response, file, fileList) {
            //     console.log("文件上传成功");
            //     console.log(response);
            //     // this.sysUserInfo.avatar = response.data.data.url;
            //     // this.$message.success(this.ForumAndTag.forum.first_picture)
            // },

            // 上传失败
            handleError(err, file, fileList) {
                console.log("文件上传失败 err", err);
                this.$message({
                    showClose: true,
                    type: 'info',
                    message: '文件上传失败'
                });
            }

            // =======头像上传======
        },
        computed: {},
        components: {Footer, Header},
    }
</script>

<style scoped>

    /*==========侧菜单样式==========*/
    /*background: #ddeaee;*/
    .person-menu, .person-favorites-menu, .person-order-menu, .center-main, .myCar-menu {
        font-weight: bold;
        border-radius: 50px;

        box-shadow: 10px 10px 20px #adc4c9,
        -10px -10px 20px #e9ffff;
    }

    .avatar {
        font-weight: bold;
        border-radius: 50px;
        background: #cbe7ec;
        box-shadow: 20px 20px 60px #adc4c9,
        -20px -20px 60px #e9ffff;
    }

    /*==========侧菜单样式==========*/
</style>
