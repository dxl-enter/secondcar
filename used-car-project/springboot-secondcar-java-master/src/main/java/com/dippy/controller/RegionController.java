package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.entity.Region;
import com.dippy.util.PageUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
@RestController
@RequestMapping("yueChi/region")
@Api(value = "地址接口", tags = "管理省、市、县、详细地址")
public class RegionController extends BaseController {

    /**
     * 列表
     */
    @ApiOperation(value = "查询所有")
    @PostMapping("/list")
    // @RequiresPermissions("yueChi:region:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = regionService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "查询单个信息")
    @GetMapping("/info/{id}")
    // @RequiresPermissions("yueChi:region:info")
    public Result info(@PathVariable("id") Integer id) {
        Region region = regionService.getById(id);

        return Result.succ("操作成功！", region);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    // @RequiresPermissions("yueChi:region:save")
    public Result save(@RequestBody Region region) {
        regionService.save(region);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:region:update")
    public Result update(@RequestBody Region region) {
        regionService.updateById(region);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    // @RequiresPermissions("yueChi:region:delete")
    public Result delete(@RequestBody Integer[] ids) {
        regionService.removeByIds(Arrays.asList(ids));

        return Result.succ("操作成功！");
    }

    @ApiOperation(value = "获取全部城市")
    @GetMapping("/getAllCityList")
    public Result getCityList() {

        return Result.succ(ResultCode.YES_UPLOAD_ADDRESS.getCode(), ResultCode.YES_UPLOAD_ADDRESS.getMessage());
    }

    @ApiOperation(value = "地址初始化，缓存地址到Redis", tags = "-1缓存详细地址信息；1缓存省份；2缓存城市；3缓存区\\县")
    @GetMapping("/initAddress")
    public Result initAddress(Integer addressLevel,Boolean cover) {

        int addressAmount = regionService.initAddress(addressLevel,cover);
        if (addressAmount == -1)
            return Result.succ("已经初始化过了！");

        return Result.succ(ResultCode.YES_UPLOAD_ADDRESS.getCode(), ResultCode.YES_UPLOAD_ADDRESS.getMessage(), addressAmount);
    }


    @GetMapping("/addressFillById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "选择的区\\县的id", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "detailedAddress", value = "具体地址", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "type", value = "地址类型，人还是车的", dataType = "String",paramType = "query"),
    })
    public Result addressFill(Integer areaId,String detailedAddress,Integer id,String type) {

        boolean  isSuccess = regionService.addressFill(areaId,detailedAddress,id, type);

        return Result.succ(ResultCode.YES_GET_ADDRESS.getCode());
    }

}
