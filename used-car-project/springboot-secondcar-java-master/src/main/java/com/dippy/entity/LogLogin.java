package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志表(LogLogin)实体类
 *
 * @author dippy
 * @since 2021-04-09 23:52:13
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录日志实体类", description = "登录日志实体类")
public class LogLogin implements Serializable {
    private static final long serialVersionUID = -53672901491969855L;
    /**
     * 登录日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 退出时间
     */
    private Date logoutTime;
    /**
     * 登录地点
     */
    private String location;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 操作系统
     */
    private String userSystem;
    /**
     * 浏览器
     */
    private String userBrowser;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserSystem() {
        return userSystem;
    }

    public void setUserSystem(String userSystem) {
        this.userSystem = userSystem;
    }

    public String getUserBrowser() {
        return userBrowser;
    }

    public void setUserBrowser(String userBrowser) {
        this.userBrowser = userBrowser;
    }

    @Override
    public String toString() {
        return "LogLogin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                ", location='" + location + '\'' +
                ", ip='" + ip + '\'' +
                ", userSystem='" + userSystem + '\'' +
                ", userBrowser='" + userBrowser + '\'' +
                '}';
    }
}
