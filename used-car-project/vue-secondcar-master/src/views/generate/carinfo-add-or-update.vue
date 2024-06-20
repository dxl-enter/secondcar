<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :close-on-click-modal="false"
            :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
            <el-form-item label="汽车品牌" prop="carBrand">
                <el-input v-model="dataForm.carBrand" placeholder="汽车品牌"></el-input>
            </el-form-item>
            <el-form-item label="车系" prop="carSeries">
                <el-input v-model="dataForm.carSeries" placeholder="车系"></el-input>
            </el-form-item>
            <el-form-item label="车型（suv、小型车。。。）" prop="carType">
                <el-input v-model="dataForm.carType" placeholder="车型（suv、小型车。。。）"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="carPrice">
                <el-input v-model="dataForm.carPrice" placeholder="价格"></el-input>
            </el-form-item>
            <el-form-item label="汽车数量" prop="carCount">
                <el-input v-model="dataForm.carCount" placeholder="汽车数量"></el-input>
            </el-form-item>
            <el-form-item label="使用年限（单位/年）" prop="carUserTime">
                <el-input v-model="dataForm.carUserTime" placeholder="使用年限（单位/年）"></el-input>
            </el-form-item>
            <el-form-item label="过户次数" prop="transferCount">
                <el-input v-model="dataForm.transferCount" placeholder="过户次数"></el-input>
            </el-form-item>
            <el-form-item label="里程（万公里）" prop="carCarMileage">
                <el-input v-model="dataForm.carCarMileage" placeholder="里程（万公里）"></el-input>
            </el-form-item>
            <el-form-item label="汽车颜色" prop="carColor">
                <el-input v-model="dataForm.carColor" placeholder="汽车颜色"></el-input>
            </el-form-item>
            <el-form-item label="汽车图片地址" prop="carPictureId">
                <el-input v-model="dataForm.carPictureId" placeholder="汽车图片地址"></el-input>
            </el-form-item>
            <el-form-item label="变数箱（手动、自动、不限）" prop="carGearBox">
                <el-input v-model="dataForm.carGearBox" placeholder="变数箱（手动、自动、不限）"></el-input>
            </el-form-item>
            <el-form-item label="排量" prop="carDisplacement">
                <el-input v-model="dataForm.carDisplacement" placeholder="排量"></el-input>
            </el-form-item>
            <el-form-item label="汽车生产日期" prop="produceTime">
                <el-input v-model="dataForm.produceTime" placeholder="汽车生产日期"></el-input>
            </el-form-item>
            <el-form-item label="配置（天窗、GPS、真皮坐垫）" prop="carConfiguration">
                <el-input v-model="dataForm.carConfiguration" placeholder="配置（天窗、GPS、真皮坐垫）"></el-input>
            </el-form-item>
            <el-form-item label="座位数" prop="carSeat">
                <el-input v-model="dataForm.carSeat" placeholder="座位数"></el-input>
            </el-form-item>
            <el-form-item label="燃料类型（柴油、机油、电力。。。）" prop="carFuelType">
                <el-input v-model="dataForm.carFuelType" placeholder="燃料类型（柴油、机油、电力。。。）"></el-input>
            </el-form-item>
            <el-form-item label="质保时间（单位/年）" prop="carQualityTime">
                <el-input v-model="dataForm.carQualityTime" placeholder="质保时间（单位/年）"></el-input>
            </el-form-item>
            <el-form-item label="国别" prop="carRegionId">
                <el-input v-model="dataForm.carRegionId" placeholder="国别"></el-input>
            </el-form-item>
            <el-form-item label="前轮胎尺寸" prop="carFrontTyre">
                <el-input v-model="dataForm.carFrontTyre" placeholder="前轮胎尺寸"></el-input>
            </el-form-item>
            <el-form-item label="后轮胎尺寸" prop="rearTyre">
                <el-input v-model="dataForm.rearTyre" placeholder="后轮胎尺寸"></el-input>
            </el-form-item>
            <el-form-item label="其他描述" prop="carDescribe">
                <el-input v-model="dataForm.carDescribe" placeholder="其他描述"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
                <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="updateTime">
                <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
            </el-form-item>
            <el-form-item label="汽车状态   0-已上架；1-未上架；2-已下架" prop="carStatus">
                <el-input v-model="dataForm.carStatus" placeholder="汽车状态   0-已上架；1-未上架；2-已下架"></el-input>
            </el-form-item>
            <el-form-item label="汽车标题" prop="carTitle">
                <el-input v-model="dataForm.carTitle" placeholder="汽车标题"></el-input>
            </el-form-item>
            <el-form-item label="用户id" prop="userId">
                <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
            </el-form-item>
            <el-form-item label="汽车主图" prop="carPicture">
                <el-input v-model="dataForm.carPicture" placeholder="汽车主图"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>

