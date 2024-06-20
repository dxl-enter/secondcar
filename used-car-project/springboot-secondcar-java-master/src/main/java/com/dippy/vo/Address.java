package com.dippy.vo;

//地址
public class Address {
    /**
     * 省份id
     */
    private String province;
    /**
     * 城市id
     */
    private String city;
    /**
     * 区\县 id
     */
    private String area;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
