package com.dippy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SysUserCollectCars;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author dippy
 * @date 2021-05-03 23:56:29
 */
public interface SysUserCollectCarsService extends IService<SysUserCollectCars> {

    PageUtils queryPage(Map<String, Object> params);
}

