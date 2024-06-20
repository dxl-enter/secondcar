package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dippy.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dippy
 * @since 2020-12-05
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    void insertUserInfo(UserInfo user);

    void batchInsertInsertUserInfo(@Param("list") List<UserInfo> list);
}
