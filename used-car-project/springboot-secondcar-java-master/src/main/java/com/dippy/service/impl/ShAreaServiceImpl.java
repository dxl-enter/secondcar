package com.dippy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.ShArea;
import com.dippy.es.vo.ShAreaVo;
import com.dippy.mapper.ShAreaMapper;
import com.dippy.service.ShAreaService;
import com.dippy.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dippy
 * @since 2021-04-16
 */
@Service
@Slf4j
public class ShAreaServiceImpl extends ServiceImpl<ShAreaMapper, ShArea> implements ShAreaService {

    @Autowired
    private ShAreaMapper shAreaMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${address.region}")
    private String region;// 全部详细信息

    @Value("${address.province}")
    private String province;// 省

    @Value("${address.city}")
    private String city;// 市

    @Value("${address.area}")
    private String area;// 区/县


    /**
     * 初始化详细地址到redis
     * @param addressLevel 地址级别
     * @param cover 是否覆盖
     * @return
     */
    @Override
    public int initAddress(Integer addressLevel, Boolean cover) {


        // 不需要覆盖
        if (!cover) return -1;

        // 初始化全部
        if (addressLevel == -1) {
            List<ShArea> provinces = shAreaMapper.selectList(null);
            redisUtil.sSet(region, provinces);
            log.info("缓存了{}个省份,对应的键为{}", provinces.size(), region);
            return provinces.size();
        }

        // 初始化省份
        if (addressLevel == 1) {
            // 查redis中有没有、没有则先从数据库查
            int amount = getaLong(addressLevel, province, cover);
            if (amount != -1) return amount;
        }

        // 初始化城市
        if (addressLevel == 2) {
            // 查redis中有没有、没有则先从数据库查
            int amount = getaLong(addressLevel, city, cover);
            if (amount != -1) return amount;
        }
        // 初始化区\县
        if (addressLevel == 3) {
            // 查redis中有没有、没有则先从数据库查
            int amount = getaLong(addressLevel, area, cover);
            if (amount != -1) return amount;
        }

        // 不用缓存了
        return -1;
    }

    /**
     * 根据id获取详细地址信息。
     * @param provinceId 省份id
     * @param cityId 城市id
     * @param areaId 区\县id
     * @return
     */
    @Override
    public List<ShAreaVo> getAddressByAllId(Integer provinceId, Integer cityId, Integer areaId) {

        if (ObjectUtil.isNotNull(provinceId)){

        }


        return null;
    }

    /**
     * 初始化地址的辅助函数
     * @param levelId
     * @param levelName
     * @param cover
     * @return
     */
    private int getaLong(Integer levelId, String levelName, Boolean cover) {
        // boolean hasAddressKey = redisUtil.hasKey(levelName);
        List<ShAreaVo> list = new LinkedList<>();
        if (cover) {
            List<ShArea> provinces = shAreaMapper.selectList(new QueryWrapper<ShArea>().eq("level", levelId).select("name"));
            for (ShArea region : provinces) {
                // list.add(region.getSname());
            }

            int amount = list.size();
            redisUtil.sSet(levelName, list);
            log.info("缓存了{}个省份,对应的键为{}", amount, levelName);
            return amount;
        }
        return -1;
    }
}
