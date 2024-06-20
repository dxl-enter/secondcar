package com.dippy.controller.es;

import com.dippy.common.result.Result;
import com.dippy.controller.BaseController;
import com.dippy.entity.CarInfo;
import com.dippy.entity.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/esInitDate")
@Api(value = "同步所有car信息到ES中", tags = "同步所有car信息到ES中")
public class EsInitAllDateController extends BaseController {

    /**
     * 初始化ES
     *
     * @return
     */
    @ApiOperation(value = "ES初始化同步")
    @PostMapping("/initAllCarInfoToEsData")
    public Result initAllCarInfoToEsData() {

        // System.out.println("1111111 carInfoService  " + carInfoService.toString());
        // System.out.println("1111111 EsInitAllDateController  " + EsInitAllDateController.class.hashCode());

        List<CarInfo> allCarInfo = carInfoService.getAllCarInfo();
        List<Region> list = regionService.list();


        // 初始化汽车信息
        int carNumbers = searchService.initEsData(allCarInfo);

        // 初始化地址信息
        int regionNumbers = searchService.initRegionEsData(list);

        long total = carNumbers + regionNumbers;

        log.info("ES初始化了，共 {} 条记录，汽车信息：{}，地址信息：{}", total, carNumbers, regionNumbers);
        return Result.succ("ES索引初始化成功，共 " + total + " 条记录！" + "汽车信息：{" + carNumbers + "}，地址信息：{" + regionNumbers + "}");
    }
}
