package com.dippy.vo;


public class CarBrandVo {

    /**
     * 汽车品牌id
     */
    private String carBrand;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

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

    public CarBrandVo(String carBrand, boolean active) {
        this.carBrand = carBrand;
        this.active = active;
    }
}
