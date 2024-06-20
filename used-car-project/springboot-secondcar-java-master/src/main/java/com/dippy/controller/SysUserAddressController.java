package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SysUserAddress;
import com.dippy.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
// import com.dippy.common.utils.PageUtils;




/**
 *
 *
 * @author dippy
 * @date 2021-04-19 17:03:13
 */
@RestController
@RequestMapping("/sysuseraddress")
@Api(value = "用户全部地址", tags = "(对的)")
public class SysUserAddressController extends BaseController {


    /**
     * 列表
     */
    @ApiOperation(value = "查看所有")
    @PostMapping("/list")
    // @ApiImplicitParam(name = "type", value = "地址类型，人还是车的", dataType = "String",paramType = "query")
    // @RequiresPermissions("yueChi:sysuseraddress:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserAddressService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "查询单个信息")
    @GetMapping("/info/{sysUserAddressId}")
    // @RequiresPermissions("yueChi:sysuseraddress:info")
    public Result info(@PathVariable("sysUserAddressId") Integer sysUserAddressId){
		SysUserAddress sysUserAddress = sysUserAddressService.getById(sysUserAddressId);

        return Result.succ("操作成功！", sysUserAddress);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    // @RequiresPermissions("yueChi:sysuseraddress:save")
    public Result save(@RequestBody SysUserAddress sysUserAddress){
		sysUserAddressService.save(sysUserAddress);

        return Result.succ("保存成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:sysuseraddress:update")
    public Result update(@RequestBody SysUserAddress sysUserAddress){
		sysUserAddressService.updateById(sysUserAddress);

        return Result.succ("修改成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    // @RequiresPermissions("yueChi:sysuseraddress:delete")
    public Result delete(@RequestBody Integer[] sysUserAddressIds){
		sysUserAddressService.removeByIds(Arrays.asList(sysUserAddressIds));

        return Result.succ("删除成功！");
    }

}
