package com.dippy.service.impl;

import com.dippy.entity.UserInfo;
import com.dippy.mapper.UserInfoMapper;
import com.dippy.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  用户
 *      服务实现类
 * </p>
 *
 * @author dippy
 * @since 2020-12-05
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
