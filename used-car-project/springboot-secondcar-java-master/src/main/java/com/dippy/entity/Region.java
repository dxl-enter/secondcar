package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-04-10
 */

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "地址", description = "地址")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域上级标识
     */
    private Integer pid;

    /**
     * 地名简称
     */
    private String sname;

    /**
     * 区域等级
     */
    private Integer level;

    /**
     * 区域编码
     */
    private String citycode;

    /**
     * 邮政编码
     */
    private String yzcode;

    /**
     * 组合名称
     */
    private String mername;

    /**
     * 经度
     */
    @TableField("Lng")
    private Float Lng;

    /**
     * 纬度
     */
    @TableField("Lat")
    private Float Lat;

    /**
     * 拼音
     */
    private String pinyin;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getYzcode() {
        return yzcode;
    }

    public void setYzcode(String yzcode) {
        this.yzcode = yzcode;
    }

    public String getMername() {
        return mername;
    }

    public void setMername(String mername) {
        this.mername = mername;
    }

    public Float getLng() {
        return Lng;
    }

    public void setLng(Float lng) {
        Lng = lng;
    }

    public Float getLat() {
        return Lat;
    }

    public void setLat(Float lat) {
        Lat = lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", sname='" + sname + '\'' +
                ", level=" + level +
                ", citycode='" + citycode + '\'' +
                ", yzcode='" + yzcode + '\'' +
                ", mername='" + mername + '\'' +
                ", Lng=" + Lng +
                ", Lat=" + Lat +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
