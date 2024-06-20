package com.dippy.es.vo;

public class CarTypeVo {

    /**
     * 汽车类型
     */
    private String carType;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
