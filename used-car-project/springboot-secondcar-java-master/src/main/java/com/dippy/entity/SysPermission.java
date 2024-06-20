package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "权限", description = "权限")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    /**
     * 父权限id
     */
    @ApiModelProperty(value = "父权限id")
    @TableField("parent_id")
    private Integer parent_id;

    /**
     * 权限名
     */
    @ApiModelProperty(value = "权限名")
    private String name;

    /**
     * 权限的地址
     */
    @ApiModelProperty(value = "权限的地址")
    private String url;

    /**
     * 一级还是二级菜单
     */
    @ApiModelProperty(value = "一级还是二级菜单")
    private Integer type;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private String permission;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
                "permissionId=" + permissionId +
                ", parent_id=" + parent_id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                '}';
    }
}
