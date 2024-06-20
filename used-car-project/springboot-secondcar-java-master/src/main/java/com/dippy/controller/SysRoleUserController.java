package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysRoleUser;
import com.dippy.service.SysRoleUserService;
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
@RequestMapping("yueChi/sysroleuser")
public class SysRoleUserController {
    @Autowired
    private SysRoleUserService sysRoleUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:sysroleuser:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleUserService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    // @RequiresPermissions("yueChi:sysroleuser:info")
    public Result info(@PathVariable("userId") Integer userId){
		SysRoleUser sysRoleUser = sysRoleUserService.getById(userId);

        return Result.succ("操作成功！",sysRoleUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:sysroleuser:save")
    public Result save(@RequestBody SysRoleUser sysRoleUser){
		sysRoleUserService.save(sysRoleUser);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:sysroleuser:update")
    public Result update(@RequestBody SysRoleUser sysRoleUser){
		sysRoleUserService.updateById(sysRoleUser);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:sysroleuser:delete")
    public Result delete(@RequestBody Integer[] userIds){
		sysRoleUserService.removeByIds(Arrays.asList(userIds));

        return Result.succ("操作成功！");
    }

}
