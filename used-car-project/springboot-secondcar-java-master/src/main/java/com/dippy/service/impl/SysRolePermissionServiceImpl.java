package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysRolePermission;
import com.dippy.mapper.SysRolePermissionMapper;
import com.dippy.service.SysRolePermissionService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRolePermission> page = this.page(
                new Query<SysRolePermission>().getPage(params),
                new QueryWrapper<SysRolePermission>()
        );

        return new PageUtils(page);
    }

}
