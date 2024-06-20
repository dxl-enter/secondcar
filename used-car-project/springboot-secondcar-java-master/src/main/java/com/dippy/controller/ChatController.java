package com.dippy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dippy.common.result.Result;
import com.dippy.entity.SysUserChatMessage;
import com.dippy.vo.ChatListVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 在线聊天
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController{

    @ApiOperation(value = "根据当前用户获取跟他有关联的聊天列表",httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", dataType = "Integer", paramType = "query")
    @GetMapping("/getChatListByUserId/{userId}")
    public Result getChatListByUserId(@NotNull @PathVariable(name="userId") Integer userId){

        List<SysUserChatMessage> from = sysUserChatMessageService.list(new QueryWrapper<SysUserChatMessage>().eq("from_id", userId).select("to_id"));
        // sysUserService.list(new QueryWrapper<SysUser>().eq("user_id",from))

        List<ChatListVo> date = sysUserChatMessageService.getChatListByUserId(userId);

        return Result.succ(date);
    }
}
