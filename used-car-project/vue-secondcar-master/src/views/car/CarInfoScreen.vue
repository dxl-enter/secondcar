<template>
    <div>

        <!--所有聚合出的要展示的属性(当前请求中已有的不再显示)-->
        <!--        <div class="attr" v-for="(item,index) in 15">
                    <dl class="attr-value">
                        <dt><b>品牌</b></dt>
                        <dd>
                            <div class="dd-top">
                                <span class="a-box">
                                    <a>值 </a>
                                </span>
                            </div>
                        </dd>
                    </dl>
                </div>-->


        <!--搜索框-->
        <div class="crumb_search">
            <el-input v-model="keyword" @keyup.enter.native="search" placeholder="请输入查询的关键字" clearable>
                <!-- 回车搜索 @keyup.enter="touch"-->
                <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
        </div>


        <!--分割线-->
        <el-divider/>

        <!--过滤筛选组件-->
        <div class="demo">
            <div class="demo-warp">
                <div class="demo-flex" v-for="(item_1, index_1) in aggVo.aggVoListList" :key="index_1">
                    <span class="demo-title">
                        {{item_1.title}}
                    </span>

                    <div class="demo-content">
                        <div class="demo-tab" :class="isShow ? 'demo-hide' : ''">
                            <span @click="clearCarAttr(item_1.title )">不限</span>
                            <span v-for="(val, key) in item_1.aggChildrenList" :key="key"
                                  :class="{'demo-active': val.active  }"
                                  @click="addCarAttr(val.value, key, item_1.title, index_1)"
                            >
                                 {{val.value}}
                            </span>
                        </div>
                    </div>

                    <div class="demo-more" @click="isShow = !isShow" v-if="aggVo.aggVoListList.length >= 14">更多
                    </div>
                </div>
            </div>
        </div>
        <!--<div>-->
        <!--    <el-button @click="printSelectArr">显示选中的值</el-button>-->
        <!--</div>-->
    </div>
</template>

