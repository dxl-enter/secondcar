<template>
    <div>
        <div class="order">
            <el-form ref="orderForm" :model="orderFormData" :rules="rules" size="medium" label-width="100px"
                     label-position="left">
                <el-row>
                    <!--                    <el-col :span="18">
                                            <el-form-item label="订单编号" prop="orderNumber">
                                                <el-input v-model="orderFormData.orderNumber" placeholder="请输入订单编号" readonly
                                                          :disabled='true'
                                                          :style="{width: '100%'}"></el-input>
                                            </el-form-item>
                                        </el-col>-->
                    <el-col :span="9">
                        <el-form-item label="买家姓名" prop="buyName">
                            <el-input v-model="orderFormData.buyName" placeholder="请输入买家姓名"
                                      :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="买家电话" prop="buyerPhone">
                            <el-input v-model="orderFormData.buyerPhone" placeholder="请输入买家电话"
                                      :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="汽车品牌" prop="carBrand">
                            <el-input v-model="orderFormData.carBrand" placeholder="请输入汽车品牌" readonly :disabled='true'
                                      clearable :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="汽车车系" prop="carSeries">
                            <el-input v-model="orderFormData.carSeries" placeholder="请输入汽车车系" readonly :disabled='true'
                                      :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="汽车颜色" prop="carColor">
                            <el-input v-model="orderFormData.carColor" placeholder="请输入汽车颜色" readonly :disabled='true'
                                      :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="汽车价格" prop="carPrice">
                            <el-input v-model="orderFormData.carPrice" placeholder="请输入汽车价格" readonly :disabled='true'
                                      :style="{width: '100%'}"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="handelConfirm">去付款</el-button>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        name: "Order",
        components: {},
        props: ["dataToOrder"],
        data() {
            return {
                //订单信息
                orderFormData: {
                    // orderNumber: undefined,
                    buyName: "",
                    buyId: null,
                    buyerPhone: "",
                    carId: "",
                    carBrand: "",
                    carSeries: "",
                    carColor: "",
                    carPrice: null,
                },
                rules: {
                    orderNumber: [{
                        required: true,
                        message: '请输入订单编号',
                        trigger: 'blur'
                    }],
                    buyerName: [{
                        required: true,
                        message: '请输入买家姓名',
                        trigger: 'blur'
                    }],
                    buyerPhone: [{
                        required: true,
                        message: '请输入买家电话',
                        trigger: 'blur'
                    }],
                    carBrand: [{
                        required: true,
                        message: '请输入汽车品牌',
                        trigger: 'blur'
                    }],
                    carSeries: [{
                        required: true,
                        message: '请输入汽车车系',
                        trigger: 'blur'
                    }],
                    carColor: [{
                        required: true,
                        message: '请输入汽车颜色',
                        trigger: 'blur'
                    }],
                    carPrice: [{
                        required: true,
                        message: '请输入汽车价格',
                        trigger: 'blur'
                    }],
                },
            }
        },
        created() {
            this.init();
        },
        methods: {

            init() {
                // console.log("dataToOrder ",this.dataToOrder);
                // 汽车属性
                this.orderFormData.carId = this.dataToOrder.carId;
                this.orderFormData.carBrand = this.dataToOrder.carBrand;
                this.orderFormData.carSeries = this.dataToOrder.carSeries;
                this.orderFormData.carColor = this.dataToOrder.carColor;
                this.orderFormData.carPrice = this.dataToOrder.carPrice;

                // 买家属性
                this.orderFormData.buyId = this.$store.getters.getUser.userId;
                 console.log(this.$store.getters.getUser.name);
                 console.log(this.$store.getters.getUser.phone);
                this.orderFormData.buyName = this.$store.getters.getUser.name;
                this.orderFormData.buyPhone = this.$store.getters.getUser.phone;

              /*  if (this.$store.getters.getUser.name !== null) {
                    console.log("name != null ");
                    this.orderFormData.buyName = this.$store.getters.getUser.name;
                }
                if (this.$store.getters.getUser.phone !== null) {
                    console.log("phone != null ");
                    this.orderFormData.buyPhone = this.$store.getters.getUser.phone;
                }
*/
            },

            onOpen() {
            },

            onClose() {
                this.$refs['orderForm'].resetFields()
            },
            close() {
                // this.$emit('update:visible', false)
                // 关闭弹窗
                this.$emit('dialogFormVisibleOrder', false)
            },
            handelConfirm() {
                this.$refs['orderForm'].validate(valid => {
                        // 确认
                        if (valid) {
                            // 先创建订单、然后才支付
                            this.$axios.post('/sysOrder/pay', this.orderFormData).then(resp => {

                                console.log("【支付宝返回】" + resp.data);

                                // 先提交页面才显示支付宝支付页面的二维码
                                document.querySelector('body').innerHTML = resp.data;//查找到当前页面的body，将后台返回的form替换掉他的内容
                                const div = document.createElement('div');
                                div.innerHTML = resp.data;
                                document.body.appendChild(div);
                                document.forms[0].setAttribute('target', '_blank');// 新建窗口页面
                                document.forms[0].submit();//执行submit表单提交，让页面重定向，跳转到支付宝页面

                            })

                            // this.$router.push('/alipay')
                        }
                    }
                )
            },
        }
    }

</script>
<style scoped>

    /*order总体样式*/
    .order {
        margin: 0px auto;
    }
</style>
