package com.dippy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysUserAddress;
import com.dippy.mapper.SysUserAddressMapper;
import com.dippy.service.SysUserAddressService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service/*("sysUserAddressService")*/
public class SysUserAddressServiceImpl extends ServiceImpl<SysUserAddressMapper, SysUserAddress> implements SysUserAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserAddress> page = this.page(
                new Query<SysUserAddress>().getPage(params),
                new QueryWrapper<SysUserAddress>()
        );

        return new PageUtils(page);
    }

}
