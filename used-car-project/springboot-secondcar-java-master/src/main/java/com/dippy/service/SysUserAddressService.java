package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SysUserAddress;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author dippy
 * @date 2021-04-19 17:03:13
 */
public interface SysUserAddressService extends IService<SysUserAddress> {

    PageUtils queryPage(Map<String, Object> params);
}

