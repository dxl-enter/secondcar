<template>
    <div>
        <!--  我卖的车的信息。-->


        <!--新增汽车的弹出框-->
        <el-dialog title="填写汽车信息" :visible.sync="dialogAdd" width="70%">
            <!--<AddCarForm @addVisible = "closeAddCarDialog"></AddCarForm>-->
            <AddCar @addVisible="closeAddCarDialog"></AddCar>
        </el-dialog>

        <!--修改汽车的汽车的弹出框-->
        <el-dialog title="修改汽车信息" :visible.sync="dialogFormVisibleCar" width="70%">
            <!--<AddCarForm @addVisible = "closeAddCarDialog"></AddCarForm>-->
            <AddCar :is-update="updateCarId" @addVisible="closeAddCarDialog"></AddCar>
        </el-dialog>

        <el-row>
            <!--新增汽车-->
            <el-col :span="6" style=" padding: 5px;">
                <el-card class="car-all" :body-style="{ padding: '0px' }" shadow="hover">
                    <el-button class="car-add" @click="addCar">新增汽车</el-button>
                </el-card>
            </el-col>
        </el-row>
        <el-row>

            <!--汽车信息-->
            <el-col :span="6" style=" padding: 5px;" v-for="(car, index) in myCar" :key="index">
                <el-card class="car-all" :body-style="{ padding: '0px' }" shadow="hover">
                    <!--图片-->
                    <router-link :to="{name: 'CarDetail', params: {carId: car.carId}}">
                        <img :src="car.carPicture"
                             class="image" alt="汽车主图"/>
                    </router-link>
                    <div style="padding: 14px;">
                        <!--标题-->
                        <span class="carTitle">{{car.carTitle}}</span>
                        <el-row class="car-attr">
                            <el-col :span="4">
                                <el-row>使用年限</el-row>
                                <el-row>{{car.carUserTime}}</el-row>
                            </el-col>
                            <el-col :span="10">
                                <el-row>里程数(万公里)</el-row>
                                <el-row>{{car.carCarMileage}}</el-row>
                            </el-col>
                            <el-col :span="4">
                                <el-row>排量</el-row>
                                <el-row>{{car.carDisplacement}}</el-row>
                            </el-col>
                            <el-col :span="6">
                                <el-row>变速箱</el-row>
                                <el-row>{{car.carGearBox}}</el-row>
                            </el-col>
                        </el-row>
                        <!--价格-->
                        <div class="car-price">
                            <label>价格：</label>
                            <span>{{car.carPrice}} 元</span>
                        </div>
                        <div class="btn-up-or-del">
                            <el-button class="btn-up" type="primary" @click="updateCar(car.carId)">查看/修改</el-button>

                            <!--删除汽车-->
                            <el-popconfirm
                                    confirm-button-text='删除'
                                    @confirm="deleteCarById(car.carId)"
                                    confirm-button-type="text"

                                    cancel-button-text='取消'
                                    cancel-button-type="danger"

                                    icon="el-icon-info"
                                    icon-color="red"
                                    title="您确定删除该汽车的全部信息吗？"
                            >
                                <el-button slot="reference" type="danger">删除</el-button>
                            </el-popconfirm>

                        </div>
                    </div>
                </el-card>
            </el-col>


        </el-row>

        <!--分页-->
        <el-pagination class="m-page"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currPage"
                       :page-sizes="[5, 10, 15, 20]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalCount">
        </el-pagination>
    </div>
</template>

<script>
import { deleteRequest } from "../../my-axios";
import qs from "qs";
import AddCar from "./AddCar";

export default {
    name: "MySellerCarInfo",
    data () {
        return {
            //=======分页属性=======
            currPage: 1,// 当前页
            pageSize: 5,// 每页显示几条
            totalPage: 1,// 总页数
            totalCount: 5,//总记录数
            //current size total
            //=======分页属性=======

                // ===== 以下我的汽车信息 ========
                myCar: [],

                dialogAdd: false,

                dialogFormVisibleCar: false,// 表单弹出框
                formLabelWidth: '120px',// 表单大小
                // ===== 以上我的汽车信息 ========

                // 修改的汽车id,用来传给子组件
                updateCarId: null,

            };
        },

        created() {
            this.page();
        },

        methods: {

            // 修改汽车
            updateCar(carId) {
                this.updateCarId = carId;
                console.log(this.updateCarId, carId);
                this.dialogFormVisibleCar = true;
            },


            // 删除汽车
            deleteCarById(carId) {
                console.log("点击删除");
                deleteRequest('/yueChi/sysUser/deleteMyCar/' + carId).then(resp => {
                    console.log("删除汽车 ", carId, "后的resp ", resp);
                }).catch(error => {
                    this.$message({
                        showClose: true,
                        type: 'error',
                        message: '删除汽车失败、请联系管理员',
                        duration: 1000
                    })
                });
            },


            // 子组件AddCarForm传来的，用来关闭对话框的，
            closeAddCarDialog(value) {
                this.dialogAdd = value;
                this.dialogFormVisibleCar = value;
            },

            // 新增汽车
            addCar() {
                this.dialogAdd =  true;
                // this.dialogFormVisibleCar = true;
            },


            // 分页参数
            handleSizeChange(pageSize) {
                console.log(`每页 ${pageSize} 条`);
                this.pageSize = pageSize;
                this.page(this.currPage);
            },

            handleCurrentChange(currPage) {
                console.log(`当前页: ${currPage}`);
                this.currPage = currPage;
                this.page(this.currPage);
            },

            page() {
                this.$axios({
                    url: "/yueChi/sysUser/findMySellCarSomeInfo",
                    method: "get",
                    params: {
                        currPage: this.currPage,
                        pageSize: this.pageSize
                    },
                    paramsSerializer: function (params) {
                        return qs.stringify(params, {arrayFormat: "indices"});
                    },
                }).then(resp => {
                    console.log("分页查询后 第 ", this.currPage, " 页。", "其相应结果为：", resp);
                    this.myCar = resp.data.data.carInfos;
                    // 当前页
                    this.currPage = resp.data.data.currPage;
                    // 总页数
                    this.totalpage = resp.data.data.totalPage;
                    // 一页显示的数据
                    this.pageSize = resp.data.data.pageSize;
                    // 总记录数
                    this.totalCount = resp.data.data.totalCount;

                    console.log("当前页：", this.currPage, "总页码", this.totalpage)
                });

            },
        },
        computed: {},
        components: {AddCar},
    }
</script>

<style scoped>

    /*修改或者删除汽车按钮的样式*/
    .btn-up-or-del {
        padding-top: 5px;

    }

    .btn-up {
        margin-right: 10px;
    }

    /*新增汽车的样式*/
    .car-add {
        padding-top: 20%;
        padding-bottom: 20%;
        font-size: large;
        width: 100%;
        height: 100%;
    }

    /*汽车部分属性*/
    .car-attr {
        padding-top: 10px;
        font-size: x-small;
        padding-bottom: 10px;
    }

    .image {
        width: 100%;
        display: block;
    }

    .car-price:before,
    .car-price:after {
        display: table;
        content: "";
    }

    .car-price:after {
        clear: both
    }

    .car-all {
        font-weight: bold;
        border-radius: 17px;

        box-shadow: 10px 10px 20px #adc4c9,
        -10px -10px 20px #e9ffff;
    }

    /*新增汽车表单*/
    .add-car-from ui {
        list-style: none;

    }

    .add-car-from ui li {
        display: block;
        width: 30%;
        list-style: none;
    }


</style>
