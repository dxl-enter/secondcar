package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysUserCollectCars;
import com.dippy.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author dippy
 * @date 2021-05-03 23:56:29
 */
@RestController
@RequestMapping("/sysUserCollectCars")
@Api(value = "", tags = "()")
public class SysUserCollectCarsController extends BaseController {


    /**
     * 列表
     */
    @ApiOperation(value = "查看所有")
    @PostMapping("/list")
    // @ApiImplicitParam(name = "type", value = "地址类型，人还是车的", dataType = "String",paramType = "query")
    // @RequiresPermissions("yueChi:sysusercollectcars:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserCollectCarsService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "查询单个信息")
    @GetMapping("/info/{userId}")
    // @RequiresPermissions("yueChi:sysusercollectcars:info")
    public Result info(@PathVariable("userId") Integer userId) {
        SysUserCollectCars sysUserCollectCars = sysUserCollectCarsService.getById(userId);

        return Result.succ("操作成功！", sysUserCollectCars);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    // @RequiresPermissions("yueChi:sysusercollectcars:save")
    public Result save(@RequestBody SysUserCollectCars sysUserCollectCars) {
        sysUserCollectCarsService.save(sysUserCollectCars);

        return Result.succ("保存成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:sysusercollectcars:update")
    public Result update(@RequestBody SysUserCollectCars sysUserCollectCars) {
        sysUserCollectCarsService.updateById(sysUserCollectCars);

        return Result.succ("修改成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    // @RequiresPermissions("yueChi:sysusercollectcars:delete")
    public Result delete(@RequestBody Integer[] userIds) {
        sysUserCollectCarsService.removeByIds(Arrays.asList(userIds));

        return Result.succ("删除成功！");
    }



}
