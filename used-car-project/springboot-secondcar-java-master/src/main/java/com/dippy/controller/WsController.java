package com.dippy.controller;

import com.dippy.dto.LoginUser;
import com.dippy.entity.SysUserChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 聊天、发送消息
     * @param authentication
     * @param chatMessage
     */
    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, SysUserChatMessage chatMessage) {
        // 获取当前用户
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        chatMessage.setFromId(loginUser.getUserId());
        chatMessage.setFromName(loginUser.getName());
        chatMessage.setUpdateTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getToName(),"/queue/chat",chatMessage);
    }

}
