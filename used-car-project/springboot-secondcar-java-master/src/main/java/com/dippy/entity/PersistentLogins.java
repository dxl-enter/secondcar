package com.dippy.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-04-10
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录日志记住我", description = "登录日志记住我")
public class PersistentLogins implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录的用户名
     */

    private String username;

    /**
     * 记住我主键
     */
    private String series;

    /**
     * token
     */
    private String token;

    /**
     * 最后一次使用时间
     */
    private LocalDateTime lastUsed;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public String toString() {
        return "PersistentLogins{" +
                "username='" + username + '\'' +
                ", series='" + series + '\'' +
                ", token='" + token + '\'' +
                ", lastUsed=" + lastUsed +
                '}';
    }
}
