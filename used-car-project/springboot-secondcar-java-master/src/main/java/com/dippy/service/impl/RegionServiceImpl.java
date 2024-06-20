package com.dippy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.common.handle.MyException;
import com.dippy.common.result.ResultCode;
import com.dippy.entity.Region;
import com.dippy.mapper.RegionMapper;
import com.dippy.service.RegionService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import com.dippy.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("regionService")
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Region> page = this.page(
                new Query<Region>().getPage(params),
                new QueryWrapper<Region>()
        );

        return new PageUtils(page);
    }

    @Override
    public int initAddress(Integer addressLevel, Boolean cover) {

        // 不需要覆盖
        if (!cover) return -1;

        // 初始化全部
        if (addressLevel == -1) {
            List<Region> provinces = regionMapper.selectList(null);

            // redisUtil.sSet(region, provinces);
            for (Region province1 : provinces) {
                region = region + ":" +province1.getId();
                redisUtil.hset(region, "id", province1.getId());
                redisUtil.hset(region, "name", province1.getSname());
                redisUtil.hset(region, "pid", province1.getPid());
                redisUtil.hset(region, "sname", province1.getSname());
                redisUtil.hset(region, "level", province1.getLevel());
                redisUtil.hset(region, "citycode", province1.getCitycode());
                redisUtil.hset(region, "yzcode", province1.getYzcode());
                redisUtil.hset(region, "mername", province1.getMername());
                redisUtil.hset(region, "yzcode", province1.getYzcode());
                redisUtil.hset(region, "Lng", province1.getLng());
                redisUtil.hset(region, "Lat", province1.getLat());
                region = "region";
            }

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

    @Override
    public boolean addressFill(Integer areaId, String detailedAddress, Integer id, String type) {

        if (ObjectUtil.isNull(areaId)) {
            throw new MyException(ResultCode.EXCEPTION_NO_ADDRESS.getCode(), ResultCode.EXCEPTION_NO_ADDRESS.getMessage());
        }


        return false;
    }

    private int getaLong(Integer levelId, String levelName, Boolean cover) {
        // boolean hasAddressKey = redisUtil.hasKey(levelName);
        List<String> list = new LinkedList<>();
        if (cover) {
            List<Region> provinces = regionMapper.selectList(new QueryWrapper<Region>().eq("level", levelId).select("sname"));
            for (Region region : provinces) {
                list.add(region.getSname());
            }

            int amount = list.size();
            redisUtil.sSet(levelName, list);
            log.info("缓存了{}个省份,对应的键为{}", amount, levelName);
            return amount;
        }
        return -1;
    }

}
