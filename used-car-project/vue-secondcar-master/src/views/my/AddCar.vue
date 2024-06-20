<template>
    <div>
        <el-row>
            <!--步骤条-->
            <el-steps :active="active" finish-status="success">
                <el-step title="1 填写汽车信息"></el-step>
                <el-step title="2 上传汽车主图"></el-step>
                <el-step title="3 上传汽车的内饰图"></el-step>
                <el-step title="4 上传汽车的外饰图"></el-step>
                <el-step title="5 上传汽车的其他图"></el-step>
                <el-step title="6 上传汽车的轮播图"></el-step>
            </el-steps>

            <!--分割线-->
            <el-divider/>

            <!---->
            <!--汽车配置信息  :rules="rules" -->
            <el-form v-show="active === 0" ref="addCarFrom" :model="addCarFrom" size="medium" label-width="100px"
                     label-position="left">
                <!--汽车标题-->
                <el-col :span="16">
                    <el-form-item label="汽车标题" prop="carTitle">
                        <el-input v-model="addCarFrom.carTitle" type="textarea"
                                  placeholder="可以按品牌、车系、配置等特点起标题，使汽车更容易被检索到" :maxlength="60" show-word-limit
                                  :autosize="{minRows: 1, maxRows: 4}" :style="{width: '100%'}"></el-input>
                    </el-form-item>
                </el-col>
                <!--汽车主要参数-->
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
                            <el-form-item label="汽车配置" prop="carConfiguration">
                                <el-input v-model="addCarFrom.carConfiguration" placeholder="天窗、GPS等" clearable
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
                                <!-- <el-select v-model="addCarFrom.carFrontTyre" placeholder="请选择前轮胎尺寸" clearable
                                            :style="{width: '100%'}">
                                     <el-option v-for="(item, index) in carFrontTyreOptions" :key="index"
                                                :label="item.label"
                                                :value="item.value" :disabled="item.disabled"></el-option>
                                 </el-select>-->

                                <el-input v-model="addCarFrom.carConfiguration" placeholder="天窗、GPS等" clearable
                                          :style="{width: '100%'}"></el-input>

                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="后轮胎尺寸" prop="rearTyre">
                                <el-select v-model="addCarFrom.rearTyre" placeholder="请选择后轮胎尺寸" clearable
                                           :style="{width: '100%'}">
                                    <el-option v-for="(item, index) in rear_tyreOptions" :key="index"
                                               :label="item.label"
                                               :value="item.value" :disabled="item.disabled"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <!--其他描述-->
                <el-col :span="24">
                    <el-form-item label="汽车其他描述" prop="carDescribe">
                        <el-input v-model="addCarFrom.carDescribe" type="textarea"
                                  placeholder="请输入汽车其他描述，如问题、汽车维修记录、事故记录等其他信息" :maxlength="150" show-word-limit
                                  :autosize="{minRows: 1, maxRows: 4}" :style="{width: '100%'}"></el-input>
                    </el-form-item>
                </el-col>

            </el-form>

            <!--汽车主图-->
            <el-col ref="carPicture" :prop="carPictureFileList" v-show="active === 1" :span="12">
                <el-upload ref="carPicture"
                           :action="carPictureAction"
                           :auto-upload="false"
                           :file-list="carPictureFileList"
                           :on-change="carPictureFileChange"
                           :on-remove="carPictureFileRemove"
                           :before-upload="carPictureBeforeUpload"
                           list-type="picture"
                           :data="carPictureFileList"
                           name="carPicture">
                    添加汽车主图：
                    <el-button size="small" type="primary" icon="el-icon-upload">选择上传的图片</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的文件</div>
                </el-upload>

                <!--主图上传方式2
                 <el-upload
                                        action="#"
                                        list-type="picture-card"
                                        :auto-upload="false">
                                    <i slot="default" class="el-icon-plus"></i>
                                    <div slot="file" slot-scope="{file}">
                                        <img class="el-upload-list__item-thumbnail"
                                             :src="file.url" alt="">
                                        <span class="el-upload-list__item-actions">
                                                 <span class="el-upload-list__item-preview"
                                                       @click="handlePictureCardPreview(file)">
                                                    <i class="el-icon-zoom-in"></i>
                                                </span>
                                                <span
                                                        v-if="!disabled"
                                                        class="el-upload-list__item-delete"
                                                        @click="handleDownload(file)"
                                                >
                                                    <i class="el-icon-download"></i>
                                                </span>
                                                <span
                                                        v-if="!disabled"
                                                        class="el-upload-list__item-delete"
                                                        @click="handleRemove(file)"
                                                >
                                                <i class="el-icon-delete"></i>
                                                </span>
                                            </span>
                                    </div>
                                </el-upload>
                                <el-dialog :visible.sync="dialogVisible">
                                    <img width="100%" :src="dialogImageUrl" alt="">
                                </el-dialog>-->

            </el-col>


            <!--汽车内饰图上传-->
            <el-col v-show="active === 2" :span="24">
                <!--<el-form-item label="汽车内饰图" prop="carInside"></el-form-item>-->
                <el-upload ref="carInside" :file-list="carInsideFileList" :action="carInsideAction"
                           v-model="carInsideFileList"
                           :auto-upload="false" multiple :before-upload="carInsideBeforeUpload"
                           list-type="picture-card"
                           :on-change="carInsideFileChange"
                           :on-remove="carInsideFileRemove"
                           :data="carInsideFileList"
                           accept="image/*" name="fileListInside">
                    <i class="el-icon-plus">点击添加汽车内饰图</i>

                    <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                </el-upload>

            </el-col>

            <!--汽车外饰图上传-->
            <el-col v-show="active === 3" :span="24">
                <!--<el-form-item label="汽车外饰图" prop="carOutside"> </el-form-item>-->
                <el-upload ref="carOutside" :file-list="carOutsideFileList" :action="carOutsideAction"
                           v-model="carOutsideFileList"
                           :auto-upload="false" multiple :before-upload="carOutsideBeforeUpload"
                           list-type="picture-card"
                           :data="carOutsideFileList"
                           :on-change="carOutsideFileChange"
                           :on-remove="carOutsideFileRemove"
                           accept="image/*" name="fileLisOutside">
                    <i class="el-icon-plus">点击添加汽车外饰图</i>

                    <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                </el-upload>
            </el-col>

            <!--汽车其他图片上传-->
            <el-col v-show="active === 4" :span="24">
                <el-upload ref="carOther" :file-list="carOtherFileList" :action="carOtherAction"
                           v-model="carOtherFileList"
                           :auto-upload="false" multiple :before-upload="carOtherBeforeUpload"
                           list-type="picture-card"
                           :data="carOtherFileList"
                           :on-change="carOtherFileChange"
                           :on-remove="carOtherFileRemove"
                           accept="image/*" name="fileListOnOther">
                    <i class="el-icon-plus">点击添加汽车其他图片</i>
                    <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                </el-upload>
            </el-col>

            <!--汽车轮播图上传-->
            <el-col v-show="active === 5" :span="24">
                <el-upload ref="carousel" :file-list="carouselFileList" :action="carouselAction"
                           v-model="carouselFileList"
                           :auto-upload="false" multiple :before-upload="carouselBeforeUpload"
                           list-type="picture-card"
                           :data="carouselFileList"
                           :on-change="carouselFileChange"
                           :on-remove="carouselFileRemove"
                           accept="image/*" name="fileListCarousel">
                    <i class="el-icon-plus">点击添加汽车轮播图</i>
                    <div slot="tip" class="el-upload__tip">只能上传不超过 10MB 的image/*文件</div>
                </el-upload>
            </el-col>

        </el-row>

        <div slot="footer" class="btn-group">
            <el-button @click="close">取消</el-button>
            <el-button v-if="this.active !== 0" type="primary" @click="pre">上一步</el-button>
            <el-button v-if="this.isUpdate" type="primary" @click="updateCarById">修改，后面信息不修改</el-button>
            <el-button v-if="!this.isUpdate" type="primary" @click="submitForm('addCarFrom')">添加，以后再上传图片</el-button>
            <el-button v-if="this.active !== 5" type="primary" @click="next">下一步</el-button>
        </div>
    </div>
</template>

<script>
    import {getRequest} from "../../my-axios";
    import bus from "../../utils/bus";

    export default {
        name: "AddCar",
        props: ["isUpdate"],
        data() {
            return {
                active: 0,
                // btnTitle: '提交并进行下一步',

                // carPictureInfo: {
                //     carId: null,// 后端返回的carId,
                //     carPictureLocation: null,
                // },

                /* 主图上传方式2
                dialogImageUrl: '',
                dialogVisible: false,
                disabled: false,
                */

                addCarFrom: {
                    carTitle: "",
                    carBrand: '',
                    carSeries: '',
                    carType: '',
                    carCarMileage: 0,
                    carPrice: 0.0,
                    carUserTime: 0,
                    carStatus: 0,
                    carDisplacement: 0,
                    transferCount: 0,
                    carColor: '',
                    carConfiguration: '',
                    produceTime: "",
                    carSeat: 0,
                    carGearBox: '',
                    carFuelType: '',
                    carRefionId: '',
                    carFrontTyre: 0,
                    rearTyre: 0,
                    carDescribe: '',

                    // carPicture: '',// 主图
                    // carousel: '',// 轮播图
                    // carInside: '',// 内饰
                    // carOutside: '',// 外饰
                    // carOther: '',// 其他
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
                carPictureAction: 'http://localhost:8090/carInfo/addCarInfoPictures/0',
                carPictureFileList: [],// 主图 内饰 外饰 其他 轮播图


                carInsideAction: 'http://localhost:8090/carInfo/addCarInfoPictures/1',
                carInsideFileList: [],// 内饰

                carOutsideAction: 'http://localhost:8090/carInfo/addCarInfoPictures/2',
                carOutsideFileList: [], // 外饰

                carOtherAction: 'http://localhost:8090/carInfo/addCarInfoPictures/3',
                carOtherFileList: [],// 其他


                carouselAction: 'http://localhost:8090/carInfo/addCarInfoPictures/4',
                carouselFileList: [],// 轮播图

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
                        "value": 7
                    }, {
                        "label": "其他",
                        "value": 0
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
        created() {
            // 创建的时候先判断是否是修改汽车信息，因为这个页面是新增和修改共用的。
            this.isUpdateCar();
        },

        methods: {

            /*主图上传方式2
                        handleRemove(file) {
                            console.log(file);
                        },
                        handlePictureCardPreview(file) {
                            this.dialogImageUrl = file.url;
                            this.dialogVisible = true;
                        },
                        handleDownload(file) {
                            console.log(file);
                        },*/

            // 修改汽车信息
            async updateCarById() {
                let {formData, config} = this.addFormData();
                // 如果想要做到方法调用和执行是同步的，可以使用async和await修饰符。
                // 1. 先等待这个接口调用结束后才调用下一接口
                const resp = await this.$axios.put('/carInfo/updateCarInfoById/' + this.isUpdate, this.addCarFrom);
                console.log("修改汽车信息", resp);
                console.log(resp.status === 200);
                if (resp.status === 200) {// 2033是后端的ResultCode设置的，代表修改汽车信息成功
                    console.log("修改汽车图片");
                    this.addCarFrom = resp.data.carInfo;
                    this.carId = this.isUpdate;
                    // 2. 第二部再调用这个接口
                    await this.submitUploadPicture(); // 先等待
                    // 新增成功后关闭弹窗
                    this.$emit('addVisible', false);
                }
            },

            // 父组件MySellerCarInfo传来的修改汽车的carID，
            async isUpdateCar() {
                await this.getUpdateCarId();
                console.log(this.isUpdate);
                // 传过来的isUpdate不为空，则回显数据，
                if (this.isUpdate) {
                    const {data: resp} = await getRequest("/carInfo/getCarDetail/" + this.isUpdate);
                    console.log("修改汽车的resp ", resp);
                    // 汽车属性
                    this.addCarFrom = resp.data.carInfo;
                    // 汽车主图
                    // this.carPictureFileList = resp.data.carInfo.carPicture;
                    var carPicturesMap = resp.data.carPicturesMap;
                    // console.log("carPictureFileList[0] ", this.carPictureFileList);
                    // 图片
                    // console.log("resp.data.carPicturesMap ", resp.data.carPicturesMap);
                    for (let key in carPicturesMap) {
                        // 图片位置是1 即内部
                        if (key == 1) {
                            var list = carPicturesMap[key];
                            console.log("list ", list);
                            for (let i = 0; i < list.length; i++) {
                                this.carInsideFileList.push(list[i]);
                            }
                            // this.carInsideFileList = carPicturesMap[key];
                            console.log("carInsideFileList  ", this.carInsideFileList);
                        }
                        // 图片位置是0 即主图
                        else if (key == 0) {
                            this.carPictureFileList = carPicturesMap[key];
                            console.log("carPictureFileList  ", this.carPictureFileList);
                        }
                        // 图片位置是2 即外部
                        else if (key == 2) {
                            this.carOutsideFileList = carPicturesMap[key];
                            console.log("carOutsideFileList  ", this.carOutsideFileList);
                        }
                        // 图片位置是3 即其他
                        else if (key == 3) {
                            this.carOtherFileList = carPicturesMap[key];
                            console.log("carOtherFileList  ", this.carOtherFileList);
                        }
                        // 图片位置是4 即轮播图
                        else if (key == 4) {
                            this.carouselFileList = carPicturesMap[key];
                            console.log("carouselFileList  ", this.carouselFileList);
                        }
                    }
                }
            },

            // 获取carId
            getUpdateCarId() {
                this.isUpdate;
            },

            // 上一步
            pre() {
                // console.log("执行上一步之前的active = ", this.active);
                this.active--;
                // console.log("执行上一步之后的active = ", this.active);
            },

            // 提交并进入下一步,
            next() {
                // 进入下一步
                this.active++;
                // console.log("点击下一步后的active = ", this.active);
            },

            onClose() {
                this.$refs['addCarFrom'].resetFields()
            },

            // 关闭弹窗
            close() {
                this.active = 0;
                this.$emit('update:visible', false);

                // 清空 主图 内饰 外饰 其他 轮播图
                this.carPictureFileList =[];
                this.carInsideFileList =[];
                this.carOutsideFileList =[];
                this.carOtherFileList =[];
                this.carouselFileList =[];

                // 关闭弹窗
                this.$emit('addVisible', false)
                // 关闭后可以刷新页面
            },


            //============以下为文件变动和删除时的方法===============
            // 1. 主图
            // 主图检测文件变动获取文件
            carPictureFileChange(file, fileList) {
                this.carPictureFileList = fileList;
            },
            // 主图检测文件删除
            carPictureFileRemove(file, fileList) {
                this.carPictureFileList = fileList;
            },
            // 2. 内饰图
            // 检测文件变动获取文件
            carInsideFileChange(file, fileList) {
                this.carInsideFileList = fileList;
            },
            // 检测文件删除
            carInsideFileRemove(file, fileList) {
                this.carInsideFileList = fileList;
            },

            // 3. 外饰图
            // 检测文件变动获取文件
            carOutsideFileChange(file, fileList) {
                this.carOutsideFileList = fileList;
            },
            // 检测文件删除
            carOutsideFileRemove(file, fileList) {
                this.carOutsideFileList = fileList;
            },

            // 4. 其他图
            // 检测文件变动获取文件
            carOtherFileChange(file, fileList) {
                this.carOtherFileList = fileList;
            },
            // 检测文件删除
            carOtherFileRemove(file, fileList) {
                this.carOtherFileList = fileList;
            },

            // 5. 轮播图图
            // 检测文件变动获取文件
            carouselFileChange(file, fileList) {
                this.carouselFileList = fileList;
            },
            // 检测文件删除
            carouselFileRemove(file, fileList) {
                this.carouselFileList = fileList;
            },
            //============以上为文件变动和删除时的方法===============


            // 上传汽车基本信息
            async submitForm(formName) {
                // 汽车信息提交
                // 提交汽车参数配置信息表单
                console.log("提交汽车参数配置信息表单");
                await this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        let {formData, config} = this.addFormData();
                        // 如果想要做到方法调用和执行是同步的，可以使用async和await修饰符。
                        // 1. 先等待这个接口调用结束后才调用下一接口
                        const {data: resp} = await this.$axios.post('/carInfo/addCarInfo', this.addCarFrom);
                        if (resp.code === 200) {
                            console.log("新增汽车resp：", resp);
                            this.carId = resp.data;
                            console.log("新增汽车后的carId：", this.carId);
                            // 2. 第二部再调用这个接口
                            await this.submitUploadPicture(); // 先等待
                            // 新增成功后关闭弹窗
                            this.$emit('addVisible', false);
                        }
                    }
                });
            },

            //  辅助函数 。将文件封装进FormData
            addFormData() {
                let formData = new FormData();
                // 主图
                this.carPictureFileList.forEach(file => {
                    if (file !== null)
                        formData.append('file', file.raw);
                });
                // 内饰
                this.carInsideFileList.forEach(file => {
                    if (file !== null)
                        formData.append('carInsideFileList', file.raw);
                });
                // 外饰
                this.carOutsideFileList.forEach(file => {
                    if (file !== null)
                        formData.append('carOutsideFileList', file.raw);
                })
                // 其他
                this.carOtherFileList.forEach(file => {
                    if (file !== null)
                        formData.append('carOtherFileList', file.raw);
                });
                // 轮播图

                this.carouselFileList.forEach(file => {
                    if (file !== null)
                        formData.append('carouselFileList', file.raw);
                });

                formData.append("carId", this.carId);//附带数据
                // console.log(this.$store.getters.getUser.userId);
                formData.append("userId", this.$store.getters.getUser.userId);//附带数据
                // 文件头
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                };
                /* 打印formData
                console.log("formData file", formData.get("file"));
                 console.log("formData carInsideFileList", formData.get("carInsideFileList"));
                 console.log("formData carOutsideFileList", formData.get("carOutsideFileList"));
                 console.log("formData carOtherFileList", formData.get("carOtherFileList"));
                 console.log("formData carouselFileList", formData.get("carouselFileList"));*/
                return {formData, config};
            },

            //手动上传图片
            submitUploadPicture() {
                console.log("this.carPictureFileList", this.carPicturefileList);
                let {formData, config} = this.addFormData();
                // 调用接口上
                // 传
                // const {data: resp} = await this.$axios.post('/carInfo/addCarInfoPictures/' + location, formData, config)
                const {data: resp} = this.$axios.post('/carInfo/addCarInfoPictures', formData, config)
                // 清空数据
                // this.carPicturefileList = [];
            },
            /* // 新增汽车，包括上传基本信息和上传全部图片
             addCar() {
                 // 1. 先上传基本信息，等待后端返回carID后再上传图片
                 this.submitForm('addCarFrom');
                 console.log("已经上传汽车基本信息 carId = ", this.carId);
                 // 2. 上传图片
                 // this.submitUploadPicture();
             },*/

            // ===========上传图片前的检验================
            carPictureBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10;
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                return isRightSize
            },

            carouselBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 10;
                if (!isRightSize) {
                    this.$message.error('文件大小超过 10MB')
                }
                let isAccept = new RegExp('image/*').test(file.type);
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
    /*提交、上下一步、取消的按钮组的样式*/
    .btn-group {
        padding-top: 15px;
    }

    /*图片上传提示的样式*/
    .el-upload__tip {
        line-height: 1.2;
    }
</style>



