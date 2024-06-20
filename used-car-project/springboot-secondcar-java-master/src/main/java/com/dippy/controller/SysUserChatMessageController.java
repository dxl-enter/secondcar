package com.dippy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.entity.SellerCar;
import com.dippy.entity.SysUser;
import com.dippy.entity.SysUserChatMessage;
import com.dippy.vo.ChatListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dippy
 * @since 2021-05-05
 */
@RestController
@RequestMapping("/sysUserChatMessage")
@Api(value = "聊天信息表")
public class SysUserChatMessageController extends BaseController {

    @ApiOperation(value = "获取聊天列表", httpMethod = "GET")
    @GetMapping("/getChatList/{userId}/{carId}")
    // @ApiImplicitParam(name = "userId", value = "当前用户id", dataTypeClass = Integer.class)
    public Result getChatList(@PathVariable(value = "userId",required = true) Integer userId,
                              @PathVariable(value = "carId",required = false) Integer carId) {

        // 买家
        if (carId != null) {

            // 1.1 carId查卖家id
            SellerCar seller = sellerCarService.getOne(new QueryWrapper<SellerCar>().eq("car_id", carId).select("user_id"));
            // 1.2 通过卖家Id查卖家信息
            SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_id", seller.getUserId()).select("name", "avatar", "user_id"));

            // 2.1 查历史聊天记录
            List<SysUserChatMessage> chatMessageList = sysUserChatMessageService.list(new QueryWrapper<SysUserChatMessage>().
                    eq("from_id", userId).eq("to_id", seller.getUserId()));
            // 2.2 插入一条记录


            // 封装返回的数据
            ChatListVo chatListVo = new ChatListVo();
            chatListVo.setAvatar(sysUser.getAvatar());
            chatListVo.setFromId(userId);
            // chatListVo.setFromName();
            chatListVo.setToId(seller.getUserId());
            chatListVo.setToName(sysUser.getName());
            chatListVo.setContent(null);


            List<ChatListVo> chatListVos = new LinkedList<>();
            chatListVos.add(chatListVo);

            return Result.succ(ResultCode.YES_GET_CHATS.getCode(), ResultCode.YES_GET_CHATS.getMessage(), chatListVos);
        }


        // 卖家
        // 2.1 查历史聊天记录
        List<SysUserChatMessage> chatMessageList = sysUserChatMessageService.list(new QueryWrapper<SysUserChatMessage>().
                eq("from_id", userId));

        return Result.succ(ResultCode.YES_GET_CHATS.getCode(), ResultCode.YES_GET_CHATS.getMessage(), chatMessageList);


    }

/*    @ApiOperation(value = "卖家获取聊天列表", httpMethod = "GET")
    @GetMapping("/getChatList/{userId}/")
    @ApiImplicitParam(name = "userId", value = "当前用户id", dataTypeClass = Integer.class)
    public Result getChatList(@NotNull @PathVariable(name = "userId") Integer userId) {

        // 2.1 查历史聊天记录
        List<SysUserChatMessage> chatMessageList = sysUserChatMessageService.list(new QueryWrapper<SysUserChatMessage>().
                eq("from_id", userId));

        return Result.succ(ResultCode.YES_GET_CHATS.getCode(),ResultCode.YES_GET_CHATS.getMessage(),chatMessageList);
    }*/


}
