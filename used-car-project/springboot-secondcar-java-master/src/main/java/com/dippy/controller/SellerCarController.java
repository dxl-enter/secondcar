package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.SellerCar;
import com.dippy.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
// import com.dippy.common.utils.PageUtils;




/**
 * 卖家拥有的车辆
 *
 * @author dippy
 * @date 2021-04-21 13:21:37
 */
@RestController
@RequestMapping("/sellerCar")
@Api(value = "卖家拥有的车辆")
public class SellerCarController extends BaseController {

    /**
     * 列表
     */
    @ApiOperation(value = "查看所有")
    @PostMapping("/list")
    // @ApiImplicitParam(name = "type", value = "地址类型，人还是车的", dataType = "String",paramType = "query")
    // @RequiresPermissions("yueChi:sellercar:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sellerCarService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "查询单个信息")
    @GetMapping("/info/{userId}")
    // @RequiresPermissions("yueChi:sellercar:info")
    public Result info(@PathVariable("userId") Integer userId){
		SellerCar sellerCar = sellerCarService.getById(userId);

        return Result.succ("操作成功！", sellerCar);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    // @RequiresPermissions("yueChi:sellercar:save")
    public Result save(@RequestBody SellerCar sellerCar){
		sellerCarService.save(sellerCar);

        return Result.succ("保存成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:sellercar:update")
    public Result update(@RequestBody SellerCar sellerCar){
		sellerCarService.updateById(sellerCar);

        return Result.succ("修改成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    // @RequiresPermissions("yueChi:sellercar:delete")
    public Result delete(@RequestBody Integer[] userIds){
		sellerCarService.removeByIds(Arrays.asList(userIds));

        return Result.succ("删除成功！");
    }

}
