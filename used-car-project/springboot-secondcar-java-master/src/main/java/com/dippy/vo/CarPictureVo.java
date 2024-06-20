package com.dippy.vo;

public class CarPictureVo {

    /**
     * 汽车图片URL
     */
    private String carUrl;

    /**
     * 汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4轮播
     */
    private Integer carPictureLocation;

    public String getCarUrl() {
        return carUrl;
    }

    public void setCarUrl(String carUrl) {
        this.carUrl = carUrl;
    }

    public Integer getCarPictureLocation() {
        return carPictureLocation;
    }

    public void setCarPictureLocation(Integer carPictureLocation) {
        this.carPictureLocation = carPictureLocation;
    }
}
