package com.dippy.vo;

import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;

import java.util.List;


/**
 * 采用mp方式查找我的全部车辆的话用这个VO，
 * 用mybatis的话直接用CarInfo。一对多的关系
 */

public class MyCarVo {

    private CarInfo carInfo;

    private List<CarPicture> carPictures;

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public List<CarPicture> getCarPictures() {
        return carPictures;
    }

    public void setCarPictures(List<CarPicture> carPictures) {
        this.carPictures = carPictures;
    }
}