<script>

    import qs from "qs";
    import bus from "../../utils/bus";
    import {getRequest, postRequest} from "../../my-axios";

    export default {
        name: "CarInfoScreen",
        data() {
            return {
                // 搜索关键词
                keyword: "",

                carInfoModel: [],

                isShow: true,

                // 选择的属性。
                selectArr: [],

                // 保存请求对象的参数
                selectObject: {},


            };
        },
        props: {
            aggVo: Object,
        },

        created() {
            this.initPage();
        },

        methods: {

            // 初始化页面时
            initPage() {
                getRequest("/carInfo/searchListPage").then(resp => {
                    // 将查到的aggVo筛选属性的新值重新赋给页面。但是用这种方式会有警告，建议用计算属性操作
                    // data.data.aggVo.aggVoListList[0].title
                    this.aggVo = resp.data.data.aggVo;

                    this.carInfoModel = resp.data.data.carInfoModel;

                    // 传查询到的carInfoModel新数据发送给兄弟组件CarListShow
                    bus.$emit("getBroCarInfoModel", this.carInfoModel);

                    // 存入localStorage
                    // this.$store.commit("SET_CAR_INFO_MODEL", this.carInfoModel);
                    console.log("初始化页面请求后的resp = ", resp);
                });
            },


            // 搜索
            search() {

                console.log(this.keyword);
                // 已经有key这个属性，则删除带你原本的、再换成新的
                if (this.selectObject.hasOwnProperty("keyword")) {
                    this.$delete(this.selectObject, "keyword")
                }
                console.log(this.selectObject);

                // 添加到请求参数的对象中
                this.$set(this.selectObject, "keyword", this.keyword);
                console.log(this.selectObject);


                // postRequest('/carInfo/searchListPage',this.selectObject).then(resp =>{
                //     // 将查到的aggVo筛选属性的新值重新赋给页面。但是用这种方式会有警告，建议用计算属性操作
                //     this.aggVo = resp.data.data.aggVo;
                //
                //     this.carInfoModel = resp.data.data.carInfoModel;
                //
                //     // 传查询到的carInfoModel新数据发送给兄弟组件CarListShow
                //     bus.$emit("getBroCarInfoModel", this.carInfoModel);
                //
                //     // 存入localStorage
                //     this.$store.commit("SET_CAR_INFO_MODEL", this.carInfoModel);
                //     console.log("请求后的resp = ", resp);
                // });


                this.$axios({
                    url: "/carInfo/searchListPage",
                    method: "get",
                    params: this.selectObject,
                    paramsSerializer: function (params) {
                        return qs.stringify(params, {arrayFormat: "indices"});
                    },
                }).then(resp => {
                    // 将查到的aggVo筛选属性的新值重新赋给页面。但是用这种方式会有警告，建议用计算属性操作
                    this.aggVo = resp.data.data.aggVo;

                    this.carInfoModel = resp.data.data.carInfoModel;


                    // 传查询到的carInfoModel新数据发送给兄弟组件CarListShow
                    bus.$emit("getBroCarInfoModel", this.carInfoModel);

                    // 存入localStorage
                    // this.$store.commit("SET_CAR_INFO_MODEL", this.carInfoModel);
                    console.log("请求后的resp = ", resp);
                });


                // getRequest('/carInfo/searchListPage',this.selectObject).then(resp=>{
                //     console.log("搜索请求后：",resp);
                // })
            },

            // 不限条件
            clearCarAttr(title) {
                console.log("点击不限 ", title);

                // 先清空原来的全部属性。
                this.selectArr = [];
                this.selectObject = {};

                // 转换选择的属性
                let att = this.changeAttrToEntity(title);
                console.log("clearCarAttr属性 ", att);

                // 替换并发请求.选择不限后该属性对应的值就是null。如 选品牌不限、则carBrand = null. 若不为空则会有误。
                this.selectScreenMap(att, null);

            },

            // 属性查询跳转方法
            // value= 选中的值  title=选中值在该属性名   attIndex=选中的属性在属性数组中的下标  key=值对应在该数组的下标
            // 即（宝马  0  品牌 0） =》 宝马是选中的属性值，0是宝马在属性值数组中对应的下标为0, 品牌是选中的属性，0是品牌在属性数组中对应的下标为0
            addCarAttr(value, key, title, attIndex) {

                console.log("value : key: title : attIndex = ", value, key, title, attIndex);

                // 1. 添加 active ==> true 显示 `active样式`
                // 1.1 先把全部变为false没选
                console.log(this.aggVo.aggVoListList[attIndex].aggChildrenList[key].value);
                this.aggVo.aggVoListList[attIndex].aggChildrenList.map(item => {
                    item.active = false
                });
                // 1.2 选中当前选中的属性值，选中后会激活选中的样式。如选宝马。宝马就会变颜色、其他属性值就会恢复默认。
                this.aggVo.aggVoListList[attIndex].aggChildrenList[key].active = true;


                // 2. 请求的具体属性。点击了哪个属性
                // var att = "";
                var att = this.changeAttrToEntity(title);
                console.log("函数转换的属性：", att);


                // switch (title) {
                //     case "品牌":
                //         att = "carBrand";
                //         break;
                //     case "车系":
                //         att = "carSeries";
                //         break;
                //     case "类型":
                //         att = "carType";
                //         break;
                //     case "燃料类型":
                //         att = "carFuelType";
                //         break;
                //     case "颜色":
                //         att = "carColor";
                //         break;
                //     case "变数箱类型":
                //         att = "carGearBox";
                //         break;
                // }

                console.log("函数转换后的att = ", att);

                // 选中的数据
                let selectValue = "";
                this.aggVo.aggVoListList[attIndex].aggChildrenList.map(item => {
                    if (item.active === true) {
                        selectValue = item.value;
                        this.selectArr.push(item);
                    }
                });

                // 发送请求
                this.selectScreenMap(att, value);

                // 3. 拼接请求参数。

                // 打印 用于调试 。
                this.printSelectArr();

            },

            // 选择的属性（title）进行转换成对应字段。即中文转英文。  如： 品牌 = > carBrand;
            changeAttrToEntity(title) {
                var att = "";
                switch (title) {
                    case "品牌":
                        att = "carBrand";
                        break;
                    case "车系":
                        att = "carSeries";
                        break;
                    case "类型":
                        att = "carType";
                        break;
                    case "燃料类型":
                        att = "carFuelType";
                        break;
                    case "颜色":
                        att = "carColor";
                        break;
                    case "变数箱类型":
                        att = "carGearBox";
                        break;
                }

                console.log("changeAttrToEntity转换属性", att);
                return att;
            },


            // 选中的属性值。
            selectScreenMap(key, value) {
                console.log(key, value);

                // 判断对象是否有key这个属性
                console.log(this.selectObject.hasOwnProperty(key));
                // 已经有key这个属性，则删除带你原本的、再换成新的
                if (this.selectObject.hasOwnProperty(key)) {
                    this.$delete(this.selectObject, key)
                }

                // 添加到请求参数的对象中
                this.$set(this.selectObject, key, value);


                // var carInfoModel = [];
                this.$axios({
                    url: "/carInfo/searchListPage",
                    method: "get",
                    params: this.selectObject,
                    paramsSerializer: function (params) {
                        return qs.stringify(params, {arrayFormat: "indices"});
                    },
                }).then(resp => {
                    // 将查到的aggVo筛选属性的新值重新赋给页面。但是用这种方式会有警告，建议用计算属性操作
                    this.aggVo = resp.data.data.aggVo;

                    this.carInfoModel = resp.data.data.carInfoModel;

                    // 传查询到的carInfoModel新数据发送给兄弟组件CarListShow
                    bus.$emit("getBroCarInfoModel", this.carInfoModel);
                    // 传查询参数到兄弟组件、分页会用到
                    bus.$emit("getSelectObject", this.selectObject);

                    // 存入localStorage
                    // this.$store.commit("SET_CAR_INFO_MODEL", this.carInfoModel);
                    console.log("请求后的resp = ", resp);
                });


                // 发给父组件
                // this.$emit('getSelData', carInfoModel);


                // 打印请求参数
                // console.log("请求参数：",this.$route.params);
                console.log("请求参数：", this.selectObject);

            },

            // 去重
            unique(arr) {
                return Array.from(new Set(arr))
            },

            // 显示选中的值
            printSelectArr() {
                // 先去重
                this.unique(this.selectArr);

                this.selectArr.map(item => {
                    console.log("selectArr", item);
                });

                // 打印url
                //完整url可以用 window.location.href
                // 路由路径可以用 this.$route.path
                // 路由路径参数 this.$route.params
            }
        },
        computed: {},
        components: {},
    }
