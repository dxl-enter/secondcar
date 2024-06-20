<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
                <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">查询</el-button>
                <!--<el-button v-if="isAuth('yueChi:carinfo:save')" type="primary" @click="addOrUpdateHandle()">新增-->
                <!--</el-button>-->
                <!--<el-button v-if="isAuth('yueChi:carinfo:delete')" type="danger" @click="deleteHandle()"-->
                <!--           :disabled="dataListSelections.length <= 0">批量删除-->
                <!--</el-button>-->
            </el-form-item>
        </el-form>
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading"
                @selection-change="selectionChangeHandle"
                style="width: 100%;">
            <el-table-column
                    type="selection"
                    header-align="center"
                    align="center"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="carId"
                    header-align="center"
                    align="center"
                    label="车辆id">
            </el-table-column>
            <el-table-column
                    prop="carBrand"
                    header-align="center"
                    align="center"
                    label="汽车品牌">
            </el-table-column>
            <el-table-column
                    prop="carSeries"
                    header-align="center"
                    align="center"
                    label="车系">
            </el-table-column>
            <el-table-column
                    prop="carType"
                    header-align="center"
                    align="center"
                    label="车型（suv、小型车。。。）">
            </el-table-column>
            <el-table-column
                    prop="carPrice"
                    header-align="center"
                    align="center"
                    label="价格">
            </el-table-column>
            <el-table-column
                    prop="carCount"
                    header-align="center"
                    align="center"
                    label="汽车数量">
            </el-table-column>
            <el-table-column
                    prop="carUserTime"
                    header-align="center"
                    align="center"
                    label="使用年限（单位/年）">
            </el-table-column>
            <el-table-column
                    prop="transferCount"
                    header-align="center"
                    align="center"
                    label="过户次数">
            </el-table-column>
            <el-table-column
                    prop="carCarMileage"
                    header-align="center"
                    align="center"
                    label="里程（万公里）">
            </el-table-column>
            <el-table-column
                    prop="carColor"
                    header-align="center"
                    align="center"
                    label="汽车颜色">
            </el-table-column>
            <el-table-column
                    prop="carPictureId"
                    header-align="center"
                    align="center"
                    label="汽车图片地址">
            </el-table-column>
            <el-table-column
                    prop="carGearBox"
                    header-align="center"
                    align="center"
                    label="变数箱（手动、自动、不限）">
            </el-table-column>
            <el-table-column
                    prop="carDisplacement"
                    header-align="center"
                    align="center"
                    label="排量">
            </el-table-column>
            <el-table-column
                    prop="produceTime"
                    header-align="center"
                    align="center"
                    label="汽车生产日期">
            </el-table-column>
            <el-table-column
                    prop="carConfiguration"
                    header-align="center"
                    align="center"
                    label="配置（天窗、GPS、真皮坐垫）">
            </el-table-column>
            <el-table-column
                    prop="carSeat"
                    header-align="center"
                    align="center"
                    label="座位数">
            </el-table-column>
            <el-table-column
                    prop="carFuelType"
                    header-align="center"
                    align="center"
                    label="燃料类型（柴油、机油、电力。。。）">
            </el-table-column>
            <el-table-column
                    prop="carQualityTime"
                    header-align="center"
                    align="center"
                    label="质保时间（单位/年）">
            </el-table-column>
            <el-table-column
                    prop="carRegionId"
                    header-align="center"
                    align="center"
                    label="国别">
            </el-table-column>
            <el-table-column
                    prop="carFrontTyre"
                    header-align="center"
                    align="center"
                    label="前轮胎尺寸">
            </el-table-column>
            <el-table-column
                    prop="rearTyre"
                    header-align="center"
                    align="center"
                    label="后轮胎尺寸">
            </el-table-column>
            <el-table-column
                    prop="carDescribe"
                    header-align="center"
                    align="center"
                    label="其他描述">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    header-align="center"
                    align="center"
                    label="创建时间">
            </el-table-column>
            <el-table-column
                    prop="updateTime"
                    header-align="center"
                    align="center"
                    label="更新时间">
            </el-table-column>
            <el-table-column
                    prop="carStatus"
                    header-align="center"
                    align="center"
                    label="汽车状态   0-已上架；1-未上架；2-已下架">
            </el-table-column>
            <el-table-column
                    prop="carTitle"
                    header-align="center"
                    align="center"
                    label="汽车标题">
            </el-table-column>
            <el-table-column
                    prop="userId"
                    header-align="center"
                    align="center"
                    label="用户id">
            </el-table-column>
            <el-table-column
                    prop="carPicture"
                    header-align="center"
                    align="center"
                    label="汽车主图">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    header-align="center"
                    align="center"
                    width="150"
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.carId)">修改</el-button>
                    <el-button type="text" size="small" @click="deleteHandle(scope.row.carId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="sizeChangeHandle"
                @current-change="currentChangeHandle"
                :current-page="pageIndex"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="pageSize"
                :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
</template>

<script>
    import AddOrUpdate from './carinfo-add-or-update'

    export default {
        name: "CarInfo",
        data() {
            return {
                dataForm: {
                    key: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false
            }
        },
        components: {
            AddOrUpdate
        },
        activated() {
            this.getDataList()
        },
        methods: {
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/yueChi/carinfo/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        'page': this.pageIndex,
                        'limit': this.pageSize,
                        'key': this.dataForm.key
                    })
                }).then(({data}) => {
                    if (data && data.code === 0) {
                        this.dataList = data.page.list
                        this.totalPage = data.page.totalCount
                    } else {
                        this.dataList = []
                        this.totalPage = 0
                    }
                    this.dataListLoading = false
                })
            },
            // 每页数
            sizeChangeHandle(val) {
                this.pageSize = val
                this.pageIndex = 1
                this.getDataList()
            },
            // 当前页
            currentChangeHandle(val) {
                this.pageIndex = val
                this.getDataList()
            },
            // 多选
            selectionChangeHandle(val) {
                this.dataListSelections = val
            },
            // 新增 / 修改
            addOrUpdateHandle(id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })
            },
            // 删除
            deleteHandle(id) {
                var ids = id ? [id] : this.dataListSelections.map(item => {
                    return item.carId
                })
                this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/yueChi/carinfo/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.getDataList()
                                }
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                })
            }
        }
    }
</script>
