package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dippy
 * @since 2021-02-03
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户对应的权限对应表", description = "用户对应的权限对应表")
public class SysRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysRoleUser{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
