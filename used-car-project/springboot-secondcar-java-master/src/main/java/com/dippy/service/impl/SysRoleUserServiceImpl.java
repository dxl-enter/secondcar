package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysRoleUser;
import com.dippy.mapper.SysRoleUserMapper;
import com.dippy.service.SysRoleUserService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("sysRoleUserService")
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleUser> page = this.page(
                new Query<SysRoleUser>().getPage(params),
                new QueryWrapper<SysRoleUser>()
        );

        return new PageUtils(page);
    }

}
