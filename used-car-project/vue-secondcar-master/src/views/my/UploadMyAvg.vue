<template>
    <div>

        <el-upload
                :action="uploadQiniuUrl"
                ref="upload"
                list-type="picture-card"
                :auto-upload=false
                :data="qiniuData"
                :before-upload="beforeUploadGetKey"
                :on-preview="handlePictureCardPreview"
        >
            <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <el-button type="primary" class="buttonupload" @click="submitUpload">上传图片</el-button>
    </div>
</template>

<script>
    export default {
        name: "UploadMyAvg",
        data() {
            return {

                dialogImageUrl: 'http://qrg5og3v4.hn-bkt.clouddn.com/%E9%BB%98%E8%AE%A4%E7%94%A8%E6%88%B7%E5%A4%B4%E5%83%8F.png',     //选中的某张图片的 url
                dialogVisible: true,    //图片原图展示框

                uploadQiniuUrl: "https://upload.qiniup.com",   //七牛云服务器地址
                qiniuData: {             //上传图片携带的参数
                    token: "",
                    key: "",
                },
            };
        },
        created() {
            const _this = this;
            this.getToken();   //获取token
        },
        methods: {

            getToken() {  //上传之前获取 token
                var url1 = this.$store.state.frontUrl + "/getQiniuToken?bucket=xdx97-album";
                this.$ajax.get(url1)
                    .then(response => {
                        //获取 token
                        this.qiniuData.token = response.data.token;
                    })
            },
            submitUpload() {   //提交上传
                this.$refs.upload.submit();
            },
            beforeUploadGetKey() {   //每个文件上传之前 给它一个 名字
                this.qiniuData.key =  (((1+Math.random())*0x10000)|0).toString(16).substring(1);
            },


            handlePictureCardPreview(file) {   //查看某张图片的原图
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },


        },
        computed: {},
        components: {},
    }
</script>

<style scoped>

</style>
