<template>
    <div class="screen">
        <!--v-for="(item_1, index_1) in carAggVoList"-->
        <dl class="clearfix" v-for="(item, index) in arrTitleList">
            <dt>
                {{item}}
            </dt>
            <dd>
                <div class="dd-top" v-for="(item2, index_2) in carAggVoList">
                    <span class="a-box" >
                        <a v-for="(item_3, index_3) in item2">
                            {{item_3}}
                        </a>
                    </span>
                </div>
            </dd>
        </dl>
    </div>
</template>

<script>
    export default {
        name: "TestScreen",
        data() {
            return {};
        },
        props: {
            carAggVoList: Object,
            arrTitleList: Array,
        },
        created() {
            const _this = this;
        },
        methods: {},
        computed: {},
        components: {},
    }
</script>

<style scoped>
    div {
        display: block;
    }

    .screen {
        width: 1198px;
        border: 1px solid #e6e6e6;
        border-bottom: none;
    }

    .screen dl {
        border-bottom: 1px solid #e6e6e6;
        min-height: 50px;
        position: relative;
    }

    dl {
        display: block;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
    }

    .clearfix:after {
        content: " ";
        display: block;
        clear: both;
        height: 0;
        visibility: hidden;
    }

    .screen dd {
        float: right;
        width: 1128px;
    }

    dd {
        display: block;
        margin-inline-start: 40px;
    }

    .dd-top {
        height: 20px;
        padding: 15px 0 15px 6px;
    }

    /*.dd-top .a-box {*/
    /*    float: left;*/
    /*    width: 940px;*/
    /*    height: 20px;*/
    /*    overflow: hidden;*/
    /*}*/

    .dd-top a {
        float: left;
        height: 20px;
        font-size: 14px;
        line-height: 20px;
        padding: 0 6px;
        color: #495056;
        margin: 0 8px;
    }

    a {
        text-decoration: none !important;
        color: #24d;
    }

    a:-webkit-any-link {
        color: -webkit-link;
        cursor: pointer;
        text-decoration: underline;
    }

    .screen dt {
        float: left;
        width: 70px;
        height: 100%;
        position: absolute;
        line-height: 50px;
        font-size: 14px;
        color: #7a838d;
        text-align: center;
        background: #fafafa;
    }

    dt {
        display: block;
    }
</style>
