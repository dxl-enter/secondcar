package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysRolePermission;
import com.dippy.service.SysRolePermissionService;
import com.dippy.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;




/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
@RestController
@RequestMapping("yueChi/sysrolepermission")
public class SysRolePermissionController {
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:sysrolepermission:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRolePermissionService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    // @RequiresPermissions("yueChi:sysrolepermission:info")
    public Result info(@PathVariable("roleId") Integer roleId){
		SysRolePermission sysRolePermission = sysRolePermissionService.getById(roleId);

        return Result.succ("操作成功！",sysRolePermission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:sysrolepermission:save")
    public Result save(@RequestBody SysRolePermission sysRolePermission){
		sysRolePermissionService.save(sysRolePermission);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:sysrolepermission:update")
    public Result update(@RequestBody SysRolePermission sysRolePermission){
		sysRolePermissionService.updateById(sysRolePermission);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:sysrolepermission:delete")
    public Result delete(@RequestBody Integer[] roleIds){
		sysRolePermissionService.removeByIds(Arrays.asList(roleIds));

        return Result.succ("操作成功！");
    }

}
