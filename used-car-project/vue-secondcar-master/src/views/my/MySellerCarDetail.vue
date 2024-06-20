<template>
    <div>
        <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="汽车详细信息">
            <el-row :gutter="4">
                <el-form ref="addCarFrom" :model="addCarFrom" :rules="rules" size="medium" label-width="100px"
                         label-position="left">
                    <el-col :span="16">
                        <el-form-item label="汽车标题" prop="carTitle">
                            <el-input v-model="addCarFrom.carTitle" type="textarea"
                                      placeholder="可以按品牌、车系、配置等特点起标题，使汽车更容易被检索到" :maxlength="60" show-word-limit
                                      :autosize="{minRows: 1, maxRows: 4}" :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="汽车品牌" prop="carBrand">
                                    <el-input v-model="addCarFrom.carBrand" placeholder="请输入汽车品牌" clearable
                                              :style="{width: '100%'}"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车车系" prop="carSeries">
                                    <el-input v-model="addCarFrom.carSeries" placeholder="请选择汽车车系" clearable
                                              :style="{width: '100%'}"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车车型" prop="carType">
                                    <el-select v-model="addCarFrom.carType" placeholder="请选择汽车车型" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carTypeOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="行驶里程" prop="carCarMileage">
                                    <el-input v-model="addCarFrom.carCarMileage" placeholder="行驶里程" clearable
                                              :style="{width: '100%'}">
                                        <template slot="append">公里</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="价格" prop="carPrice">
                                    <el-input v-model="addCarFrom.carPrice" placeholder="请输入价格" clearable
                                              :style="{width: '100%'}">
                                        <template slot="append">元</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="使用年限" prop="carUserTime">
                                    <el-input v-model="addCarFrom.carUserTime" placeholder="使用年限" clearable
                                              :style="{width: '100%'}">
                                        <template slot="append">年</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车状态" prop="carStatus" required>
                                    <el-switch v-model="addCarFrom.carStatus" active-text="已上架" inactive-text="已下架"
                                               active-color="#2AE027" :active-value='1' :inactive-value='0'></el-switch>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车排量" prop="carDisplacement">
                                    <el-input v-model="addCarFrom.carDisplacement" placeholder="汽车排量" clearable
                                              :style="{width: '100%'}">
                                        <template slot="append">L</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="过户次数" prop="transferCount">
                                    <el-input v-model="addCarFrom.transferCount" placeholder="请输入过户次数" clearable
                                              :style="{width: '100%'}"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车颜色" prop="carColor">
                                    <el-input v-model="addCarFrom.carColor" placeholder="请输入汽车颜色" clearable
                                              :style="{width: '100%'}"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="汽车配置" prop="field139">
                                    <el-input v-model="addCarFrom.field139" placeholder="天窗、GPS等" clearable
                                              :style="{width: '100%'}"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="生产日期" prop="produceTime">
                                    <el-date-picker v-model="addCarFrom.produceTime" format="yyyy-MM-dd"
                                                    value-format="yyyy-MM-dd" :style="{width: '100%'}"
                                                    placeholder="汽车生产日期" clearable>
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="座位数" prop="carSeat">
                                    <el-select v-model="addCarFrom.carSeat" placeholder="请选择座位数" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carSeatOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="变数箱类型" prop="carGearBox">
                                    <el-select v-model="addCarFrom.carGearBox" placeholder="请选择变数箱类型" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carGearBoxOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="燃料类型" prop="carFuelType">
                                    <el-select v-model="addCarFrom.carFuelType" placeholder="请选择燃料类型" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carFuelTypeOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="国别" prop="carRefionId">
                                    <el-select v-model="addCarFrom.carRefionId" placeholder="请选择国别" filterable clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carRefionIdOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="前轮胎尺寸" prop="carFrontTyre">
                                    <el-select v-model="addCarFrom.carFrontTyre" placeholder="请选择前轮胎尺寸" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in carFrontTyreOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="后轮胎尺寸" prop="rear_tyre">
                                    <el-select v-model="addCarFrom.rear_tyre" placeholder="请选择后轮胎尺寸" clearable
                                               :style="{width: '100%'}">
                                        <el-option v-for="(item, index) in rear_tyreOptions" :key="index"
                                                   :label="item.label"
                                                   :value="item.value" :disabled="item.disabled"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="汽车其他描述" prop="carDescribe">
                            <el-input v-model="addCarFrom.carDescribe" type="textarea"
                                      placeholder="请输入汽车其他描述，如问题、汽车维修记录、事故记录等其他信息" :maxlength="150" show-word-limit
                                      :autosize="{minRows: 1, maxRows: 4}" :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>


                    <el-col :span="12">
                        <el-form-item label="汽车主图" prop="carPicture" required>
                            <el-upload ref="carPicture" :file-list="carPicturefileList" :action="carPictureAction"
                                       :auto-upload="false" :before-upload="carPictureBeforeUpload" list-type="picture"
                                       name="carPicture">
                                <el-button size="small" type="primary" icon="el-icon-upload">选择上传的图片</el-button>
                                <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="汽车轮播图" prop="carousel" required>
                            <el-upload ref="carousel" :file-list="carouselfileList" :action="carouselAction"
                                       :auto-upload="false" multiple :before-upload="carouselBeforeUpload"
                                       list-type="picture-card"
                                       accept="image/*" name="fileListCarousel">
                                <i class="el-icon-plus"></i>
                                <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="汽车内饰图" prop="carInside" required>
                            <el-upload ref="carInside" :file-list="carInsidefileList" :action="carInsideAction"
                                       :auto-upload="false" multiple :before-upload="carInsideBeforeUpload"
                                       list-type="picture-card"
                                       accept="image/*" name="fileListInside">
                                <i class="el-icon-plus"></i>
                                <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="汽车外饰图" prop="carOutside" required>
                            <el-upload ref="carOutside" :file-list="carOutsidefileList" :action="carOutsideAction"
                                       :auto-upload="false" multiple :before-upload="carOutsideBeforeUpload"
                                       list-type="picture-card"
                                       accept="image/*" name="fileLisOutside">
                                <i class="el-icon-plus"></i>
                                <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="汽车其他图片" prop="carOther" required>
                            <el-upload ref="carOther" :file-list="carOtherfileList" :action="carOtherAction"
                                       :auto-upload="false" multiple :before-upload="carOtherBeforeUpload"
                                       list-type="picture-card"
                                       accept="image/*" name="fileListOnOther">
                                <i class="el-icon-plus"></i>
                                <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="handelConfirm">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>


