<template>
    <div>
        <h2>七牛云上传图片</h2>
        <el-button type="primary" @click="addPicture()">添加学生信息(上传图片)</el-button>
        <!-- 弹出框 -->
        <el-dialog title="增加学生信息" :visible.sync="dialogFormVisible">
            <el-form :model="sysUserInfo">
                <el-upload
                        action="http://localhost:8090/yueChi/sysUser/upload"
                        list-type="picture-card"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :on-success="uploadOk"
                >
                    <i class="el-icon-plus"></i>
                    <el-dialog :visible.sync="dialogVisible" size="tiny">
                        <img width="100%" :src="dialogImageUrl" alt />
                    </el-dialog>
                </el-upload>
                <el-form-item label="学生ID" :label-width="formLabelWidth">
                    <el-input v-model="sysUserInfo.id" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生姓名" :label-width="formLabelWidth">
                    <el-input v-model="sysUserInfo.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生性别" :label-width="formLabelWidth">
                    <el-input v-model="sysUserInfo.sex" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生年龄" :label-width="formLabelWidth">
                    <el-input v-model="sysUserInfo.age" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addPictureStudent()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                dialogImageUrl: "",
                dialogVisible: false,
                dialogFormVisible: false,
                formLabelWidth: "120px",
                sysUserInfo: {
                    id: 0,
                    username: "",
                    sex: "",
                    age: "",
                    avatar: "",
                },
            };
        },
        methods: {
            uploadOk(response, file, fileList) {
                this.sysUserInfo.avatar = response.data;
            },
            addPicture: function () {
                this.dialogFormVisible = true;
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            addPictureStudent: function () {
                this.$axios
                    .post('/yueChi/sysUser/addPicture', {
                        'id': this.sysUserInfo.id,
                        'name': this.sysUserInfo.username,
                        'sex': this.sysUserInfo.sex,
                        'age': this.sysUserInfo.age,
                        'avatar': this.sysUserInfo.avatar,
                    })
                    .then((response) => {
                        if (response.data.statusCode == 200) {
                            this.$message("add success");
                            this.dialogFormVisible = false;
                        }
                    })
                    .catch((error) => {
                        this.$message(error);
                    });
            },
        },
    };
</script>
<style>
</style>
