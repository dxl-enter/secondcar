package com.dippy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SysUserChatMessage;
import com.dippy.mapper.SysUserChatMessageMapper;
import com.dippy.service.SysUserChatMessageService;
import com.dippy.vo.ChatListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dippy
 * @since 2021-05-05
 */
@Service
public class SysUserChatMessageServiceImpl extends ServiceImpl<SysUserChatMessageMapper, SysUserChatMessage> implements SysUserChatMessageService {

    @Autowired
    private SysUserChatMessageMapper sysUserChatMessageMapper;


    @Override
    public List<ChatListVo> getChatListByUserId(Integer userId) {
        List<ChatListVo> data = sysUserChatMessageMapper.getChatListByUserId(userId);

        return data;
    }
}
