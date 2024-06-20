package com.dippy.es.vo;

public class CarGearBoxVo {

    /**
     * 变数箱类型
     */
    private String carGearBox;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

    public String getCarGearBox() {
        return carGearBox;
    }

    public void setCarGearBox(String carGearBox) {
        this.carGearBox = carGearBox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
