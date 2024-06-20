<template>
    <div class="main_container">
        <el-container>
            <!--头部布局-->
            <Header/>

            <!--中间-->
            <el-main>
                <!--面包屑-->
                <div class="breadcrumb">
                    <el-breadcrumb separator-cl ass="el-icon-arrow-right">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>活动管理</el-breadcrumb-item>
                        <el-breadcrumb-item>活动列表</el-breadcrumb-item>
                        <el-breadcrumb-item>活动详情</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>

                <!--搜索框-->
                <div class="crumb_search">
                    <el-input>
                        <el-button slot="append" icon="el-icon-search"></el-button>
                    </el-input>
                </div>

                <!--多条件搜索、过滤-->
                <CarScreen   v-bind:get-list="filterList" @get-sel-data="getFilterSelData" @set-time="setTime"/>

                <!--搜索总数据个数-->
                <pre>
   			        选中的数据：{{filterSelData}}
   		        </pre>

                <!--汽车列表、搜索结果-->
                <CarList v-bind:view-data="viewList" width="25%" height="300px" :view-time="viewTime"/>
            </el-main>

            <!--尾部-->
            <el-footer>
                <Footer/>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
    import Header from './Header'
    import CarScreen from "../components/sys/CarScreen";
    import CarList from "../components/sys/CarList";
    import Footer from "./Footer";

    export default {
        name: "CarIndex",
        data() {
            return {
                viewList: [],
                viewTime: {
                    time: true,
                    msg: '数据拼命加载中...'
                },
                param: {},
                filterList: [],
                filterSelData: [] // 过滤选中的数据
            };
        },
        created() {
            let _this = this;
            // 请求数据
            // _this.$axios.get("/carInfo/findAllCarBrand").then(resp => {
            //     console.log("res : ", resp);
            //     this.viewList = [...resp.data.data]
            // });

            this.setRequest('/carInfo/findAllCarBrand', this.param, 'get').then(res => {
                console.log("viewList res : ", res);
                this.viewList = [...res.data]
                return this.setRequest('/carInfo/findAllCarBrand', this.param, 'get')
            }).then(res => {
                console.log("filterList res :", res);
                this.filterList = [...res.data]
            })
        },
        mounted() {
            this.setTime(1000, 0, false)
        },
        methods: {
            // 封装 axios
            setRequest(url, data = {}, method = 'get') {
                return new Promise((resolve, reject) => {
                    this.$axios({
                        url,
                        method,
                        data
                    }).then(res => {
                        resolve(res.data)
                    }).catch(err => {
                        reject(err)
                    })
                })
            },

            // 获取筛选组件选中的值
            getFilterSelData(data) {
                this.filterSelData = data
            },

            // 模拟延时显示数据视图
            setTime(startTime, endTime, bool) {
                setTimeout(() => {
                    this.viewTime.time = bool
                    setTimeout(() => {
                        this.viewTime.time = false
                    }, endTime)
                }, startTime)
            }
        },
        computed: {},
        components: {Footer, CarScreen, Header, CarList},
    }
</script>

<!-- scoped :它的 CSS 只作用于当前组件中的元素 -->
<style lang="less" scoped>

    /*设置整个容器的高度*/
    .main_container {
        height: 100%;
        background-color: #797b72;
    }

    .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
    }

    /*搜索框*/
    .crumb_search {
        float: right;
    }

</style>
