package com.dippy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dippy.entity.SysPermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dippy
 * @since 2021-02-03
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("SELECT p.*  FROM sys_user u, sys_permission p LEFT JOIN sys_role_permission rp ON p.permission_id = rp.permission_id LEFT JOIN sys_role_user ru ON rp.role_id = ru.role_id  WHERE u.user_id = #{userId}  ")
    List<SysPermission> listPermissionByUserId(Integer userId);
}
