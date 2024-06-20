package com.dippy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysUserCollectCars;
import com.dippy.mapper.SysUserCollectCarMapper;
import com.dippy.service.SysUserCollectCarsService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service/*("sysUserCollectCarsService")*/
public class SysUserCollectCarsServiceImpl extends ServiceImpl<SysUserCollectCarMapper, SysUserCollectCars> implements SysUserCollectCarsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserCollectCars> page = this.page(
                new Query<SysUserCollectCars>().getPage(params),
                new QueryWrapper<SysUserCollectCars>()
        );

        return new PageUtils(page);
    }

}
