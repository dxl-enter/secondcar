package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author dippy
 * @since 2021-02-08
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ES查询条件", description = "ES查询条件")
public class CarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id")
    @TableId(value = "car_id", type = IdType.AUTO)
    private Integer carId;

    /**
     * 汽车品牌
     */
    @ApiModelProperty(value = "汽车品牌")
    private String carBrand;

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
     * 汽车数量
     */
    @ApiModelProperty(value = "汽车数量")
    private Integer carCount;

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
    private Double carCarMileage;

    /**
     * 汽车颜色
     */
    @ApiModelProperty(value = "汽车颜色")
    private String carColor;

    /**
     * 汽车图片地址
     */
    @ApiModelProperty(value = "汽车图片地址")
    private Integer carPictureId;

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
     * 其他描述
     */
    @ApiModelProperty(value = "其他描述")
    private String carDescribe;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 汽车状态   0-已上架；1-未上架；2-已下架
     */
    @ApiModelProperty(value = "汽车状态")
    private Integer carStatus;

    /**
     * 汽车标题
     */
    @ApiModelProperty(value = "汽车标题")
    private String carTitle;

    /**
     * 汽车全部图片
     */
    @ApiModelProperty(value = "汽车全部图片")
    // 不包含该字段
    @TableField(exist = false) // 不是表字段
    private List<CarPicture> carPictures;

    /**
     * 该车属于这个id的用户
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;


    /**
     * 汽车主图
     */
    @ApiModelProperty(value = "汽车主图")
    private String carPicture;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
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

    public Double getCarCarMileage() {
        return carCarMileage;
    }

    public void setCarCarMileage(Double carCarMileage) {
        this.carCarMileage = carCarMileage;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarPictureId() {
        return carPictureId;
    }

    public void setCarPictureId(Integer carPictureId) {
        this.carPictureId = carPictureId;
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

    public String getCarDescribe() {
        return carDescribe;
    }

    public void setCarDescribe(String carDescribe) {
        this.carDescribe = carDescribe;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public List<CarPicture> getCarPictures() {
        return carPictures;
    }

    public void setCarPictures(List<CarPicture> carPictures) {
        this.carPictures = carPictures;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCarPicture() {
        return carPicture;
    }

    public void setCarPicture(String carPicture) {
        this.carPicture = carPicture;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", carSeries='" + carSeries + '\'' +
                ", carType='" + carType + '\'' +
                ", carPrice=" + carPrice +
                ", carCount=" + carCount +
                ", carUserTime=" + carUserTime +
                ", transferCount=" + transferCount +
                ", carCarMileage=" + carCarMileage +
                ", carColor='" + carColor + '\'' +
                ", carPictureId=" + carPictureId +
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
                ", carDescribe='" + carDescribe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", carStatus=" + carStatus +
                ", carTitle='" + carTitle + '\'' +
                ", carPictures=" + carPictures +
                ", userId=" + userId +
                ", carPicture='" + carPicture + '\'' +
                '}';
    }
}
