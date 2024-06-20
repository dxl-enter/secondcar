package com.dippy.controller;


import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.es.vo.ShAreaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dippy
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/shArea")
@Api(value = "地址接口2", tags = "管理省、市、县、详细地址")
public class ShAreaController extends BaseController {


    // @ApiOperation(value = "地址初始化2，缓存地址到Redis", tags = "-1缓存详细地址信息；1缓存省份；2缓存城市；3缓存区\\县")
    // @ApiOperation(value = "接口说明", httpMethod = "接口请求方式",response = Result.class, notes = "接口发布说明")
    @ApiOperation(value = "地址初始化2，缓存地址到Redis -1缓存详细地址信息；1缓存省份；2缓存城市；3缓存区\\县", httpMethod = "GET",response = Result.class, notes = "地址初始化2，缓存地址到Redis")
    @GetMapping("/initAddress")
    public Result initAddress(Integer addressLevel, Boolean cover) {

        int addressAmount = shAreaService.initAddress(addressLevel, cover);
        if (addressAmount == -1)
            return Result.succ("已经初始化过了！");

        return Result.succ(ResultCode.YES_UPLOAD_ADDRESS.getCode(), ResultCode.YES_UPLOAD_ADDRESS.getMessage(), addressAmount);
    }

    @ApiOperation(value = "获取详细地址", httpMethod = "GET",response = Result.class, notes = "接口发布说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "provinceId", value = "省份id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "cityId", value = "城市id", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "areaId", value = "区\\县id", required = false, dataType = "Integer", paramType = "query"),

    })
    @GetMapping("/getAddress")
    public Result getAddress(Integer provinceId, Integer cityId, Integer areaId) {

        List<ShAreaVo> shAreaVos = shAreaService.getAddressByAllId(provinceId,cityId,areaId);

        return Result.succ(ResultCode.YES_GET_ADDRESS.getCode());
    }

}
