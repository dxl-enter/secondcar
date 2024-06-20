package com.dippy.es.vo;

public class CarFuelTypeVo {

    /**
     * 燃料类型
     */
    private String carFuelType;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

    public String getCarFuelType() {
        return carFuelType;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
