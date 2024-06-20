package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SellerCar;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 * 卖家拥有的车辆
 *
 * @author dippy
 * @date 2021-04-21 13:21:37
 */
public interface SellerCarService extends IService<SellerCar> {

    PageUtils queryPage(Map<String, Object> params);
}

