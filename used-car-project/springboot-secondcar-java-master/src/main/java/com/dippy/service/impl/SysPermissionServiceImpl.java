package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysPermission;
import com.dippy.mapper.SysPermissionMapper;
import com.dippy.service.SysPermissionService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysPermissionService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPermission> page = this.page(
                new Query<SysPermission>().getPage(params),
                new QueryWrapper<SysPermission>()
        );

        return new PageUtils(page);
    }

}
