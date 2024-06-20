<template>
    <div>
        <!--过滤筛选组件-->
        <div class="demo">
            <div class="demo-warp">
                <div class="demo-flex" v-for="(v,k) in getList" :key="k">
                    <span class="demo-title">{{v.title}}</span>
                    <div class="demo-content">
                        <div class="demo-tab" :class="isShow ? 'demo-hide' : ''">
                            <span v-for="(val, key) in v.childer" :key="key" :class="{'demo-active': val.active}"
                                  @click="tabClick(val,key,k)">{{val.value}}</span>
                        </div>
                    </div>
                    <div class="demo-more" @click="isShow = !isShow" v-if="v.childer.length >= 14">更多</div>
                </div>
            </div>
        </div>
        <!--布局组件-->
    </div>
</template>


<!--
{
	"list": [{
			"title": "分类：",
			"childer": [{
					"value": "全部",
					"active": true
				},
				{
					"value": "漂浮素材",
					"active": false
				},
				{
					"value": "效果元素",
					"active": false
				},
				{
					"value": "卡通手绘",
					"active": false
				},
				{
					"value": "装饰图案",
					"active": false
				},
				{
					"value": "图标元素",
					"active": false
				},
				{
					"value": "促销标签",
					"active": false
				},
				{
					"value": "边框纹理",
					"active": false
				},
				{
					"value": "不规则图形",
					"active": false
				},
				{
					"value": "表情包213123",
					"active": false
				},
				{
					"value": "表情包2323",
					"active": false
				},
				{
					"value": "表情包1111",
					"active": false
				},
				{
					"value": "表情包3333",
					"active": false
				},
				{
					"value": "表情包444",
					"active": false
				}
			]
		},
		{
			"title": "格式：",
			"childer": [{
					"value": "全部",
					"active": true
				},
				{
					"value": "PSD",
					"active": false
				},
				{
					"value": "AI",
					"active": false
				},
				{
					"value": "EPS",
					"active": false
				}
			]
		},
		{
			"title": "排序：",
			"childer": [{
					"value": "推荐",
					"active": true
				},
				{
					"value": "昨日热门",
					"active": false
				},
				{
					"value": "最新上传",
					"active": false
				},
				{
					"value": "热门下载",
					"active": false
				},
				{
					"value": "热门收藏",
					"active": false
				}
			]
		}
	]
}



-->
<script>
    export default {
        name: "CarScreen",
        props: {
            getList: {
                type: Array,
                default: () => []
            }
        },
        data() {
            return {
                isShow: false,
                myDate: [],
            };
        },
        created() {

        },
        methods: {
            tabClick(data, key, k) {
                // 添加 active ==> true 显示 `active样式`
                this.getList[k].childer.map(item => {
                    item.active = false
                });
                this.getList[k].childer[key].active = true;

                // 选中的数据
                let newArray = [];
                this.getList.map(data => {
                    data.childer.map(item => {
                        if (item.active === true) {
                            newArray.push(item)
                        }
                    })
                })
                this.$emit('get-sel-data', newArray);
                this.$emit('set-time', 0, 1000, true)
            },

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
        flex-basis: 70px;
        margin-top: 5px;
    }

    .demo-content {
        display: flex;
        flex: 1;
    }

    .demo-tab {
        flex: 1;
        margin-right: 15px;
        height: 35px;
        overflow: hidden;
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

    /* 过滤列表:end */
</style>

<!--

2. 过滤筛选组件json ==> 路径： json/demo3.json
{
	"list": [{
			"title": "分类：",
			"childer": [{
					"value": "全部",
					"active": true
				},
				{
					"value": "漂浮素材",
					"active": false
				},
				{
					"value": "效果元素",
					"active": false
				},
				{
					"value": "卡通手绘",
					"active": false
				},
				{
					"value": "装饰图案",
					"active": false
				},
				{
					"value": "图标元素",
					"active": false
				},
				{
					"value": "促销标签",
					"active": false
				},
				{
					"value": "边框纹理",
					"active": false
				},
				{
					"value": "不规则图形",
					"active": false
				},
				{
					"value": "表情包213123",
					"active": false
				},
				{
					"value": "表情包2323",
					"active": false
				},
				{
					"value": "表情包1111",
					"active": false
				},
				{
					"value": "表情包3333",
					"active": false
				},
				{
					"value": "表情包444",
					"active": false
				}
			]
		},
		{
			"title": "格式：",
			"childer": [{
					"value": "全部",
					"active": true
				},
				{
					"value": "PSD",
					"active": false
				},
				{
					"value": "AI",
					"active": false
				},
				{
					"value": "EPS",
					"active": false
				}
			]
		},
		{
			"title": "排序：",
			"childer": [{
					"value": "推荐",
					"active": true
				},
				{
					"value": "昨日热门",
					"active": false
				},
				{
					"value": "最新上传",
					"active": false
				},
				{
					"value": "热门下载",
					"active": false
				},
				{
					"value": "热门收藏",
					"active": false
				}
			]
		}
	]
}

-->