</script>

<style scoped>
    /* 过滤列表:start */
    .demo {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
        margin-bottom: 15px;
        min-height: 140px;
        height: auto !important;
        height: 140px;
    }

    .demo-warp {
        display: flex;
        max-width: 1200px;
        margin: auto;
        height: 100%;
        flex-direction: column;
        padding: 15px 0;
    }

    .demo-flex {
        display: flex;
        margin-bottom: 15px;
    }

    .demo-flex:last-of-type {
        margin-bottom: 0;
    }

    .demo-title {
        flex-basis: 80px;
        margin-top: 10px;
    }

    .demo-content {
        display: flex;
        flex: 1;
    }

    .demo-tab {
        flex: 1;
        margin-left: 15px;
        margin-right: 15px;
        height: 35px;
        overflow: hidden;
        text-align: left;
    }

    .demo-tab span {
        display: inline-block;
        margin: 0 5px 15px 5px;
        cursor: pointer;
        padding: 5px 10px;
        color: #999999;
    }

    .demo-more {
        margin-top: 5px;
        cursor: pointer;
    }

    .demo-active {
        background-color: #09F;
        color: white !important;
        border-radius: 3px;
    }

    .demo-tab span:hover {
        background-color: #09F;
        color: white;
        border-radius: 3px;
    }

    .demo-hide {
        min-height: 35px;
        height: auto !important;
    }


    /*====以下样式为a标签变为按钮=======*/
    a {
        display: inline-block;
        margin: 0px 0px 5px 5px;
        padding: 6px 8px;
        font-size: 14px;
        outline: none;
        text-align: center;
        width: 50px;
        line-height: 30px;
        cursor: pointer;
        color: white;
        background-color: #09F;
    }

    /*====以上样式为a标签变为按钮=======*/

    /* 过滤列表:end */

    /*div {*/
    /*    display: block;*/
    /*}*/
    /*dl {*/
    /*    border-bottom: 1px solid #e6e6e6;*/
    /*    display: block;*/
    /*    margin-block-start: 1em;*/
    /*    margin-block-end: 1em;*/
    /*    margin-inline-start: 0px;*/
    /*    margin-inline-end: 0px;*/
    /*}*/

    /*dt {*/
    /*    display: block;*/
    /*    float: left;*/
    /*    width: 70px;*/
    /*    height: 100%;*/
    /*    position: absolute;*/
    /*    line-height: 50px;*/
    /*    font-size: 14px;*/
    /*    color: #7a838d;*/
    /*    text-align: center;*/
    /*    background: #fafafa;*/
    /*}*/

    /*.dd-top {*/
    /*    height: 20px;*/
    /*    padding: 15px 0 15px 6px;*/
    /*}*/

    /*.dd-top .a-box {*/
    /*    float: left;*/
    /*    width: 940px;*/
    /*    height: 20px;*/
    /*    overflow: hidden;*/
    /*}*/


    /*.screen dd {*/
    /*    float: right;*/
    /*    width: 1128px;*/
    /*}*/

    /*dd {*/
    /*    display: block;*/
    /*    margin-inline-start: 40px;*/
    /*}*/


    /*.dd-top a {*/
    /*    float: left;*/
    /*    height: 20px;*/
    /*    font-size: 14px;*/
    /*    line-height: 20px;*/
    /*    padding: 0 6px;*/
    /*    color: #495056;*/
    /*    margin: 0 8px;*/
    /*}*/

</style>
