package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.entity.CarInfo;
import com.dippy.vo.CarBrandVo;
import com.dippy.vo.CarDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dippy
 * @since 2021-02-08
 */
public interface CarInfoMapper extends BaseMapper<CarInfo> {

    /**
     * 查找所有汽车品牌
     */
    @Select("SELECT distinct(car_brand) FROM car_info")
    List<CarBrandVo> getAllCarBrand();

    IPage<CarInfo> findMySellCarByPage(Page<CarInfo> page, @Param("userId") Integer userId);

    CarDetailVo getCarDetailByCarId(@Param("carId") Integer carId);
}
