package com.dippy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 卖家拥有的车辆
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-21 13:21:37
 */
@TableName("seller_car")
@ApiModel("卖家拥有的车辆")
public class SellerCar implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 卖家id
	 */
	private Integer userId;
	/**
	 * 车辆id
	 */
	private Integer carId;
	/**
	 * 汽车状态   0-已上架；1-未上架；2-已下架
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SellerCar{" +
				"userId=" + userId +
				", carId=" + carId +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
