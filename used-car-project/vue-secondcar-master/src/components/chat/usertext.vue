<template>
    <div id="uesrtext">
        <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: 'uesrtext',
        data() {
            return {
                content: ''
            }
        },
        methods: {
            addMessage(e) {
                if (e.ctrlKey && e.keyCode === 13 && this.content.length) {
                    // // 发送消息
                    // this.$store.commit('addMessage', this.content);
                    this.content = '';

                    let msgObj = new Object();

                    /*msgObj.to = 'naqiao'
                    msgObj.content = this.content*/
                    msgObj.toName = this.currentSession.name;
                    msgObj.content = this.content;
                    console.log(JSON.stringify((msgObj)));
                    this.$store.state.stomp.send('/ws/chat', {}, JSON.stringify((msgObj)));
                    this.$store.commit('addMessage', msgObj);
                    this.content = '';
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    #uesrtext {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 30%;
        border-top: solid 1px #DDD;

        > textarea {
            padding: 10px;
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
        }
    }
</style>
