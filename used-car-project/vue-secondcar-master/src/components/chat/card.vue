<template>
    <div id="card">
        <header>
            <img class="avatar" v-bind:src="user.img" v-bind:alt="user.name">
            <p class="name">{{user.name}}</p>
        </header>
        <footer>
            <input class="search" type="text" v-model="$store.state.filterKey" placeholder="search user...">
        </footer>
    </div>
</template>

<script>
    import bus from "../../utils/bus";

    export default {
        name: 'card',
        data() {
            return {
                //当前登录用户
                user: {
                    name: 'Li-yiwei',
                    img: 'http://qrg5og3v4.hn-bkt.clouddn.com/%E9%BB%98%E8%AE%A4%E7%94%A8%E6%88%B7%E5%A4%B4%E5%83%8F.png'
                }
            }
        },
        created() {
            this.init();
        },
        methods: {
            init() {
                this.user.name = this.$store.getters.getUser.name;
                this.user.img = this.$store.getters.getUser.avatar;
            }
        }
    }
</script>

<style lang="scss" scoped>
    #card {
        padding: 12px;

        .avatar {
            width: 40px;
            height: 40px;
            vertical-align: middle; /*这个是图片和文字居中对齐*/
        }

        .name {
            display: inline-block;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 16px;
        }

        .search {
            background: #26292E;
            height: 30px;
            line-height: 30px;
            padding: 0 10px;
            border: 1px solid #3a3a3a;
            border-radius: 4px;
            outline: none; /*鼠标点击后不会出现蓝色边框*/
            color: #FFF;
        }
    }
</style>
