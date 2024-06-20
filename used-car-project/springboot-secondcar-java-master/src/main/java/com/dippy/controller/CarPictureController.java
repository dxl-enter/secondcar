package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.CarPicture;
import com.dippy.service.CarPictureService;
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
@RequestMapping("yueChi/carpicture")
public class CarPictureController {
    @Autowired
    private CarPictureService carPictureService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:carpicture:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = carPictureService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{carPictureId}")
    // @RequiresPermissions("yueChi:carpicture:info")
    public Result info(@PathVariable("carPictureId") Integer carPictureId){
		CarPicture carPicture = carPictureService.getById(carPictureId);

        return Result.succ("操作成功！",carPicture);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:carpicture:save")
    public Result save(@RequestBody CarPicture carPicture){
		carPictureService.save(carPicture);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:carpicture:update")
    public Result update(@RequestBody CarPicture carPicture){
		carPictureService.updateById(carPicture);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:carpicture:delete")
    public Result delete(@RequestBody Integer[] carPictureIds){
		carPictureService.removeByIds(Arrays.asList(carPictureIds));

        return Result.succ("操作成功！");
    }

}
