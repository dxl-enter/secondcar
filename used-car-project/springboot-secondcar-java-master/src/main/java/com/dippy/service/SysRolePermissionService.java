package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SysRolePermission;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    PageUtils queryPage(Map<String, Object> params);
}

