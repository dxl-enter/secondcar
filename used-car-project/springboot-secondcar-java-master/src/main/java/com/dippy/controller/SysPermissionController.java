package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysPermission;
import com.dippy.service.SysPermissionService;
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
@RequestMapping("yueChi/syspermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:syspermission:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPermissionService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{permissionId}")
    // @RequiresPermissions("yueChi:syspermission:info")
    public Result info(@PathVariable("permissionId") Integer permissionId){
		SysPermission sysPermission = sysPermissionService.getById(permissionId);

        return Result.succ("操作成功！",sysPermission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:syspermission:save")
    public Result save(@RequestBody SysPermission sysPermission){
		sysPermissionService.save(sysPermission);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:syspermission:update")
    public Result update(@RequestBody SysPermission sysPermission){
		sysPermissionService.updateById(sysPermission);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:syspermission:delete")
    public Result delete(@RequestBody Integer[] permissionIds){
		sysPermissionService.removeByIds(Arrays.asList(permissionIds));

        return Result.succ("操作成功！");
    }

}
