package com.dippy.es.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(value = "ES汽车信息聚合", description = "ES汽车信息聚合用于筛选")
public class CarAggVo {


    @ApiModelProperty(value = "品牌ID聚合结果")
    private List<CarBrandVo> carBrandVoList;

    @ApiModelProperty(value = "汽车颜色聚合结果")
    private List<CarColorVo> carColorVoList;

    @ApiModelProperty(value = "变数箱类型聚合结果")
    private List<CarGearBoxVo> carGearBoxVoList;

    @ApiModelProperty(value = "燃料类型聚合结果")
    private List<CarFuelTypeVo> carFuelTypeVoList;

    @ApiModelProperty(value = "汽车车系聚合结果")
    private List<CarSeriesVo> carSeriesVoList;

    @ApiModelProperty(value = "汽车类型聚合结果")
    private List<CarTypeVo> carTypeVoList;

    // @ApiModelProperty(value = "聚合信息涉及的标题")
    // private List<String> arrTitleList;


    public List<CarBrandVo> getCarBrandVoList() {
        return carBrandVoList;
    }

    public void setCarBrandVoList(List<CarBrandVo> carBrandVoList) {
        this.carBrandVoList = carBrandVoList;
    }

    public List<CarColorVo> getCarColorVoList() {
        return carColorVoList;
    }

    public void setCarColorVoList(List<CarColorVo> carColorVoList) {
        this.carColorVoList = carColorVoList;
    }

    public List<CarGearBoxVo> getCarGearBoxVoList() {
        return carGearBoxVoList;
    }

    public void setCarGearBoxVoList(List<CarGearBoxVo> carGearBoxVoList) {
        this.carGearBoxVoList = carGearBoxVoList;
    }

    public List<CarFuelTypeVo> getCarFuelTypeVoList() {
        return carFuelTypeVoList;
    }

    public void setCarFuelTypeVoList(List<CarFuelTypeVo> carFuelTypeVoList) {
        this.carFuelTypeVoList = carFuelTypeVoList;
    }

    public List<CarSeriesVo> getCarSeriesVoList() {
        return carSeriesVoList;
    }

    public void setCarSeriesVoList(List<CarSeriesVo> carSeriesVoList) {
        this.carSeriesVoList = carSeriesVoList;
    }

    public List<CarTypeVo> getCarTypeVoList() {
        return carTypeVoList;
    }

    public void setCarTypeVoList(List<CarTypeVo> carTypeVoList) {
        this.carTypeVoList = carTypeVoList;
    }

    @Override
    public String toString() {
        return "CarAggVo{" +
                "carBrandVoList=" + carBrandVoList +
                ", carColorVoList=" + carColorVoList +
                ", carGearBoxVoList=" + carGearBoxVoList +
                ", carFuelTypeVoList=" + carFuelTypeVoList +
                ", carSeriesVoList=" + carSeriesVoList +
                ", carTypeVoList=" + carTypeVoList +
                '}';
    }
}
