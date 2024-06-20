package com.dippy.es.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;


@ApiModel(value = "ES查询条件", description = "ES查询条件")
public class SearchParamVo {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id")
    private Integer carId;


    /**
     * 汽车品牌
     */
    @ApiModelProperty(value = "汽车品牌")
    private String carBrand;

    // @ApiModelProperty(value = "汽车品牌多选")
    // private List<Integer> carBrandId;

    /**
     * 车系
     */
    @ApiModelProperty(value = "车系")
    private String carSeries;

    /**
     * 车型（suv、小型车。。。）
     */
    @ApiModelProperty(value = "车型")
    private String carType;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double carPrice;

    /**
     * 使用年限（单位/年）
     */
    @ApiModelProperty(value = "使用年限")
    private Integer carUserTime;

    /**
     * 过户次数
     */
    @ApiModelProperty(value = "过户次数")
    private Integer transferCount;

    /**
     * 里程（万公里）
     */
    @ApiModelProperty(value = "里程")
    private Integer carCarMileage;

    /**
     * 汽车颜色
     */
    @ApiModelProperty(value = "汽车颜色")
    private String carColor;

    /**
     * 变数箱（手动、自动、不限）
     */
    @ApiModelProperty(value = "变数箱")
    private String carGearBox;

    /**
     * 排量
     */
    @ApiModelProperty(value = "排量")
    private Double carDisplacement;

    /**
     * 汽车生产日期
     */
    @ApiModelProperty(value = "汽车生产日期")
    private LocalDate produceTime;

    /**
     * 配置（天窗、GPS、真皮坐垫）
     */
    @ApiModelProperty(value = "配置")
    private String carConfiguration;

    /**
     * 座位数
     */
    @ApiModelProperty(value = "座位数")
    private Integer carSeat;

    /**
     * 燃料类型（柴油、机油、电力。。。）
     */
    @ApiModelProperty(value = "燃料类型")
    private String carFuelType;

    /**
     * 质保时间（单位/年）
     */
    @ApiModelProperty(value = "质保时间")
    private Integer carQualityTime;

    /**
     * 国别
     */
    @ApiModelProperty(value = "国别")
    private Integer carRegionId;

    /**
     * 前轮胎尺寸
     */
    @ApiModelProperty(value = "前轮胎尺寸")
    private String carFrontTyre;

    /**
     * 后轮胎尺寸
     */
    @ApiModelProperty(value = "后轮胎尺寸")
    private String rearTyre;


    /**
     * 排序条件
     * <p>
     * &sort=hotScore_asc/desc
     */
    @ApiModelProperty(value = "排序条件")
    private String sort;

    /**
     * 支持分页
     */
    @ApiModelProperty(value = "第几页，默认第一页")
    private Integer currPage = 1;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private Integer totalPage;

    /**
     * 每页显示几个
     */
    @ApiModelProperty(value = "每页显示条数，默认50条")
    private Integer pageSize = 10; //

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Long totalCount;


    /**
     * 请求参数部分
     */
    @ApiModelProperty(value = "请求参数部分")
    private String queryString;


    @ApiModelProperty(value = "价格最小值，默认0.0")
    private Double carPriceMin = 0.0;// 默认最小值

    @ApiModelProperty(value = "价格最大值")
    private Double carPriceMax;


    /**
     * 汽车标题
     */
    @ApiModelProperty(value = "汽车标题")
    private String carTitle;


    @ApiModelProperty(value = "汽车状态   0-已上架；1-未上架；2-已下架")
    private Integer carStatus;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Double carPrice) {
        this.carPrice = carPrice;
    }

    public Integer getCarUserTime() {
        return carUserTime;
    }

    public void setCarUserTime(Integer carUserTime) {
        this.carUserTime = carUserTime;
    }

    public Integer getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(Integer transferCount) {
        this.transferCount = transferCount;
    }

    public Integer getCarCarMileage() {
        return carCarMileage;
    }

    public void setCarCarMileage(Integer carCarMileage) {
        this.carCarMileage = carCarMileage;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarGearBox() {
        return carGearBox;
    }

    public void setCarGearBox(String carGearBox) {
        this.carGearBox = carGearBox;
    }

    public Double getCarDisplacement() {
        return carDisplacement;
    }

    public void setCarDisplacement(Double carDisplacement) {
        this.carDisplacement = carDisplacement;
    }

    public LocalDate getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(LocalDate produceTime) {
        this.produceTime = produceTime;
    }

    public String getCarConfiguration() {
        return carConfiguration;
    }

    public void setCarConfiguration(String carConfiguration) {
        this.carConfiguration = carConfiguration;
    }

    public Integer getCarSeat() {
        return carSeat;
    }

    public void setCarSeat(Integer carSeat) {
        this.carSeat = carSeat;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public Integer getCarQualityTime() {
        return carQualityTime;
    }

    public void setCarQualityTime(Integer carQualityTime) {
        this.carQualityTime = carQualityTime;
    }

    public Integer getCarRegionId() {
        return carRegionId;
    }

    public void setCarRegionId(Integer carRegionId) {
        this.carRegionId = carRegionId;
    }

    public String getCarFrontTyre() {
        return carFrontTyre;
    }

    public void setCarFrontTyre(String carFrontTyre) {
        this.carFrontTyre = carFrontTyre;
    }

    public String getRearTyre() {
        return rearTyre;
    }

    public void setRearTyre(String rearTyre) {
        this.rearTyre = rearTyre;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Double getCarPriceMin() {
        return carPriceMin;
    }

    public void setCarPriceMin(Double carPriceMin) {
        this.carPriceMin = carPriceMin;
    }

    public Double getCarPriceMax() {
        return carPriceMax;
    }

    public void setCarPriceMax(Double carPriceMax) {
        this.carPriceMax = carPriceMax;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "SearchParamVo{" +
                "keyword='" + keyword + '\'' +
                ", carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", carSeries='" + carSeries + '\'' +
                ", carType='" + carType + '\'' +
                ", carPrice=" + carPrice +
                ", carUserTime=" + carUserTime +
                ", transferCount=" + transferCount +
                ", carCarMileage=" + carCarMileage +
                ", carColor='" + carColor + '\'' +
                ", carGearBox='" + carGearBox + '\'' +
                ", carDisplacement=" + carDisplacement +
                ", produceTime=" + produceTime +
                ", carConfiguration='" + carConfiguration + '\'' +
                ", carSeat=" + carSeat +
                ", carFuelType='" + carFuelType + '\'' +
                ", carQualityTime=" + carQualityTime +
                ", carRegionId=" + carRegionId +
                ", carFrontTyre='" + carFrontTyre + '\'' +
                ", rearTyre='" + rearTyre + '\'' +
                ", sort='" + sort + '\'' +
                ", currPage=" + currPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", queryString='" + queryString + '\'' +
                ", carPriceMin=" + carPriceMin +
                ", carPriceMax=" + carPriceMax +
                ", carTitle='" + carTitle + '\'' +
                ", carStatus=" + carStatus +
                '}';
    }
}
