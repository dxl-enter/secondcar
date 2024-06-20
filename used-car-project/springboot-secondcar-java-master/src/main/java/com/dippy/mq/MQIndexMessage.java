package com.dippy.mq;

import java.io.Serializable;


/**
 * carInfo mq model
 */
public class MQIndexMessage implements Serializable {

    private static final long serialVersionUID = 324343223525L;

    // 操作的车辆id
    private Integer carId;

    // 操作类型
    private String type;

    // 数据
    private Object loginUser;


    // type
    public final static String CREATE_OR_UPDATE = "create_update";
    public final static String REMOVE = "remove";

    /*
     更新用户信息，头像，等
     */
    public final static String SYS_USER_INFO_UPDATE = "sys_user_info_update";

    // 用户收藏的汽车
    public final static String SYS_USER_COLLECT_CARS = "sys_user_collect_cars";


    public MQIndexMessage(Integer carId, String type) {
        this.carId = carId;
        this.type = type;
    }

    public MQIndexMessage(Integer carId, String type, Object loginUser) {
        this.carId = carId;
        this.type = type;
        this.loginUser = loginUser;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Object loginUser) {
        this.loginUser = loginUser;
    }

    public static String getCreateOrUpdate() {
        return CREATE_OR_UPDATE;
    }

    public static String getREMOVE() {
        return REMOVE;
    }

    public static String getSysUserInfoUpdate() {
        return SYS_USER_INFO_UPDATE;
    }

    public static String getSysUserCollectCars() {
        return SYS_USER_COLLECT_CARS;
    }

    @Override
    public String toString() {
        return "MQIndexMessage{" +
                "carId=" + carId +
                ", type='" + type + '\'' +
                ", loginUser=" + loginUser +
                '}';
    }
}
