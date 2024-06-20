package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SysPermission;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
public interface SysPermissionService extends IService<SysPermission> {

    PageUtils queryPage(Map<String, Object> params);
}

