package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-19 17:03:13
 */
@TableName("sys_user_address")
@ApiModel("用户地址列表")
public class SysUserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户地址id
	 */
	@TableId
	private Integer sysUserAddressId;
	/**
	 * 省份id
	 */
	private Integer provinceId;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 区\县 id
	 */
	private Integer areaId;
	/**
	 * 详细地址
	 */
	private String detailed;
	/**
	 *
	 */
	private Integer isDefault;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getSysUserAddressId() {
		return sysUserAddressId;
	}

	public void setSysUserAddressId(Integer sysUserAddressId) {
		this.sysUserAddressId = sysUserAddressId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getDetailed() {
		return detailed;
	}

	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SysUserAddress{" +
				"sysUserAddressId=" + sysUserAddressId +
				", provinceId=" + provinceId +
				", cityId=" + cityId +
				", areaId=" + areaId +
				", detailed='" + detailed + '\'' +
				", isDefault=" + isDefault +
				", remark='" + remark + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
