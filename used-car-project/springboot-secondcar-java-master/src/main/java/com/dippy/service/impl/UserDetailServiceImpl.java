package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dippy.dto.LoginUser;
import com.dippy.entity.SysPermission;
import com.dippy.entity.SysRoleUser;
import com.dippy.entity.SysUser;
import com.dippy.mapper.SysPermissionMapper;
import com.dippy.service.SysRoleService;
import com.dippy.service.SysRoleUserService;
import com.dippy.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义登录逻辑
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    // @Autowired
    // private UserInfoService userInfoService;

    // 用户角色
    @Autowired
    private SysRoleUserService sysRoleUserService;

    // 用户信息
    @Autowired
    private SysUserService sysUserService;

    // 权限
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    // 角色
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        log.info("执行UserDetailServiceImpl自定义登录逻辑---{}--用户名", username);

        // 1. 通过username查数据库
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));

        // 2.根据用户名去数据库查询，如果不存在抛UsernameNotFoundException异常
        if (sysUser.getUsername() == null) {
            log.info("用户名{}不存在", username);
            throw new UsernameNotFoundException("用户名不存在");
        } else if (sysUser.getIsLock() == SysUser.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (sysUser.getIsLock() == SysUser.Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }
        // 密码错
        // else if (!passwordEncoder.matches(password,sysUser.getPassword()))

        // 运行到这里变慢了卡顿！！！？？？？？？
        //2.比较密码（注册时已经加密过），如果匹配成功返回UserDetails
        // 登录加密
        // userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        // 3. 查询用户拥有的角色
        // TODO: 2021/3/27 待补充
        List<SysRoleUser> userHasRoles = sysRoleUserService.list(new QueryWrapper<SysRoleUser>()
                .eq("user_id", loginUser.getUserId()).select("role_id"));



        // 4.查询用户拥有的登录的权限
        List<SysPermission> permissions = sysPermissionMapper.listPermissionByUserId(sysUser.getUserId());
        // List<SysPermission> permissions = sysPermissionService.;
        loginUser.setPermissions(permissions);

        return loginUser;
    }

    /*@Override
    public UserDetails loadUserByUsername(String username) {

        log.info("执行UserDetailServiceImpl自定义登录逻辑---{}--用户名", username);

        // 1. 通过username查数据库
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>()sys_role_user
                .eq("username", username)
                .select("id", "username", "password", "name", "avatar")
        );
        // 设置账户正常
        userInfo.setStatus(UserInfo.Status.VALID);

        //1.根据用户名去数据库查询，如果不存在抛UsernameNotFoundException异常
        if (userInfo.getUsername() == null) {
            log.info("用户名不存在");
            throw new UsernameNotFoundException("用户名不存在");
        } else if (userInfo.getStatus() == UserInfo.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (userInfo.getStatus() == UserInfo.Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }

        // 运行到这里变慢了卡顿！！！？？？？？？
        //2.比较密码（注册时已经加密过），如果匹配成功返回UserDetails
        // 登录加密
        // userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        LoginUser loginUser = new LoginUser();

        BeanUtils.copyProperties(userInfo, loginUser);

        Permission permission = new Permission();
        permission.setName("admin");
        permission.setPermission("aaa");
        // 登录的权限
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        loginUser.setPermissions(permissions);

        // // 登录的权限
        // List<Permission> permissions = permissionDao.listByUserId(sysUser.getId());
        // loginUser.setPermissions(permissions);

        return loginUser;
    }*/
}
