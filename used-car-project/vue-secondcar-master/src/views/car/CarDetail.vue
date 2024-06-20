<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <!--头-->
    <Header />

    <!--主题信息-->
    <div class="car-detail-main">
      <!--第二部分-->
      <el-row class="car-title" style="text-align: left">
        <!--标题-->
        <h2>车辆概览</h2>
      </el-row>

      <!--轮播图  4-轮播图-->
      <el-row class="car-block" style="padding-top: 5px;">
        <el-col :span="16">
          <el-carousel :interval="4000" type="card" height="300px">
            <el-carousel-item v-for="(item,index) in carPictureCarousel" :key="index">
              <img class="medium" :src="item.carUrl" id="imgW" />
            </el-carousel-item>
          </el-carousel>
        </el-col>

        <!--标题和主要信息-->
        <el-col :span="8">
          <el-row><h3>{{carDetail.carInfo.carTitle}}</h3></el-row>

          <!--部分属性值-->
          <el-row class="car-attr">
            <el-col :span="4">
              <el-row>使用年限</el-row>
              <el-row>{{carDetail.carInfo.carUserTime}}</el-row>
            </el-col>
            <el-col :span="10">
              <el-row>里程数(万公里)</el-row>
              <el-row>{{carDetail.carInfo.carCarMileage}}</el-row>
            </el-col>
            <el-col :span="4">
              <el-row>排量</el-row>
              <el-row>{{carDetail.carInfo.carDisplacement}}</el-row>
            </el-col>
            <el-col :span="6">
              <el-row>变速箱</el-row>
              <el-row>{{carDetail.carInfo.carGearBox}}</el-row>
            </el-col>
          </el-row>


          <div class="second">
            <el-row>
              价格： {{carDetail.carInfo.carPrice}} 元
            </el-row>
          </div>


          <el-row style="padding-top: 20px">

            <el-button type="primary" @click="showOrder">预订</el-button>
            <el-button @click="chat">咨询</el-button>

            <!--收藏-->
            <el-tooltip class="item" effect="dark" :content="isCollectCar ? '已经收藏': '点击收藏'"
                        placement="bottom">

              <el-button :type="isCollectCar ? 'primary' : ''"
                         icon="el-icon-star-off" circle
                         @click="changeCollect"></el-button>
            </el-tooltip>
          </el-row>

        </el-col>

        <!--预订、下单的弹出框子组件-->
        <el-dialog :visible.sync="dialogFormVisibleOrder" width="50%" title="订单">
          <Order @dialogFormVisibleOrder="closeOrder" :dataToOrder="carDetail.carInfo"></Order>
        </el-dialog>

      </el-row>

      <!--聊天页面 抽屉-->
      <el-drawer
        title="聊天咨询"
        :visible.sync="chatDrawer"
        size="50%"
        :with-header="false">
        <Chat />
      </el-drawer>

      <!--第二部分-->
      <el-row class="car-del" style="text-align: left; padding-top: 10px;">
        <!--标题-->
        <h2>车辆详细信息</h2>
        <!--车主信息概览-->
        <div class="sell-info">
                    <span>
                        <i class="el-icon-user-solid"> 车主：{{carDetail.name}} {{carDetail.sex === 0? "先生": "女士"}}</i>
                    </span>
          <span>  <i class="el-icon-phone"> {{carDetail.phone}}</i></span>
          <!--<span>{{carDetail.sex === 0? '男': '女'}}</span>-->

          <el-divider />
          <!--汽车 参数-->
          <div class="csbox">
            <div class="one-line">基本参数</div>
            <ul>
              <li><span>品牌</span><span>{{carDetail.carInfo.carBrand}}</span></li>
              <li><span>车系</span><span>{{carDetail.carInfo.carSeries}}</span></li>
              <li><span>车型</span><span>{{carDetail.carInfo.carType}}</span></li>
              <li><span>使用年限</span><span>{{carDetail.carInfo.carUserTime}}</span></li>
              <li><span>过户次数</span><span>{{carDetail.carInfo.transferCount}}</span></li>
              <li><span>行驶里程</span><span>{{carDetail.carInfo.carCarMileage}}</span></li>
              <li><span>颜色</span><span>{{carDetail.carInfo.carColor}}</span></li>
              <li><span>变数箱</span><span>{{carDetail.carInfo.carGearBox}}</span></li>
              <li><span>排量</span><span>{{carDetail.carInfo.carDisplacement}}</span></li>
              <li><span>汽车生产日期</span><span>{{carDetail.carInfo.produceTime}}</span></li>
              <li><span>座位数</span><span>{{carDetail.carInfo.carSeat}}</span></li>
              <li><span>燃料类型</span><span>{{carDetail.carInfo.carFuelType}}</span></li>
              <li><span>国别</span><span>{{carDetail.carInfo.carRegionId}}</span></li>
              <li><span>前轮胎尺寸</span><span>{{carDetail.carInfo.carFrontTyre}}</span></li>
              <li><span>后轮胎尺寸</span><span>{{carDetail.carInfo.rearTyre}}</span></li>
              <li><span>其他配置</span><span>{{carDetail.carInfo.carConfiguration}}</span></li>
              <li><span>其他描述</span><span>{{carDetail.carInfo.carDescribe}}</span></li>
            </ul>

          </div>


        </div>


      </el-row>


      <!--第三部分- 汽车图片-->
      <el-row class="car-picture-part" style="text-align: left; padding-top: 10px;">
        <!--标题-->
        <h2>车辆图片</h2>


        <el-row class="car-picture-info" style="text-align: left; padding-top: 10px;">

          <!--1. 外部-->
          <el-row>
            <h3 style="padding-left: 60px;">汽车外饰</h3>
            <ul>
              <li v-for="item in carPictureOnOutside">
                <!--<el-image :src="item.carUrl"></el-image>-->
                <img :src="item.carUrl" alt="加载中">
              </li>
            </ul>
          </el-row>

          <!--分割线-->
          <el-divider />

          <el-row>

            <!--2. 内部-->
            <h3 style="padding-left: 60px;">汽车内饰</h3>
            <ul>
              <li v-for="item in carPictureOnInside">
                <!--<el-image :src="item.carUrl"></el-image>-->
                <img :src="item.carUrl" alt="加载中">
              </li>
            </ul>

          </el-row>

          <!--分割线-->
          <el-divider />

          <el-row>

            <h3 style="padding-left: 60px;">汽车其他图片</h3>
            <!--3. 其他-->
            <ul>
              <li v-for="item in carPictureOnOther">
                <img :src="item.carUrl" alt="加载中">
              </li>
            </ul>
          </el-row>
        </el-row>
      </el-row>
    </div>
    <!--尾部-->

    <Footer />

  </div>
