package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.LogLogin;
import com.dippy.service.LogLoginService;
import com.dippy.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;





/**
 * 登录日志表
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
@RestController
@RequestMapping("yueChi/loglogin")
public class LogLoginController {
    @Autowired
    private LogLoginService logLoginService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:loglogin:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = logLoginService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("yueChi:loglogin:info")
    public Result info(@PathVariable("id") Long id){
		LogLogin logLogin = logLoginService.getById(id);

        return Result.succ("操作成功！",logLogin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:loglogin:save")
    public Result save(@RequestBody LogLogin logLogin){
		logLoginService.save(logLogin);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:loglogin:update")
    public Result update(@RequestBody LogLogin logLogin){
		logLoginService.updateById(logLogin);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:loglogin:delete")
    public Result delete(@RequestBody Long[] ids){
		logLoginService.removeByIds(Arrays.asList(ids));

        return Result.succ("操作成功！");
    }

}
