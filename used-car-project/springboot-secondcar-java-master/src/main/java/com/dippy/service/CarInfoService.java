package com.dippy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.CarInfo;
import com.dippy.es.model.CarInfoModel;
import com.dippy.vo.CarBrandVo;
import com.dippy.vo.CarDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dippy
 * @since 2021-02-08
 */
public interface CarInfoService extends IService<CarInfo> {

    /**
     * 查找所有汽车品牌
     * @return
     */
    List<CarBrandVo> getAllCarBrand();

    IPage<CarInfo> getAllCarInfoByPage();

    CarInfo selectOneCarInfo(QueryWrapper<CarInfo> wrapper);

    /**
     * 汽车上架
     * @param carId
     * @return
     */
    CarInfoModel up(Integer carId);

    List<CarInfo> getAllCarInfo();



    /**
     * 分页查询我的全部车辆
     * @param page
     * @param userId
     * @return
     */
    IPage<CarInfo> findMySellCarByPage(Page<CarInfo> page, Integer userId);

    /**
     * 根据Id查找汽车详细信息。包括卖家、汽车图片
     * @param carId
     * @return
     */
    CarDetailVo getCarDetailByCarId(Integer carId);
}
