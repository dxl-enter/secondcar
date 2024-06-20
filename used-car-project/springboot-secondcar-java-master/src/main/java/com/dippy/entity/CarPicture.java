package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dippy
 * @since 2021-03-23
 */
@EqualsAndHashCode(callSuper = false)
public class CarPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 汽车图片id
     */
    @TableId(value = "car_picture_id", type = IdType.AUTO)
    private Integer carPictureId;

    /**
     * 汽车图片URL
     */
    private String carUrl;

    /**
     * 汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4 轮播
     */
    private Integer carPictureLocation;

    /**
     * 汽车id
     */
    private Integer carId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCarPictureId() {
        return carPictureId;
    }

    public void setCarPictureId(Integer carPictureId) {
        this.carPictureId = carPictureId;
    }

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

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
