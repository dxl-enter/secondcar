<template>
  <div class="order-goods">
    <van-nav-bar
        title="首页"
    />
    <van-pull-refresh
        v-if="isShowList"
        v-model="refreshing"
        @refresh="onRefresh"
    >
      <van-list
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
      >
        <div class="box">
          <audit-item v-for="(info, index) in auditList" :key="index" :item="info" @goToDetails="goToDetails" />
        </div>
      </van-list>
    </van-pull-refresh>
    <van-empty v-else description="暂无数据">
      <van-button class="bottom-button" round color="#305FF5" @click="refreshPage">刷新</van-button>
    </van-empty>
  </div>
</template>
<script>
import qs from "qs";
import AuditItem from "./components/AuditItem";
export default {
  components:{ AuditItem },
  data(){
    return {
      // 列表
      listParams: {
        currPage:1,
        pageSize: 10
      },
      auditList: [],
      orderList:[],
      loading: false,
      finished: false,
      refreshing: false,
      isShowList: true,
    }
  },
  methods:{
    refreshPage() {
      location.reload()
    },
    goToDetails(info) {
      this.$router.push({
        path: "/auditDetail",
        query: {
          orderId: info.id
        }
      })
    },
    async onLoad() {
      this.$axios({
        url: "/carInfo/searchListPage",
        method: "get",
        params: this.listParams,
        paramsSerializer: function (params) {
          return qs.stringify(params, {arrayFormat: "indices"});
        },
      }).then((res) => {
        console.log('res====', res.data.data.carInfoModel)
        if (this.refreshing) {
          this.auditList = []
          this.refreshing = false
        }
        this.auditList = [...this.auditList, ...res.data.data.carInfoModel]
        this.loading = false
        if (this.auditList.length >= res.data.data.totalCount) {
          this.finished = true
        }
        this.listParams.currPage++
        if (this.auditList.length > 0) {
          this.isShowList = true
          return
        }
        this.isShowList = false
      })
    },
    resetData() {
      this.listParams.currPage = 1
      // 清空列表数据
      this.finished = false
      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true
      this.orderList = []
    },
    // 下拉刷新处理函数
    onRefresh() {
      this.resetData()
      this.onLoad()
    },
  }
}
</script>
<style lang="less" scoped>
.order-goods {
  height: 100%;
  text-align: left;
  // 能全屏加载刷新
  ::v-deep .van-pull-refresh__track {
    //height: calc(100vh - 50px);
  }
  ::v-deep .van-list__finished-text {
    height: 100%;
  }
  .van-list{
    width:100%;
    padding:11px;
    box-sizing: border-box;
  }
  .box {
    position: relative;
    width:100%;
    column-count: 2;
    flex-flow: column wrap;
    background-color: aliceblue;
  }
}
</style>
