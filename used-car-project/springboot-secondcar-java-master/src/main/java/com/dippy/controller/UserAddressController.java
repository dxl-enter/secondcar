package com.dippy.controller;

import com.dippy.common.result.Result;
import com.dippy.entity.UserAddress;
import com.dippy.service.UserAddressService;
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
@RequestMapping("yueChi/useraddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:useraddress:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = userAddressService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{addressId}")
    // @RequiresPermissions("yueChi:useraddress:info")
    public Result info(@PathVariable("addressId") Integer addressId){
		UserAddress userAddress = userAddressService.getById(addressId);

        return Result.succ("操作成功！",userAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:useraddress:save")
    public Result save(@RequestBody UserAddress userAddress){
		userAddressService.save(userAddress);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("yueChi:useraddress:update")
    public Result update(@RequestBody UserAddress userAddress){
		userAddressService.updateById(userAddress);

        return Result.succ("操作成功！");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:useraddress:delete")
    public Result delete(@RequestBody Integer[] addressIds){
		userAddressService.removeByIds(Arrays.asList(addressIds));

        return Result.succ("操作成功！");
    }

}
