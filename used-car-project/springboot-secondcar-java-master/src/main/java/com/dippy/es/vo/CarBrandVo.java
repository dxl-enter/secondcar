package com.dippy.es.vo;


public class CarBrandVo {

    /**
     * 汽车ID
     */
    private Integer carId;

    /**
     * 汽车品牌
     */
    private String carBrand;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;


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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CarBrandVo{" +
                "carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", active=" + active +
                '}';
    }
}