<script>
    export default {
        name: "MySellerCarDetail",
        inheritAttrs: false,
        components: {},
        props: [],
        data() {
            return {
                addCarFrom: {
                    carTitle: undefined,
                    carBrand: undefined,
                    carSeries: null,
                    carType: undefined,
                    carCarMileage: undefined,
                    carPrice: undefined,
                    carUserTime: undefined,
                    carStatus: 0,
                    carDisplacement: undefined,
                    transferCount: undefined,
                    carColor: undefined,
                    field139: undefined,
                    produceTime: "",
                    carSeat: undefined,
                    carGearBox: undefined,
                    carFuelType: undefined,
                    carRefionId: undefined,
                    carFrontTyre: undefined,
                    rear_tyre: undefined,
                    carDescribe: undefined,
                    carPicture: null,
                    carousel: null,
                    carInside: null,
                    carOutside: null,
                    carOther: null,
                },
                rules: {
                    carTitle: [{
                        required: true,
                        message: '可以按品牌、车系、配置等特点起标题，使汽车更容易被检索到',
                        trigger: 'blur'
                    }],
                    carBrand: [{
                        required: true,
                        message: '请输入汽车品牌',
                        trigger: 'blur'
                    }],
                    carSeries: [{
                        required: true,
                        message: '请选择汽车车系',
                        trigger: 'blur'
                    }],
                    carType: [{
                        required: true,
                        message: '请选择汽车车型',
                        trigger: 'change'
                    }],
                    carCarMileage: [{
                        required: true,
                        message: '行驶里程',
                        trigger: 'blur'
                    }],
                    carPrice: [{
                        required: true,
                        message: '请输入价格',
                        trigger: 'blur'
                    }],
                    carUserTime: [{
                        required: true,
                        message: '使用年限',
                        trigger: 'blur'
                    }],
                    carDisplacement: [{
                        required: true,
                        message: '汽车排量',
                        trigger: 'blur'
                    }],
                    transferCount: [{
                        required: true,
                        message: '请输入过户次数',
                        trigger: 'blur'
                    }],
                    carColor: [{
                        required: true,
                        message: '请输入汽车颜色',
                        trigger: 'blur'
                    }],
                    field139: [{
                        required: true,
                        message: '天窗、GPS等',
                        trigger: 'blur'
                    }],
                    produceTime: [{
                        required: true,
                        message: '汽车生产日期',
                        trigger: 'change'
                    }],
                    carSeat: [{
                        required: true,
                        message: '请选择座位数',
                        trigger: 'change'
                    }],
                    carGearBox: [{
                        required: true,
                        message: '请选择变数箱类型',
                        trigger: 'change'
                    }],
                    carFuelType: [{
                        required: true,
                        message: '请选择燃料类型',
                        trigger: 'change'
                    }],
                    carRefionId: [{
                        required: true,
                        message: '请选择国别',
                        trigger: 'change'
                    }],
                    carFrontTyre: [{
                        required: true,
                        message: '请选择前轮胎尺寸',
                        trigger: 'change'
                    }],
                    rear_tyre: [{
                        required: true,
                        message: '请选择后轮胎尺寸',
                        trigger: 'change'
                    }],
                    carDescribe: [{
                        required: true,
                        message: '请输入汽车其他描述，如问题、汽车维修记录、事故记录等其他信息',
                        trigger: 'blur'
                    }],
                },
                carPictureAction: 'http://localhost:8090/carInfo/addCarInfo',
                carPicturefileList: [],
                carouselAction: 'http://localhost:8090/carInfo/addCarInfo',
                carouselfileList: [],
                carInsideAction: 'http://localhost:8090/carInfo/addCarInfo',
                carInsidefileList: [],
                carOutsideAction: 'http://localhost:8090/carInfo/addCarInfo',
                carOutsidefileList: [],
                carOtherAction: 'http://localhost:8090/carInfo/addCarInfo',
                carOtherfileList: [],
                carTypeOptions: [
                    {
                    "label": "两厢轿车",
                    "value": "两厢轿车"
                }, {
                    "label": "三厢轿车",
                    "value": "三厢轿车"
                }, {
                    "label": "跑车",
                    "value": "跑车"
                }, {
                    "label": "SUV",
                    "value": "SUV"
                }, {
                    "label": "MPV",
                    "value": "MPV"
                }, {
                    "label": "面包车",
                    "value": "面包车"
                }, {
                    "label": "皮卡",
                    "value": "皮卡"
                }, {
                    "label": "其他",
                    "value": "其他"
                }],
                carSeatOptions: [
                    {
                    "label": "1座",
                    "value": 1
                }, {
                    "label": "2座",
                    "value": 2
                }, {
                    "label": "4座",
                    "value": 4
                }, {
                    "label": "7座",
                    "value": "7座"
                }, {
                    "label": "其他",
                    "value": "其他"
                }],
                carGearBoxOptions: [
                    {
                    "label": "手动",
                    "value": "手动"
                }, {
                    "label": "自动",
                    "value": "自动"
                }, {
                    "label": "手自一体",
                    "value": "手自一体"
                }],
                carFuelTypeOptions: [
                    {
                    "label": "柴油",
                    "value": "柴油"
                }, {
                    "label": "机油",
                    "value": "机油"
                }, {
                    "label": "电力",
                    "value": "电力"
                }, {
                    "label": "其他",
                    "value": "其他"
                }],
                carRefionIdOptions: [
                    {
                    "label": "国产",
                    "value": "国产"
                }, {
                    "label": "德系",
                    "value": "德系"
                }, {
                    "label": "日系",
                    "value": "日系"
                }, {
                    "label": "美系",
                    "value": "美系"
                }, {
                    "label": "法系",
                    "value": "法系"
                }, {
                    "label": "韩系",
                    "value": "韩系"
                }, {
                    "label": "其他",
                    "value": "其他"
                }],
                carFrontTyreOptions: [
                    {

                    "label": "1",
                    "value": 1
                }, {
                    "label": "2",
                    "value": 2
                }, {
                    "label": "3",
                    "value": 3
                }],
                rear_tyreOptions: [
                    {
                    "label": "1",
                    "value": 1
                }, {
                    "label": "2",
                    "value": 2
                }, {
                    "label": "3",
                    "value": 3
                }],
            }
        },
        methods: {
            onOpen() {
            },
            onClose() {
                this.$refs['addCarFrom'].resetFields()
            },
            close() {
                this.$emit('update:visible', false)
            },
            handelConfirm() {
                this.$refs['addCarFrom'].validate(valid => {
                    if (!valid) return
                    this.close()
                })
            },
            carPictureBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                return isRightSize
            },

            submitUpload(name) {
                this.$refs[name].submit()
            },

            carouselBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                let isAccept = new RegExp('image/*').test(file.type)
                if (!isAccept) {
                    this.$message.error('应该选择image/*类型的文件')
                }
                return isRightSize && isAccept
            },

            carInsideBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                let isAccept = new RegExp('image/*').test(file.type)
                if (!isAccept) {
                    this.$message.error('应该选择image/*类型的文件')
                }
                return isRightSize && isAccept
            },

            carOutsideBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                let isAccept = new RegExp('image/*').test(file.type)
                if (!isAccept) {
                    this.$message.error('应该选择image/*类型的文件')
                }
                return isRightSize && isAccept
            },

            carOtherBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                let isAccept = new RegExp('image/*').test(file.type)
                if (!isAccept) {
                    this.$message.error('应该选择image/*类型的文件')
                }
                return isRightSize && isAccept
            },

        }
    }
</script>

<style>
    .el-upload__tip {
        line-height: 1.2;
    }

</style>
