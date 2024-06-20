package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.Region;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
public interface RegionService extends IService<Region> {

    PageUtils queryPage(Map<String, Object> params);

    int initAddress(Integer addressLevel,Boolean cover);

    /**
     * 填写地址 人和车的
     * @param areaId 具体的区id
     * @param detailedAddress 详细地址
     * @param id id
     * @param type 人的地址还是车的
     * @return
     */
    boolean addressFill(Integer areaId, String detailedAddress, Integer id,String type);
}

