package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.LogLogin;
import com.dippy.util.PageUtils;

import java.util.Map;

/**
 * 登录日志表
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
public interface LogLoginService extends IService<LogLogin> {

    PageUtils queryPage(Map<String, Object> params);
}

