package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dippy.entity.SellerCar;
import org.apache.ibatis.annotations.Mapper;

/**
 * 卖家拥有的车辆
 *
 * @author dippy
 * @date 2021-04-21 13:21:37
 */
@Mapper
public interface SellerCarMapper extends BaseMapper<SellerCar> {

}
