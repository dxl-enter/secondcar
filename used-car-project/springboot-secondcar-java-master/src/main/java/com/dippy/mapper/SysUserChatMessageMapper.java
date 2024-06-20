package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dippy.entity.SysUserChatMessage;
import com.dippy.vo.ChatListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dippy
 * @since 2021-05-05
 */
public interface SysUserChatMessageMapper extends BaseMapper<SysUserChatMessage> {
    List<ChatListVo> getChatListByUserId(@Param(value = "userId") Integer userId);
}
