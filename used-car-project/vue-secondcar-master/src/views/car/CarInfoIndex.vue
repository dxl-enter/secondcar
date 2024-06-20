<template>
    <div class="main_container">
        <el-container>
            <!--头部布局-->
            <Header/>

            <!--中间-->
            <el-main>

                <!--                &lt;!&ndash;面包屑&ndash;&gt;
                                <div class="breadcrumb">
                                    <el-breadcrumb separator-cl ass="el-icon-arrow-right">
                                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>活动管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>活动列表</el-breadcrumb-item>
                                        <el-breadcrumb-item>活动详情</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>-->

                <!--多条件搜索、过滤-->
                <!--<CarInfoScreen :car-agg-vo-list="carAggVoList"/>-->
                <CarInfoScreen :agg-vo="aggVo"/>

                <!--分割线-->
                <el-divider></el-divider>

                <!--测试的筛选组件-->
                <!--<TestScreen :car-agg-vo-list="carAggVoList"  :arr-title-list="arrTitleList"/>-->
                <!--<CarScreen  :get-list="carAggVoListList"/>-->

                <!--搜索总数据个数-->
                <!--汽车列表、搜索结果-->
                <CarListShow/>

            </el-main>

            <!--尾部-->
            <el-footer>
                <Footer/>
            </el-footer>
        </el-container>
    </div>
</template>

<script>

    import Header from '../Header'
    import CarInfoScreen from "../car/CarInfoScreen";
    import CarListShow from "../car/CarListShow";
    import TestScreen from "./TestScreen";
    import {getRequest} from "../../my-axios";
    import {postRequest} from "../../my-axios";
    import CarScreen from "../../components/sys/CarScreen";
    import Footer from "../Footer";

    export default {
        name: "CarInfoIndex",
        data() {
            return {
                // 搜索关键词
                searchKeyword: "",

                // 筛选的信息
                carAggVoList: {
                    carBrandVoList: {},
                    carColorVoList: {},
                    carGearBoxVoList: {},
                    carFuelTypeVoList: {},
                    carSeriesVoList: {},
                    carTypeVoList: {},
                    arrTitleList: {},
                },

                arrTitleList: [],

                aggVo: {},

                // carAggVoListList:[],

                // 车辆数据
                carInfoModel: [{
                    carBrand: "",
                    carSeries: "",
                    carUserTime: "",
                    transferCount: "",
                    carCarMileage: 1,
                    carColor: "",
                    carConfiguration: "",
                    //排量
                    carDisplacement: "",
                    carGearBox: "",
                    carPicture: "",
                    carPictures: {
                        carPictureLocation: 1,
                        carUrl: ""
                    },
                    carPrice: 0,
                    carQualityTime: 0,
                    carSeat: 4,
                    carTitle: "",
                    carType: "",
                }],

                //分页属性
                currPage: 1,
                pageSize: 10,
                totalPage: 10,

            };
        },
        created() {
            const _this = this;
            // 当前
            // this.getCarInfoModel();

        },
        methods: {

            // 初始化时获取全部汽车信息
            async getCarInfoModel() {
                let {data: resp} = await getRequest("/carInfo/searchListPage");
                console.log("父组件汽车信息列表resp", resp);

                let date = resp.data;
                console.log("父组件汽车信息列表 ", date.carInfoModel);

                this.carInfoModel = date.carInfoModel;
                this.currPage = date.currPage;
                this.pageSize = date.pageSize;
                this.totalpage = date.totalpage;
                this.carAggVoList = date.carAggVoList;
                this.arrTitleList = date.arrTitleList;

                this.aggVo = date.aggVo;
                console.log("aggVo: ", this.aggVo);
                // this.carAggVoListList = date.aggVo;

            }

            // // 初始化时获取全部汽车信息
            // async getCarInfoModel() {
            //     let {data: resp} = await getRequest("/carInfo/searchListPage");
            //     console.log("父组件汽车信息列表resp", resp);
            //
            //     let date = resp.data;
            //     console.log("父组件汽车信息列表 ", date.carInfoModel);
            //
            //     this.carInfoModel = date.carInfoModel;
            //     this.currPage = date.currPage;
            //     this.pageSize = date.pageSize;
            //     this.totalpage = date.totalpage;
            //     this.carAggVoList = date.carAggVoList;
            //     this.arrTitleList = date.arrTitleList;
            //
            //     this.aggVo = date.aggVo;
            //     console.log("aggVo: ", this.aggVo);
            //     // this.carAggVoListList = date.aggVo;
            //
            // }
        },
        computed: {},
        components: {Footer, CarScreen, Header, CarInfoScreen, CarListShow, TestScreen},
    }
</script>

<style scoped>

</style>
