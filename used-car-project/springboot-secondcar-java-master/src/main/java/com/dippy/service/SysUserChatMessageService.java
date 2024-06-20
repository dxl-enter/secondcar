package com.dippy.service;

import com.dippy.entity.SysUserChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.vo.ChatListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dippy
 * @since 2021-05-05
 */
public interface SysUserChatMessageService extends IService<SysUserChatMessage> {

    /**
     * 通过当前用户获取聊天列表
     * @param userId
     * @return
     */
    List<ChatListVo> getChatListByUserId(Integer userId);
}
