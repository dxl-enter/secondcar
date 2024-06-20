package com.dippy.es.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


@Document(indexName = "region", createIndex = true)
public class RegionModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 区域主键
     */
    @Id
    private Integer id;

    /**
     * 区域名称
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String name;

    /**
     * 区域上级标识
     */
    @Field(type = FieldType.Integer)
    private Integer pid;

    /**
     * 地名简称
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String sname;

    /**
     * 区域等级
     */
    @Field(type = FieldType.Integer)
    private Integer level;

    /**
     * 区域编码
     */
    @Field(type = FieldType.Keyword)
    private String citycode;

    /**
     * 邮政编码
     */
    @Field(type = FieldType.Keyword)
    private String yzcode;

    /**
     * 组合名称
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String mername;

    /**
     * 经度
     */
    @Field(type = FieldType.Double)
    private Float Lng;

    /**
     * 纬度
     */
    @Field(type = FieldType.Double)
    private Float Lat;

    /**
     * 拼音
     */
    @Field(type = FieldType.Keyword)
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
        return "RegionModel{" +
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
