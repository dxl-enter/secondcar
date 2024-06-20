<template>
    <div>
        <!--展示汽车-->
        <el-row>
            <!--
                :to  动态传参，跳到详情页，具体参数和路由里的一样
                    name: 跳到的页面的名字
                    params: 参数
            -->

            <!--:offset="index > 0 ? 6 : 0"-->
            <el-col :span="6" style=" padding: 5px;" v-for="(car, index) in dataFromBro" :key="index">
                <el-card class="car-all" :body-style="{ padding: '0px' }" shadow="hover">
                    <!--图片-->
                    <router-link :to="{name: 'CarDetail', params: {carId: car.carId}}">
                        <img :src="car.carPicture"
                             class="image" alt="汽车主图"/>
                    </router-link>
                    <div style="padding: 14px;">
                        <!--标题-->
                        <!--data.data.carInfoModel[0].carTitle-->
                        <span class="carTitle" v-html="car.carTitle">{{car.carTitle}}</span>

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


                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!--分页-->
        <el-pagination class="m-page"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currPage"
                       :page-sizes="[5, 10, 30, 50]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalCount">
        </el-pagination>

    </div>
</template>

<script>

    import qs from "qs";
    import bus from "../../utils/bus";
    import {getRequest} from "../../my-axios";

    export default {
        name: "CarListShow",
        data() {
            return {
                // carInfo信息
                dataFromBro: [],

                // 兄弟组件CarInfoScreen传来的选择参数
                selectObject: {},

                //分页属性
                currPage: 1,// 当前页
                pageSize: 10,// 每页显示几条
                totalPage: 10,// 总页数
                totalCount: 20,//总记录数

                myCollectCars: [], //用户收藏的车
                isCollectCar: false, // 收藏
            };
        },
        props: {

            // currPage: Number,
            // pageSize: Number,
            // totalPage: Number,

        },

        created() {
            const _this = this;

            // this.getBroCar();

            // this.getFromStore();

            // 从兄弟组件获取carInfoModel详细信息
            bus.$on("getBroCarInfoModel", getCarInfoModelFromScreen => {
                console.log("收到兄弟组件CarInfoScreen的数据 = ", getCarInfoModelFromScreen);
                this.dataFromBro = getCarInfoModelFromScreen;
                // 在刚开始刷新页面时通过兄弟组件的carInfo长度来赋值给总记录数
                this.totalCount = getCarInfoModelFromScreen.length;
                // console.log("getCarInfoModelFromScreen.length ", getCarInfoModelFromScreen.length);
            });

            bus.$on("getSelectObject", selectObject => {
                console.log("收到兄弟组件CarInfoScreen的选择的参数信息 = ", selectObject);
                this.selectObject = selectObject;
            });

            // // 获取用户收藏的车辆
            // this.getMyCollectCars();


            // 分页、默认第一页
            // this.page(1);
        },
        // 有修改DOM就用mounted没有就用created
        // created在渲染前调用，mounted在渲染后调用
        mounted() {

        },
        methods: {

            // 获取用户收藏的车
            getMyCollectCars() {
                getRequest("/yueChi/sysUser/getMyCollectCar").then(resp => {
                    console.log("用户收藏的车 ", resp);
                    this.myCollectCars = resp.data.data;
                })
            },

            // 收藏汽车
            changeCollect(carId) {
                console.log(carId);
                getRequest("/yueChi/sysUser/changeMyCollectCar/" + carId).then(resp => {
                    this.isCollectCar = resp.data.data;
                }).catch(error => {

                });
            },


            // 分页
            page(currentPage) {

                // 请求方式一，不携带选择的属性
                // this.$axios.get("/carInfo/searchListPage?currPage= " + currentPage).then(resp => {
                //     console.log("分页查询后 第 ",currentPage ," 页。","其相应结果为：",resp);
                //     // 具体数据
                //     this.dataFromBro = resp.data.data.carInfoModel;
                //     // 当前页
                //     this.currPage = resp.data.data.currPage;
                //     // 总页数
                //     this.totalPage = resp.data.data.totalPage;
                //     // 一页显示的数据
                //     this.pageSize = resp.data.data.pageSize;
                //
                //     // 总记录数 data.data.totalCount
                //
                //     console.log("当前页：",this.currPage, "总页码",this.totalpage)
                // });

                // 请求方式二，携带选择的属性
                // 已经有第几页这个属性，则删除带你原本的、再换成新的
                if (this.selectObject.hasOwnProperty("currPage")) {
                    this.$delete(this.selectObject, "currPage")
                }
                // 选择每页显示几条
                if (this.selectObject.hasOwnProperty("pageSize")) {
                    this.$delete(this.selectObject, "pageSize")
                }
                console.log("currentPage: ", currentPage);
                // 添加到请求参数的对象中
                // 加入分页参数：当前页
                this.$set(this.selectObject, "currPage", currentPage);
                // 加入分页参数：每页显示几条
                this.$set(this.selectObject, "pageSize", this.pageSize);
                console.log("分页请求参数", this.selectObject);


                this.$axios({
                    url: "/carInfo/searchListPage",
                    method: "get",
                    params: this.selectObject,
                    paramsSerializer: function (params) {
                        return qs.stringify(params, {arrayFormat: "indices"});
                    },
                }).then(resp => {
                    console.log("分页查询 第 ", currentPage, " 页后。", "其相应结果为：", resp);
                    // 具体数据
                    this.dataFromBro = resp.data.data.carInfoModel;
                    // 当前页
                    this.currPage = resp.data.data.currPage;
                    // 总页数
                    this.totalpage = resp.data.data.totalPage;
                    // 一页显示的数据
                    this.pageSize = resp.data.data.pageSize;
                    // 总记录数
                    this.totalCount = resp.data.data.totalCount;

                    console.log("当前页：", this.currPage, "总页码", this.totalpage, "总记录数", this.totalCount);
                });

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

            // getFromStore() {
            //     this.carInfoModel = this.$store.getters.getCarInfoModel();
            // }

        },
        computed: {
            // getBroCar() {
            //     bus.$on("getCarInfoModel", getCarInfoModelFromScreen => {
            //         console.log("收到兄弟组件CarInfoScreen的数据 = ", getCarInfoModelFromScreen);
            //         this.carInfoModel = getCarInfoModelFromScreen;
            //     })
            // }
        },
        components: {},
    }
</script>

<style scoped>

    /*汽车部分属性*/
    .car-attr {
        padding-top: 10px;
        font-size: x-small;
        padding-bottom: 10px;
    }


    /*汽车标题*/
    .carTitle {
        font-size: larger;
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

    /*价格*/
    .car-price span {
        color: #f95523;
    }

</style>
