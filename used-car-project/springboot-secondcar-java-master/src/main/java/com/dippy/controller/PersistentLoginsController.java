package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.PersistentLogins;
import com.dippy.service.PersistentLoginsService;
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
@RequestMapping("yueChi/persistentlogins")
public class PersistentLoginsController {
    @Autowired
    private PersistentLoginsService persistentLoginsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:persistentlogins:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = persistentLoginsService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{series}")
    // @RequiresPermissions("yueChi:persistentlogins:info")
    public Result info(@PathVariable("series") String series){
		PersistentLogins persistentLogins = persistentLoginsService.getById(series);

        return Result.succ("操作成功！",persistentLogins);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:persistentlogins:save")
    public Result save(@RequestBody PersistentLogins persistentLogins){
		persistentLoginsService.save(persistentLogins);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:persistentlogins:update")
    public Result update(@RequestBody PersistentLogins persistentLogins){
		persistentLoginsService.updateById(persistentLogins);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:persistentlogins:delete")
    public Result delete(@RequestBody String[] seriess){
		persistentLoginsService.removeByIds(Arrays.asList(seriess));

        return Result.succ("操作成功！");
    }

}
