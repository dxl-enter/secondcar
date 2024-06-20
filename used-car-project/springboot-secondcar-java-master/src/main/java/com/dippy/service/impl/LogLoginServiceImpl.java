package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.LogLogin;
import com.dippy.mapper.LogLoginMapper;
import com.dippy.service.LogLoginService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("logLoginService")
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogLogin> page = this.page(
                new Query<LogLogin>().getPage(params),
                new QueryWrapper<LogLogin>()
        );

        return new PageUtils(page);
    }

}
