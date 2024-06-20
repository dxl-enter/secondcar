package com.dippy.vo;

import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;

import java.util.List;
import java.util.Map;

public class CarDetailVo {

    private CarInfo carInfo;

    // private List<CarPicture> carPictures;

    private Map<Integer, List<CarPicture>> carPicturesMap;

    // private List<Map<String, Object>> carAllPictures;

    private Map<Integer, List<String>> carPictures;

    private String name;

    private String phone;

    private Integer sex;

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public Map<Integer, List<CarPicture>> getCarPicturesMap() {
        return carPicturesMap;
    }

    public void setCarPicturesMap(Map<Integer, List<CarPicture>> carPicturesMap) {
        this.carPicturesMap = carPicturesMap;
    }

    public Map<Integer, List<String>> getCarPictures() {
        return carPictures;
    }

    public void setCarPictures(Map<Integer, List<String>> carPictures) {
        this.carPictures = carPictures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