</template>

<script>
import Header from "../Header";
import { getRequest } from "../../my-axios";
import Footer from "../Footer";
import Chat from "../chat/Chat";
import bus from "../../utils/bus";
import Order from "../pay/Order";

export default {
  name: "CarDetail",
  data () {
    return {
      carDetail: {
        carInfo: {
          carBrand: "",
          carSeries: "",
          carUserTime: "",
          transferCount: "",
          carCarMileage: 1,
          carColor: "",
          carConfiguration: "",
          carDisplacement: "", //排量
          carGearBox: "",
          carPicture: "",
          carPrice: 0,
          carQualityTime: 0,
          carSeat: 4,
          carTitle: "标题",
          carFrontTyre: "-",
          rearTyre: "-",
          carDescribe: "-",
          carType: "",
        },

        name: "",
        phone: "",
        sex: "",
        address: "",
      },
      // carPicturesMap: {},
      // 汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4-轮播图
      carPictureOnInside: [],// 1 内部
      carPictureOnOutside: [],// 2 外部
      carPictureOnOther: [],// 3 其他
      carPictureCarousel: [],// 4 轮播图

      isCollectCar: false, // 是否收藏

      chatDrawer: false,// 聊天弹窗

      dialogFormVisibleOrder: false,// 订单
    };
  },
  created () {
    this.initPage();
    this.isCollect();
  },
  methods: {

    // 关闭订单
    closeOrder (data) {
      this.dialogFormVisibleOrder = data;
    },

    // 打开订单弹窗
    showOrder () {
      if (this.$store.getters.getUser === null) {
        this.$message.info({ message: "请先登录", duration: 1.5 * 1000, showClose: true });
        // this.$router.push('/yueChi/sysUser/login');
        return;
      }

      this.dialogFormVisibleOrder = true;
    },

    // 聊天
    chat () {
      this.chatDrawer = true;
      // 连接ws
      this.$store.dispatch("connect")
    },
    // 初始化页面
    initPage () {
      // console.log(this.$route.params);
      console.log(this.$route.params.carId);

      // this.$axios.get()
      getRequest("/carInfo/getCarDetail/" + this.$route.params.carId).then(resp => {
        console.log("汽车详细页resp：", resp);
        this.carDetail = resp.data.data;
        console.log("carDescribe.carInfo ：", this.carDetail.carInfo.carTitle);

        var carPicturesMap = {};
        carPicturesMap = resp.data.data.carPicturesMap;
        console.log("carPicturesMap: ", carPicturesMap);

        // 将卖家信息传给chat页面。
        bus.$emit("sellerName", this.carDetail.name);

        for (let key in carPicturesMap) {
          // 图片位置是1 即内部
          if (key == 1) {
            this.carPictureOnInside = carPicturesMap[key];
            console.log("carPictureOnInside ", this.carPictureOnInside);
          }
          // 图片位置是2 即外部
          else if (key == 2) {
            this.carPictureOnOutside = carPicturesMap[key];
            console.log("carPictureOnOutside ", this.carPictureOnOutside);
          }
          // 图片位置是3 即其他
          else if (key == 3) {
            this.carPictureOnOther = carPicturesMap[key];
            console.log("carPictureOnOther ", this.carPictureOnOther);
          }
          // 图片位置是4 即轮播图
          else if (key == 4) {
            this.carPictureCarousel = carPicturesMap[key];
            console.log("carPictureCarousel ", this.carPictureCarousel);
          }
        }
        // data.data.carPicturesMap[1]
      })

    },

    // 收藏汽车
    changeCollect () {
      getRequest("/yueChi/sysUser/changeMyCollectCar/" + this.$route.params.carId).then(resp => {
        this.isCollectCar = resp.data.data;
      }).catch(error => {

      });
    },

    // 该车是否已经收藏
    isCollect () {
      getRequest("/yueChi/sysUser/isCollectCar/" + this.$route.params.carId).then(resp => {
        this.isCollectCar = resp.data.data;
      })
    }
  },
  computed: {},
  components: { Order, Chat, Footer, Header },
}
</script>

