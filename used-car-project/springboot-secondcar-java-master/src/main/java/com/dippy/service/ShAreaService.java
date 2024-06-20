package com.dippy.service;

import com.dippy.entity.ShArea;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.es.vo.ShAreaVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dippy
 * @since 2021-04-16
 */
public interface ShAreaService extends IService<ShArea> {

    /**
     * 初始化详细地址到redis
     * @param addressLevel 地址级别
     * @param cover 是否覆盖
     * @return
     */
    int initAddress(Integer addressLevel, Boolean cover);

    /**
     * 根据id获取详细地址信息。
     * @param provinceId 省份id
     * @param cityId 城市id
     * @param areaId 区\县id
     * @return
     */
    List<ShAreaVo> getAddressByAllId(Integer provinceId, Integer cityId, Integer areaId);
}
