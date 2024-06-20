<template>
    <div>
        <div class="h5-title">
            我收藏的汽车列表
            <!--<span><el-button >按收藏时间排序</el-button></span>-->
        </div>
        <el-row class="row-cars">
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
    import qs from "qs";

    export default {
        name: "MyAllCollectCars",
        data() {
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
            };
        },
        created() {
            this.page();
        },
        methods: {
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
                    url: "/yueChi/sysUser/getMyCollectCarPage",
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
        components: {},
    }
</script>

<style scoped>

    /*汽车列表*/
    .row-cars {
        padding-bottom: 10px;
    }

    /*标题居中显示*/
    .h5-title {
        text-align: center;
        padding-bottom: 10px;
    }
</style>