<style scoped>

/*汽车部分属性及其属性值*/
.car-attr {
  font-size: medium;
  font-weight: 100;
  padding-top: 10px;
  padding-bottom: 10px;
}

/*el-row标签里都是同一个样式*/
.car-block, .sell-info, .car-picture-info {
  font-weight: bold;
  border-radius: 17px;

  box-shadow: 10px 10px 20px #adc4c9,
  -10px -10px 20px #e9ffff;

}


.second {
  padding-top: 40px;
}

/*横向显示li*/
.car-base-info li {
  float: left;
  list-style: none;
  border-left: 1px;
  display: inline-block;
  margin: 3px;
  margin-left: 10px;

}

/*以下汽车图片展示两列*/

.car-picture-info li {
  width: 50%;
  float: left;
  display: block;
  text-align: center;
}

.car-picture-info li img {
  width: 90%;
}

/*以上汽车图片展示两列*/

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

#imgW {
  height: 100%;
}


/*汽车车主信息*/
.sell-info {
  padding-top: 20px;
}

.sell-info span {
  font-weight: 100;
  padding-left: 50px;

  font-size: 20px
}


/*汽车参数*/
.csbox {
  padding-left: 30px;
  padding-bottom: 30px;
}

.csbox .one-line {
  width: 50%;
  text-align: center;
  background-color: #f5f5f5;
  font-size: 20px;
  font-weight: 100;
}

.csbox ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 50%;

}

.csbox ul li {
}

.csbox ul li span {
  display: inline-block;
  width: 50%;
  height: 38px;
  line-height: 38px;
  font-size: 15px;
  border: 1px solid #ececec;
  text-align: center;
  box-sizing: border-box;
  font-family: Microsoft Yahei;
  font-weight: 100;
}


/*收藏汽车 颜色改变*/
.collectCar {
  background-color: #409EFF;
}
</style>
