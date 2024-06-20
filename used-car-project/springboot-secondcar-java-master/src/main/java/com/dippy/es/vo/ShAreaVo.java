package com.dippy.es.vo;

public class ShAreaVo {

    private Integer id;
    private String RegionName;

    private Integer oneLevel;

    private Integer twoLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public Integer getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(Integer oneLevel) {
        this.oneLevel = oneLevel;
    }

    public Integer getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(Integer twoLevel) {
        this.twoLevel = twoLevel;
    }
}