<script>
    export default {
        data() {
            return {
                visible: false,
                dataForm: {
                    carId: 0,
                    carBrand: '',
                    carSeries: '',
                    carType: '',
                    carPrice: '',
                    carCount: '',
                    carUserTime: '',
                    transferCount: '',
                    carCarMileage: '',
                    carColor: '',
                    carPictureId: '',
                    carGearBox: '',
                    carDisplacement: '',
                    produceTime: '',
                    carConfiguration: '',
                    carSeat: '',
                    carFuelType: '',
                    carQualityTime: '',
                    carRegionId: '',
                    carFrontTyre: '',
                    rearTyre: '',
                    carDescribe: '',
                    createTime: '',
                    updateTime: '',
                    carStatus: '',
                    carTitle: '',
                    userId: '',
                    carPicture: ''
                },
                dataRule: {
                    carBrand: [
                        {required: true, message: '汽车品牌不能为空', trigger: 'blur'}
                    ],
                    carSeries: [
                        {required: true, message: '车系不能为空', trigger: 'blur'}
                    ],
                    carType: [
                        {required: true, message: '车型（suv、小型车。。。）不能为空', trigger: 'blur'}
                    ],
                    carPrice: [
                        {required: true, message: '价格不能为空', trigger: 'blur'}
                    ],
                    carCount: [
                        {required: true, message: '汽车数量不能为空', trigger: 'blur'}
                    ],
                    carUserTime: [
                        {required: true, message: '使用年限（单位/年）不能为空', trigger: 'blur'}
                    ],
                    transferCount: [
                        {required: true, message: '过户次数不能为空', trigger: 'blur'}
                    ],
                    carCarMileage: [
                        {required: true, message: '里程（万公里）不能为空', trigger: 'blur'}
                    ],
                    carColor: [
                        {required: true, message: '汽车颜色不能为空', trigger: 'blur'}
                    ],
                    carPictureId: [
                        {required: true, message: '汽车图片地址不能为空', trigger: 'blur'}
                    ],
                    carGearBox: [
                        {required: true, message: '变数箱（手动、自动、不限）不能为空', trigger: 'blur'}
                    ],
                    carDisplacement: [
                        {required: true, message: '排量不能为空', trigger: 'blur'}
                    ],
                    produceTime: [
                        {required: true, message: '汽车生产日期不能为空', trigger: 'blur'}
                    ],
                    carConfiguration: [
                        {required: true, message: '配置（天窗、GPS、真皮坐垫）不能为空', trigger: 'blur'}
                    ],
                    carSeat: [
                        {required: true, message: '座位数不能为空', trigger: 'blur'}
                    ],
                    carFuelType: [
                        {required: true, message: '燃料类型（柴油、机油、电力。。。）不能为空', trigger: 'blur'}
                    ],
                    carQualityTime: [
                        {required: true, message: '质保时间（单位/年）不能为空', trigger: 'blur'}
                    ],
                    carRegionId: [
                        {required: true, message: '国别不能为空', trigger: 'blur'}
                    ],
                    carFrontTyre: [
                        {required: true, message: '前轮胎尺寸不能为空', trigger: 'blur'}
                    ],
                    rearTyre: [
                        {required: true, message: '后轮胎尺寸不能为空', trigger: 'blur'}
                    ],
                    carDescribe: [
                        {required: true, message: '其他描述不能为空', trigger: 'blur'}
                    ],
                    createTime: [
                        {required: true, message: '创建时间不能为空', trigger: 'blur'}
                    ],
                    updateTime: [
                        {required: true, message: '更新时间不能为空', trigger: 'blur'}
                    ],
                    carStatus: [
                        {required: true, message: '汽车状态   0-已上架；1-未上架；2-已下架不能为空', trigger: 'blur'}
                    ],
                    carTitle: [
                        {required: true, message: '汽车标题不能为空', trigger: 'blur'}
                    ],
                    userId: [
                        {required: true, message: '用户id不能为空', trigger: 'blur'}
                    ],
                    carPicture: [
                        {required: true, message: '汽车主图不能为空', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            init(id) {
                this.dataForm.carId = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.carId) {
                        this.$http({
                            url: this.$http.adornUrl(`/yueChi/carinfo/info/${this.dataForm.carId}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({data}) => {
                            if (data && data.code === 0) {
                                this.dataForm.carBrand = data.carInfo.carBrand
                                this.dataForm.carSeries = data.carInfo.carSeries
                                this.dataForm.carType = data.carInfo.carType
                                this.dataForm.carPrice = data.carInfo.carPrice
                                this.dataForm.carCount = data.carInfo.carCount
                                this.dataForm.carUserTime = data.carInfo.carUserTime
                                this.dataForm.transferCount = data.carInfo.transferCount
                                this.dataForm.carCarMileage = data.carInfo.carCarMileage
                                this.dataForm.carColor = data.carInfo.carColor
                                this.dataForm.carPictureId = data.carInfo.carPictureId
                                this.dataForm.carGearBox = data.carInfo.carGearBox
                                this.dataForm.carDisplacement = data.carInfo.carDisplacement
                                this.dataForm.produceTime = data.carInfo.produceTime
                                this.dataForm.carConfiguration = data.carInfo.carConfiguration
                                this.dataForm.carSeat = data.carInfo.carSeat
                                this.dataForm.carFuelType = data.carInfo.carFuelType
                                this.dataForm.carQualityTime = data.carInfo.carQualityTime
                                this.dataForm.carRegionId = data.carInfo.carRegionId
                                this.dataForm.carFrontTyre = data.carInfo.carFrontTyre
                                this.dataForm.rearTyre = data.carInfo.rearTyre
                                this.dataForm.carDescribe = data.carInfo.carDescribe
                                this.dataForm.createTime = data.carInfo.createTime
                                this.dataForm.updateTime = data.carInfo.updateTime
                                this.dataForm.carStatus = data.carInfo.carStatus
                                this.dataForm.carTitle = data.carInfo.carTitle
                                this.dataForm.userId = data.carInfo.userId
                                this.dataForm.carPicture = data.carInfo.carPicture
                            }
                        })
                    }
                })
            },
            // 表单提交
            dataFormSubmit() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/yueChi/carinfo/${!this.dataForm.carId ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'carId': this.dataForm.carId || undefined,
                                'carBrand': this.dataForm.carBrand,
                                'carSeries': this.dataForm.carSeries,
                                'carType': this.dataForm.carType,
                                'carPrice': this.dataForm.carPrice,
                                'carCount': this.dataForm.carCount,
                                'carUserTime': this.dataForm.carUserTime,
                                'transferCount': this.dataForm.transferCount,
                                'carCarMileage': this.dataForm.carCarMileage,
                                'carColor': this.dataForm.carColor,
                                'carPictureId': this.dataForm.carPictureId,
                                'carGearBox': this.dataForm.carGearBox,
                                'carDisplacement': this.dataForm.carDisplacement,
                                'produceTime': this.dataForm.produceTime,
                                'carConfiguration': this.dataForm.carConfiguration,
                                'carSeat': this.dataForm.carSeat,
                                'carFuelType': this.dataForm.carFuelType,
                                'carQualityTime': this.dataForm.carQualityTime,
                                'carRegionId': this.dataForm.carRegionId,
                                'carFrontTyre': this.dataForm.carFrontTyre,
                                'rearTyre': this.dataForm.rearTyre,
                                'carDescribe': this.dataForm.carDescribe,
                                'createTime': this.dataForm.createTime,
                                'updateTime': this.dataForm.updateTime,
                                'carStatus': this.dataForm.carStatus,
                                'carTitle': this.dataForm.carTitle,
                                'userId': this.dataForm.userId,
                                'carPicture': this.dataForm.carPicture
                            })
                        }).then(({data}) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message.error(data.msg)
                            }
                        })
                    }
                })
            }
        }
    }
</script>
