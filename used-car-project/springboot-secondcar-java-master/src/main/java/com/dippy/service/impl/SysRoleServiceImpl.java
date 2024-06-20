package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysRole;
import com.dippy.mapper.SysRoleMapper;
import com.dippy.service.SysRoleService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRole> page = this.page(
                new Query<SysRole>().getPage(params),
                new QueryWrapper<SysRole>()
        );

        return new PageUtils(page);
    }

}
