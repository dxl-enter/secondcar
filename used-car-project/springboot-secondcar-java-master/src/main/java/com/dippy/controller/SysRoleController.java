package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysRole;
import com.dippy.service.SysRoleService;
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
@RequestMapping("yueChi/sysrole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:sysrole:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    // @RequiresPermissions("yueChi:sysrole:info")
    public Result info(@PathVariable("roleId") Integer roleId){
		SysRole sysRole = sysRoleService.getById(roleId);

        return Result.succ("操作成功！",sysRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:sysrole:save")
    public Result save(@RequestBody SysRole sysRole){
		sysRoleService.save(sysRole);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:sysrole:update")
    public Result update(@RequestBody SysRole sysRole){
		sysRoleService.updateById(sysRole);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:sysrole:delete")
    public Result delete(@RequestBody Integer[] roleIds){
		sysRoleService.removeByIds(Arrays.asList(roleIds));

        return Result.succ("操作成功！");
    }

}
