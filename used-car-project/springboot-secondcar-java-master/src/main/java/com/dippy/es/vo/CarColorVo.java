package com.dippy.es.vo;

public class CarColorVo {

    /**
     * 汽车颜色
     */
    private String carColor;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CarColorVo{" +
                "carColor='" + carColor + '\'' +
                ", active=" + active +
                '}';
    }
}
