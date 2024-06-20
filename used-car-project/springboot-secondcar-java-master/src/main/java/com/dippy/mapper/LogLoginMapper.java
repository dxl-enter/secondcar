package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dippy.entity.LogLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志表
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {

}
