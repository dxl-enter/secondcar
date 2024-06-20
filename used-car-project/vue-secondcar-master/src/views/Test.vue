<template>
    <div class="custom-demo-view" style="padding: 1rem;">
        <div>
            <el-tabs v-if="reload" v-model="tabIndex" @tab-click="jump">
                <el-tab-pane :label="tabs[0].title" v-if="tabs[0].isShow" name="0"></el-tab-pane>
                <el-tab-pane :label="tabs[1].title" v-if="tabs[1].isShow" name="1"></el-tab-pane>
                <el-tab-pane :label="tabs[2].title" v-if="tabs[2].isShow" name="2"></el-tab-pane>
            </el-tabs>
        </div>
        <div class="scroll-content" @scroll="onScroll"
             :style="'overflow-x: hidden; overflow-y: auto;height:' + contentStyleObj.height">
            <!-- 用户管理 -->
            <div :ref="tabs[0].refName" class="scroll-item" style="padding-top: 1rem;">
                <div class="line-title">
                    <h2>{{tabs[0].title}}</h2>
                </div>
                <div>
                    <p style="height:40px" v-for="item in [0,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16]" :key="item">
                        待发货符合规范化如故</p>
                </div>
            </div>
            <!-- 配置管理 -->
            <div :ref="tabs[1].refName" class="scroll-item" style="padding-top: 1rem;">
                <div class="line-title">
                    <h2>{{tabs[1].title}}</h2>
                </div>
                <div>
                    <p style="height:40px" v-for="item in [0,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16]" :key="item">
                        新能说出的基本功发到你DNF不得发布</p>
                </div>
            </div>
            <!-- 角色管理 -->
            <div :ref="tabs[2].refName" class="scroll-item" style="padding-top: 1rem;top:5px;">
                <div class="line-title">
                    <h2>{{tabs[2].title}}</h2>
                </div>
                <div>
                    <p v-for="item in [0,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16]" :key="item">新水泥厂剧场版</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Test",
    data () {
        return {
            reload: true,
            tabIndex: "0",
            contentStyleObj: {
                height: "100px"
            },
            tabs: [
                {
                    isShow: true,
                    title: "用户管理",
                    refName: "setOneRef"
                },
                {
                    isShow: true,
                    title: "配置管理",
                    refName: "setTwoRef"
                },
                {
                    isShow: true,
                    title: "角色管理",
                    refName: "setThreeRef"
                }
            ]
        }
    },
    methods: {
        refresh () {
            this.reload = false
            this.$nextTick(() => (this.reload = true))
        },
        // tab click
        jump (index, info) {
            let target = document.querySelector(".scroll-content")
            let scrollItems = document.querySelectorAll(".scroll-item")
            // 判断滚动条是否滚动到底部
            if (target.scrollHeight <= target.scrollTop + target.clientHeight) {
                this.tabIndex = index.index.toString()
            }
            let totalY = scrollItems[index.index].offsetTop - scrollItems[0].offsetTop // 锚点元素距离其offsetParent(这里是body)顶部的距离(待滚动的距离)
            let distance = document.querySelector(".scroll-content").scrollTop // 滚动条距离滚动区域顶部的距离
            // let distance = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset // 滚动条距离滚动区域顶部的距离(滚动区域为窗口)
            // 滚动动画实现, 使用setTimeout的递归实现平滑滚动，将距离细分为50小段，10ms滚动一次
            // 计算每一小段的距离
            let step = totalY / 50
            if (totalY > distance) {
                smoothDown(document.querySelector(".scroll-content"))
            } else {
                let newTotal = distance - totalY
                step = newTotal / 50
                smoothUp(document.querySelector(".scroll-content"))
            }

            // 参数element为滚动区域
            function smoothDown (element) {
                if (distance < totalY) {
                    distance += step
                    element.scrollTop = distance
                    setTimeout(smoothDown.bind(this, element), 10)
                } else {
                    element.scrollTop = totalY
                }
            }

            // 参数element为滚动区域
            function smoothUp (element) {
                if (distance > totalY) {
                    distance -= step
                    element.scrollTop = distance
                    setTimeout(smoothUp.bind(this, element), 10)
                } else {
                    element.scrollTop = totalY
                }
            }
        },
        // 滚动条滚动
        onScroll (e) {
            let scrollItems = document.querySelectorAll(".scroll-item")
            for (let i = scrollItems.length - 1; i >= 0; i--) {
                // 判断滚动条滚动距离是否大于当前滚动项可滚动距离
                let judge = e.target.scrollTop >= scrollItems[i].offsetTop - scrollItems[0].offsetTop - 400
                if (judge) {
                    this.tabIndex = i.toString()
                    break
                }
            }
        },
        getHight () {
            this.contentStyleObj.height = (window.innerHeight - 190) + "px"
        }
    },
    created () {
        this.getHight();
        window.addEventListener("resize", this.getHight)
    },
    destroyed () {
        window.removeEventListener("resize", this.getHight)
    },
}
</script>
<style lang="less" scoped>

</style>

