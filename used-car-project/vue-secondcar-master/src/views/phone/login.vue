<template>
  <div>
    <!-- 1. nav-bar -->
    <van-nav-bar
        title="登录"
    />
    <!-- 2. 表单登录 -->
    <van-form @submit="onSubmit">
      <van-field
          v-model="userInfo.username"
          name="username"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="userInfo.password"
          name="password"
          type="password"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
      <van-field
          v-model="userInfo.code"
          name="code"
          label="验证码"
          placeholder="验证码"
          :rules="[{ required: true, message: '请填写验证码' }]"
      >
        <template #extra>
          <img :src="verCode" class="verCode" @click="newVerCode" alt="验证码加载中...">
        </template>
      </van-field>
      <div style="margin: 16px;">
        <van-button block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {baseUrl} from "../../my-axios";
import qs from "qs";

export default {
  name: 'login',
  data () {
    return {
      userInfo: {
        username: "admin",
        password: "admin",
        code: '',
        rememberMe: true
      },
      // 验证码
      verCode: "",
    }
  },
  mounted() {
    this.newVerCode();
  },
  methods: {
    onSubmit () {
      this.$axios.post("/yueChi/sysUser/login", qs.stringify(this.userInfo), {
            // 设置成表单提交
            headers: {
              "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
            },
          }
      ).then(resp => {
        // 拿到请求头中的jwt
        const jwt = resp.headers["authorization"];
        // 拿到date信息
        const userInfo = resp.data.data;
        // 从localStorage获取信息
        this.$store.commit("SET_TOKEN", jwt);
        this.$store.commit("SET_USERINFO", userInfo);
        // 获取用户信息
        console.log("_this.$store.getters.getUser", this.$store.getters.getUser);
        // 成功页面跳转到成功页面
        this.$router.push("/yueChi/phone/index");
      })
    },
    // 刷新验证码
    newVerCode() {
      //刷新验证码后面加上随机数防止缓存导致刷新验证码失败。也可以加时间戳
      this.verCode = baseUrl + "/captcha.jpg?m=" + Math.random();
    },
  }
}
</script>

<style lang="less" scoped>
// 按钮
.verCode {
  width: 35vw;
  height: auto;
  object-fit: fill;
  margin-left: 30px;
}

</style>
