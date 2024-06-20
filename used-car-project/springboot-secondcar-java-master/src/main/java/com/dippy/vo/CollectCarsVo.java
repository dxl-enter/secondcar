package com.dippy.vo;

public class CollectCarsVo {
    /**
     * 车辆id
     */

    private Integer carId;

    /**
     * 汽车品牌
     */
    private String carBrand;

    private Integer carUserTime;
    private Integer transferCount;
    private Double carCarMileage;
    private String carColor;

    private String carGearBox;
    private Double carDisplacement;
    private String carTitle;
    private String carPicture;

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

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public String getCarPicture() {
        return carPicture;
    }

    public void setCarPicture(String carPicture) {
        this.carPicture = carPicture;
    }
}
