package com.dippy.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.common.handle.MyException;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.config.RabbitConfig;
import com.dippy.dto.LoginUser;
import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;
import com.dippy.entity.SellerCar;
import com.dippy.entity.SysUser;
import com.dippy.es.model.CarInfoModel;
import com.dippy.mq.MQIndexMessage;
import com.dippy.util.QiniuUpload;
import com.dippy.util.UserUtil;
import com.dippy.vo.AddCarVo;
import com.dippy.vo.CarBrandVo;
import com.dippy.vo.CarDetailVo;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dippy
 * @since 2021-02-08
 */

@CrossOrigin
@RestController
@RequestMapping("/carInfo")
@Api(value = "汽车信息表", tags = "汽车信息")
@Slf4j
public class CarInfoController extends BaseController {


    @ApiOperation(value = "查找所有汽车品牌")
    @GetMapping("/findAllCarBrand")
    public Result findAllCarBrand() {
        List<CarBrandVo> result = new ArrayList<>();

        List<CarBrandVo> allCarBrand = carInfoService.getAllCarBrand();
        allCarBrand.add(0, new CarBrandVo("全部", true));

        // List<Object> brand = carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_brand)"));

        return Result.succ(allCarBrand);
    }

    @ApiOperation(value = "查询所有汽车车系")
    @GetMapping("/findAllCarSeries")
    public Result findAllCarSeries() {
        List<Object> series = carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_series)"));

