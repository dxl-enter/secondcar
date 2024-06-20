<template>
    <div id="list">
        <ul>
            <!--<li v-for="item in sessions" :class="{ active: item.id === currentSession }"
                v-on:click="changeCurrentSession(item.id)">
                &lt;!&ndash;   :class="[item.id === currentSession ? 'active':'']" &ndash;&gt;
                <img class="avatar" :src="item.user.img" :alt="item.user.name">
                <p class="name">{{item.user.name}}</p>
            </li>-->
            <li v-for="item in chatList"
                :class="{ active: currentSession ? item.fromId === currentSession.toId:false }"
                v-on:click="changeCurrentSession(item)">
                {{item}}
                <!--   :class="[item.id === currentSession ? 'active':'']" -->
                <!--别人的图片、姓名信息-->
                <img class="avatar" :src="item.avatar" :alt="item.toName">
                <p class="name">{{item.toName}}</p>
            </li>
        </ul>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: 'list',
        data() {
            return {}
        },
        computed: mapState([
            // 'sessions',
            //聊天列表
            'chatList',
            'currentSession'
        ]),

        methods: {
            changeCurrentSession: function (currentSession) {
                this.$store.commit('changeCurrentSession', currentSession)
                console.log("currentSession ", this.currentSession)
            }
        }
    }
</script>

<style lang="scss" scoped>
    #list {
        ul {
            padding-left: 0px;
        }

        li {
            padding: 12px 15px;
            border-bottom: 1px solid #292C33;
            cursor: pointer;

            &:hover {
                background-color: rgba(255, 255, 255, 0.03);
            }
        }

        li.active { /*注意这个是.不是冒号:*/
            background-color: rgba(255, 255, 255, 0.1);
        }

        .avatar {
            border-radius: 2px;
            width: 30px;
            height: 30px;
            vertical-align: middle;
        }

        .name {
            display: inline-block;
            margin-left: 15px;
        }
    }
</style>
