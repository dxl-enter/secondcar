package com.dippy.es.vo;

public class CarSeriesVo {

    /**
     * 汽车系列
     */
    private String carSeries;
    /**
     * 是否激活、第一个是全部--默认激活即true
     */
    private boolean active;

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
