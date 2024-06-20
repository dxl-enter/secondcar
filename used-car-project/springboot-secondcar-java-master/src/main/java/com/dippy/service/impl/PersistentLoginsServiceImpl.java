package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.PersistentLogins;
import com.dippy.mapper.PersistentLoginsMapper;
import com.dippy.service.PersistentLoginsService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("persistentLoginsService")
public class PersistentLoginsServiceImpl extends ServiceImpl<PersistentLoginsMapper, PersistentLogins> implements PersistentLoginsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PersistentLogins> page = this.page(
                new Query<PersistentLogins>().getPage(params),
                new QueryWrapper<PersistentLogins>()
        );

        return new PageUtils(page);
    }

}