        return Result.succ(series);
    }

    @ApiOperation(value = "查询所有汽车车型")
    @GetMapping("/findAllCarType")
    public Result findAllCarType() {
        List<Object> type = carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_type)"));

        return Result.succ(type);
    }

    @ApiOperation(value = "查询所有汽车品牌、车系、车型")
    @GetMapping("/findAllCarScreen")
    public Result findAllCarScreen() {
        List<Object> type = carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_type)"));

        return Result.succ(type);
    }


    @PostMapping("/saveCarInfo")
    @ApiOperation(value = "新增汽车11", httpMethod = "POST")
    @ApiImplicitParam(name = "registerVo", value = "注册信息", dataType = "RegisterVo")
    public Result saveCarInfo() {
        CarInfo carInfo = new CarInfo();
        carInfo.setCarBrand("路虎");
        if (!carInfoService.save(carInfo)) {
            return Result.fail("新增失败");
        }

        // 通知消息给mq，告知更新或添加
        amqpTemplate.convertAndSend(
                RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(carInfo.getCarId(), MQIndexMessage.CREATE_OR_UPDATE));


        return Result.succ();
    }


    @Transactional
    @ApiOperation(value = "新增汽车信息", httpMethod = "POST")
    @ApiImplicitParam(name = "addCarVo", value = "汽车属性", required = false, dataType = "AddCarVo", paramType = "body")
    @PostMapping("/addCarInfo")
    public Result addCarInfo(@RequestBody AddCarVo addCarVo) {


        LoginUser loginUser = UserUtil.getLoginUser();

        // 获取当前用户是否与登录用户一致，不一致就提示用户重新登录
        if (loginUser == null) {
            log.info("用户登录异常！重新登录");
            return Result.fail(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        addCarVo.setCreateTime(LocalDateTime.now());

        if (ObjectUtil.isNotNull(addCarVo)) {
            // 新增汽车信息到汽车表中
            boolean save = carInfoService.save(addCarVo);
            if (!save) {
                // 新增汽车失败
                return Result.fail(ResultCode.NO_ADD_CAR.getCode(), ResultCode.NO_ADD_CAR.getMessage());
            }
            SellerCar sellerCar = new SellerCar();
            sellerCar.setCarId(addCarVo.getCarId());
            sellerCar.setUserId(loginUser.getUserId());
            sellerCar.setCreateTime(LocalDateTime.now());

            // 新增的汽车信息的id存入我的汽车表中
            sellerCarService.save(sellerCar);
        }

        // 通知消息给mq，告知更新或添加
        amqpTemplate.convertAndSend(
                RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(addCarVo.getCarId(), MQIndexMessage.CREATE_OR_UPDATE));

        log.info("用户为 {}在添加汽车的id为 {}", loginUser.getUsername(), addCarVo.getCarId());

        return Result.succ(addCarVo.getCarId());
    }


    @Transactional
    @ApiOperation(value = "上传新增汽车的图片", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "汽车主图", required = false, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "carInsideFileList", value = "汽车内饰图片列表", required = false, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "carOutsideFileList", value = "汽车外饰图片列表", required = false, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "carOtherFileList", value = "汽车其他图片列表", required = false, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "carouselFileList", value = "汽车轮播图列表", required = false, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "carId", value = "汽车id", dataType = "Integer"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer"),
    })
    @PostMapping("/addCarInfoPictures")
    public Result addCarInfoPictures(@RequestParam(value = "file", required = false) MultipartFile[] files,// 汽车图片
                                     @RequestParam(value = "carInsideFileList", required = false) MultipartFile[] carInsideFileList, //内饰
                                     @RequestParam(value = "carOutsideFileList", required = false) MultipartFile[] carOutsideFileList,// 外饰
                                     @RequestParam(value = "carOtherFileList", required = false) MultipartFile[] carOtherFileList,// 其他
                                     @RequestParam(value = "carouselFileList", required = false) MultipartFile[] carouselFileList,// 轮播图
                                     Integer carId, Integer userId) {
        // 1. 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();
        // 断言用户为空
        assert loginUser != null;
        log.info("用户为 {}", loginUser.getUsername());
        // 获取当前用户是否与登录用户一致，不一致就提示用户重新登录
        if (!userId.equals(loginUser.getUserId())) {
            log.info("用户登录异常！重新登录");
            return Result.fail(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        // TODO 后期可以去掉，重新修改逻辑。 因为之前设计数据库时有不足，冗余字段，并且已经编码了所以得按之前的来、就是冗余字段carInfo中的carPicture
        // carInfo
        CarInfo carInfo = new CarInfo();
        carInfo.setUserId(userId);
        carInfo.setCarId(carId);


        // 主图
        if (files != null) {
            uploadPicture(files, carId, carInfo, 0);
        }
        // 内饰图
        if (carInsideFileList != null) {
            uploadPicture(carInsideFileList, carId, null, 1);
        }
        // 外饰图
        if (carOutsideFileList != null) {
            uploadPicture(carOutsideFileList, carId, null, 2);
        }
        // 其他图
        if (carOtherFileList != null) {
            uploadPicture(carOtherFileList, carId, null, 3);
        }
        // 轮播图
        if (carouselFileList != null) {
            uploadPicture(carouselFileList, carId, null, 4);
        }

        return Result.succ(ResultCode.YES_ADD_CAR_PICTURE.getCode(), ResultCode.YES_ADD_CAR_PICTURE.getMessage());
    }

    /**
     * 上传汽车图片的主要函数-上传到七牛云后返回url-存入数据库
     *
     * @param files              图片文件列表
     * @param carId              汽车id
     * @param carInfo            汽车信息
     * @param carPictureLocation 图片的位置
     */
    private void uploadPicture(@RequestParam(value = "file", required = false) MultipartFile[] files,
                               Integer carId, CarInfo carInfo, Integer carPictureLocation) {

        // 汽车图片
        CarPicture carPicture = new CarPicture();
        carPicture.setCarId(carId);
        carPicture.setCarPictureLocation(carPictureLocation);

        // 遍历上传到七牛云
        for (MultipartFile file : files) {
            // 上传到七牛云,返回url
            String url = QiniuUpload.uploadFile(file);
            carPicture.setCarUrl(url);

            // 保存或修改到数据库
            // carPictureService.save(carPicture);
            carPictureService.saveOrUpdate(carPicture,
                    new QueryWrapper<CarPicture>().eq("car_id", carPicture.getCarId())
                            .eq("car_picture_location", carPicture.getCarPictureLocation())
            );

            if (carPictureLocation == 0) {
                // TODO 后期可以去掉冗余字段重新修改逻辑，汽车主图保存到表carInfo中
                carInfo.setCarPicture(url);
                carInfoService.updateById(carInfo);
            }
        }

        // 通知消息给mq，告知es更新或添加汽车信息
        amqpTemplate.convertAndSend(
                RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(carId, MQIndexMessage.CREATE_OR_UPDATE));
    }


    @Transactional
    @ApiOperation(value = "删除车辆")
    @PostMapping("/deleteCarInfo")
    public Result deleteCarInfo(Integer carId) {

        Assert.notNull(carId, "汽车id为空");
        if (!carInfoService.removeById(carId)) {
            return Result.fail("删除失败");
        }

        // 通知消息给mq，告知更新或添加
        amqpTemplate.convertAndSend(
                RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(carId, MQIndexMessage.REMOVE));

        return Result.succ();
    }


    /**
     * 查找全部汽车，包括全部汽车图片
     *
     * @return
     */

    public Result findAllCarInfo() {
        carInfoService.list();

        return Result.succ();
    }


    /**
     * 汽车上架
     *
     * @return
     */
    @Transactional
    @ApiOperation(value = "汽车上架")
    @PostMapping("/up/{carId}")
    public Result up(@PathVariable("carId") Integer carId) {
        CarInfoModel carInfoModel = carInfoService.up(carId);
        if (carInfoModel == null) {
            throw new DataAccessResourceFailureException("carId: " + carId + " 上架失败！");
        }
        carInfoRepository.save(carInfoModel);

        log.info("es 索引更新成功！ ---> {}", carInfoModel.toString());
        // // 通知消息给mq，告知更新或添加
        // amqpTemplate.convertAndSend(
        //         RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
        //         new MQIndexMessage(carId, MQIndexMessage.CREATE_OR_UPDATE));
        return Result.succ("汽车上架成功！");
    }


    /**
     * 分页初始化ES部分。
     *
     * @return
     */
    @ApiOperation(value = "ES初始化同步-部分-分页-每页5条")
    @PostMapping("/initEsData")
    public Result initEsData() {

        int size = 10000;
        Page page = new Page();
        page.setSize(size);

        long total = 0;

        /**
         * 1. 查询汽车信息。
         * 2. 汽车图片信息。
         * 3. 并封装到carInfoModel中
         */
        for (int i = 1; i < 1000; i++) {
            page.setCurrent(i);

            // 1. 查询汽车信息

            IPage<CarInfo> allCarInfo = carInfoService.getAllCarInfoByPage();

            int num = searchService.initEsData(allCarInfo.getRecords());

            total += num;

            // 当一页查不出10000条的时候，说明是最后一页了
            if (allCarInfo.getRecords().size() < size) {
                break;
            }
        }
        log.info("ES初始化了 共 {} 条记录", total);
        return Result.succ("ES索引初始化成功，共 " + total + " 条记录！");
    }


    @ApiOperation(value = "ES删除全部CarInfo")
    @PostMapping("/deleteALlESCarInfoModel")
    // @RequiresPermissions("${moduleName}:carinfo:info")
    public Result deleteALlESCarInfoModel() {
        boolean b = searchService.deleteAllESCarInfoIndex();
        return b ? Result.succ("ES 全部删除成功！！！") : Result.fail("ES 删除全部时出现异常！");
    }

    // /**
    //  * 列表
    //  */
    // @RequestMapping("/list")
    // // @RequiresPermissions("${moduleName}:carinfo:list")
    // public R list(@RequestParam Map<String, Object> params){
    //     PageUtils page = carInfoService.queryPage(params);
    //
    //     return R.ok().put("page", page);

    @ApiOperation(value = "汽车首页", notes = "跳到汽车首页")
    @PostMapping("/carInfoIndex")
    public Result goIndex(HttpServletResponse httpServletResponse) {
        log.info("跳转到汽车首页了");
        try {
            httpServletResponse.sendRedirect("/carInfo/carInfoIndex");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.succ("成功");
    }
    // }

    /**
     * 信息
     */
    @ApiOperation(value = "按汽车ID查询汽车信息")
    @PostMapping("/info/{carId}")
    // @RequiresPermissions("${moduleName}:carinfo:info")
    public Result info(@PathVariable("carId") Integer carId) {
        CarInfo carInfo = carInfoService.getById(carId);

        return Result.succ(carInfo);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存汽车信息")
    @PostMapping("/save")
    // @RequiresPermissions("${moduleName}:carinfo:save")
    public Result save(@RequestBody CarInfo carInfo) {
        carInfoService.save(carInfo);

        return Result.succ();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "更新汽车信息")
    @PostMapping("/update")
    // @RequiresPermissions("${moduleName}:carinfo:update")
    public Result update(@RequestBody CarInfo carInfo) {
        carInfoService.updateById(carInfo);

        return Result.succ();
    }

    /**
     * 删除
     */

    @ApiOperation(value = "按汽车id批量删除信息")
    @DeleteMapping("/delete")
    // @RequiresPermissions("${moduleName}:carinfo:delete")
    public Result delete(@RequestBody Integer[] carIds) {
        carInfoService.removeByIds(Arrays.asList(carIds));

        return Result.succ();
    }

    //PreAuthorize允许角色以ROLE_开头，也可以不以ROLE_开头，但是配置类不允许以ROLE_开头
    // @PreAuthorize("hasRole('adc')")
    @PostMapping("/success")
    @ApiOperation(value = "用户列表", notes = "登录成功页面")
    public Result home() {
        log.info("登录成功页面");

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 当前登录用户
        LoginUser currentUser = currentUser();

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(currentUser, loginUser);
        loginUser.setPassword(null);

        // 返回登录成功的用户信息
        return Result.succ(loginUser);
    }


    @ApiOperation(value = "根据carId查汽车详细信息", httpMethod = "GET", notes = "汽车全部信息")
    @ApiImplicitParam(name = "carId", value = "汽车id", dataType = "Integer", paramType = "query")
    @GetMapping("/getCarDetail/{carId}")
    public Result getCarDetailByCarId(@NotNull @PathVariable(name = "carId") Integer carId) {

        if (carId == null) {
            throw new MyException(ResultCode.PARAM_NOT_COMPLETE.getCode(), ResultCode.PARAM_NOT_COMPLETE.getMessage());
        }

        // CarDetailVo carDetailVo =  carInfoService.getCarDetailByCarId(carId);

        // 根据carId 查汽车全部信息
        CarInfo carInfo = carInfoService.getById(carId);

        // 根据carId 查该车所有的图片
        List<CarPicture> carPictures = carPictureService.list(
                new QueryWrapper<CarPicture>().eq("car_id", carId).select("car_picture_location", "car_url"));

        // 根据汽车图片位置进行分组  汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4-轮播图
        Map<Integer, List<CarPicture>> picturesMap = carPictures.stream().collect(Collectors.groupingBy(CarPicture::getCarPictureLocation));


        // QueryWrapper<CarPicture> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("car_id",carId).select("car_picture_location","car_url").groupBy("car_picture_location");
        // List<Map<String, Object>> maps = carPictureService.listMaps(queryWrapper);

        SellerCar sellerCar = sellerCarService.getOne(new QueryWrapper<SellerCar>().eq("car_id", carId).select("user_id"));


        // 根据carId 查该车车主的信息 、还可以返回车主的地址。
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>()
                .eq("user_id", sellerCar.getUserId())
                .select("name", "phone", "sex")
        );

        // 封装返回对象
        CarDetailVo carDetailVo = new CarDetailVo();
        BeanUtil.copyProperties(sysUser, carDetailVo);
        carDetailVo.setCarInfo(carInfo);
        // carDetailVo.setCarPictures(carPictures);
        // carDetailVo.setCarAllPictures(maps);

        carDetailVo.setCarPicturesMap(picturesMap);
/*        // if (sysUser.getName() != null)
            carDetailVo.setName(sysUser.getName());
        // if (sysUser.getPhone() != null)
            carDetailVo.setPhone(sysUser.getPhone());
        // if (sysUser.getSex() != null)
            carDetailVo.setSex(sysUser.getSex());*/

        // 返回结果
        return Result.succ(carDetailVo);
    }


    @Transactional
    @ApiOperation(value = "根据car修改相关汽车参数", httpMethod = "PUT")
    @ApiImplicitParam(name = "carId", value = "汽车Id", dataType = "Integer", paramType = "body")
    @PutMapping("/updateCarInfoById/{carId}")
    public Result updateCarInfoById(@NotNull @PathVariable(name = "carId") Integer carId,
                                    @RequestBody AddCarVo addCarVo) {

        // 修改汽车信息
        carInfoService.updateById(addCarVo);

        // 通知mq修改es中汽车信息
        amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(addCarVo.getCarId(),MQIndexMessage.CREATE_OR_UPDATE));

        return Result.succ(ResultCode.YES_UPDATE_CAR.getCode(),ResultCode.YES_UPDATE_CAR.getMessage(),addCarVo);
    }

}
