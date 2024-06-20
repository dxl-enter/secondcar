package com.dippy.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.common.handle.MyException;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.config.RabbitConfig;
import com.dippy.dto.LoginUser;
import com.dippy.entity.*;
import com.dippy.es.vo.AddressVo;
import com.dippy.mq.MQIndexMessage;
import com.dippy.util.PageUtils;
import com.dippy.util.QiniuUpload;
import com.dippy.util.UserUtil;
import com.dippy.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author dippy
 * @date 2021-04-09 23:20:07
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/yueChi/sysUser")
public class SysUserController extends BaseController {


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("yueChi:sysuser:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    // @RequiresPermissions("yueChi:sysuser:info")
    public Result info(@PathVariable("userId") Integer userId) {
        SysUser sysUser = sysUserService.getById(userId);

        return Result.succ("操作成功！", sysUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("yueChi:sysuser:save")
    public Result save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);

        return Result.succ("操作成功！");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:sysuser:update")
    public Result update(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);

        return Result.succ("操作成功！");
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("yueChi:sysuser:delete")
    public Result delete(@RequestBody Integer[] userIds) {
        sysUserService.removeByIds(Arrays.asList(userIds));

        return Result.succ("操作成功！");
    }


    /**
     * 头像文件上传
     *
     * @param filePath 上传路径
     * @param fileName 上传的文件名
     * @return
     */
    @PostMapping("/uploadAvg")
    public Result uploadAvg(String filePath, String fileName) {
        sysUserService.uploadAvg(filePath, fileName);

        return Result.succ();
    }


    /**
     * 填写个人地址
     *
     * @return
     */
    @PostMapping("/addressFillById")
    @ApiOperation(value = "填写个人地址", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "选择的区\\县的id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "detailedAddress", value = "具体地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "Integer", paramType = "query"),
    })
    public Result addressFill(@RequestBody AddressVo addressVo) {

        String completeAddress = sysUserService.addressFill(addressVo);

        return Result.succ(ResultCode.YES_GET_ADDRESS.getCode());
    }


    @PostMapping("/fail")
    @ApiOperation(value = "用户列表", notes = "登录失败页面")
    public Result about() {
        log.info("登录失败页面");
        return Result.fail("登录失败！");
    }

    //PreAuthorize允许角色以ROLE_开头，也可以不以ROLE_开头，但是配置类不允许以ROLE_开头
    // @PreAuthorize("hasRole('adc')")
    @PostMapping("/success")
    @ApiOperation(value = "登录成功", notes = "登录成功页面")
    public Result home(HttpServletRequest request, HttpServletResponse response) {
        log.info("登录成功页面");

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 当前登录用户
        LoginUser currentUser = UserUtil.getLoginUser();
        assert currentUser != null;
        currentUser.setPassword(null);

        // 返回登录成功的用户信息
        return Result.succ(ResultCode.YES_USER_LOGIN.getCode(), currentUser.getUsername() + ResultCode.YES_USER_LOGIN.getMessage()
                , currentUser);
    }


    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public Result logout() {

        String token = null;

        try {
            // 根据request域获取头token的值
            token = req.getHeader(AUTHORIZATION);

            // String token = tokenFilter.getToken(request);
            // 删除redis中的token
            tokenService.deleteToken(token);
        } catch (Exception e) {
            log.info("退出失败");
            e.printStackTrace();
        }

        return Result.succ(ResultCode.YES_USER_LOGOUT.getCode(), ResultCode.YES_USER_LOGOUT.getMessage(), token);
    }


    @Transactional
    @ApiOperation(value = "注册", httpMethod = "POST")
    @ApiImplicitParam(name = "registerVo", value = "注册信息", dataType = "RegisterVo")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {

        // 两次密码是否一致
        if (!registerVo.getPassword().equals(registerVo.getPasswordConfirm())) {
            return Result.fail(ResultCode.NO_USER_PASSWORD_NO_SAME.getCode(), ResultCode.NO_USER_PASSWORD_NO_SAME.getMessage());
        }

        // 判空
        if (ObjectUtil.isNull(registerVo))
            throw new MyException(ResultCode.NO_USER_REGISTER.getCode(), ResultCode.NO_USER_REGISTER.getMessage());


        // 查出是否已经该用户名和角色
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>()
                .eq("username", registerVo.getUsername())
                .select("user_id", "user_id"));

        // 已经有有该用户、去查角色
        if (ObjectUtil.isNotNull(sysUser)) {
            // 查询用户角色
            SysRoleUser roleUser = sysRoleUserService.getOne(new QueryWrapper<SysRoleUser>()
                    .eq("user_id", sysUser.getUserId())
                    .eq("role_id", registerVo.getRoleId()));

            // 有该角色、抛异常
            if (ObjectUtil.isNotNull(roleUser))
                return Result.fail(ResultCode.NO_USER_REGISTER_HAS_ROLE.getCode(), ResultCode.NO_USER_REGISTER_HAS_ROLE.getMessage());
            // throw new MyException(ResultCode.NO_USER_REGISTER_HAS_ROLE.getCode(), ResultCode.NO_USER_REGISTER_HAS_ROLE.getMessage());

            // 没有该角色、注册该角色
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(registerVo.getRoleId());
            sysRoleUser.setUserId(sysUser.getUserId());
            boolean save = sysRoleUserService.save(sysRoleUser);
            if (save) {
                // 注册成功
                return Result.succ(ResultCode.YES_USER_REGISTER.getCode(), ResultCode.YES_USER_REGISTER.getMessage(), registerVo.getUsername() + "注册成功");
            }

        }

        // 否则没有该用户名或对应角色，去注册
        Boolean success = sysUserService.register(registerVo);

        // 注册失败、
        if (!success)
            // throw new MyException(ResultCode.NO_USER_REGISTER.getCode(), ResultCode.NO_USER_REGISTER.getMessage());
            return Result.fail(ResultCode.NO_USER_REGISTER.getCode(), ResultCode.NO_USER_REGISTER.getMessage());


        log.info("用户名为：{}，在 {} 注册", registerVo.getUsername(), new Date());
        // 注册成功
        return Result.succ(ResultCode.YES_USER_REGISTER.getCode(), ResultCode.YES_USER_REGISTER.getMessage(), registerVo.getUsername() + "注册成功");

    }


    // @ApiOperation(value = "上传头像", httpMethod = "POST")
    // @ApiImplicitParam(name = "registerVo", value = "注册信息", dataType = "RegisterVo")
    // @PostMapping("/uploadAvatar")
    // public Result uploadPicture(@RequestParam("file") MultipartFile multipartFile) {
    //
    //     String pictureUrl = sysUserService.uploadPicture(multipartFile);
    //
    //     if (StrUtil.isEmpty(pictureUrl))
    //         return Result.fail(ResultCode.NO_USER_AVATAR_UPLOAD.getCode(), ResultCode.NO_USER_AVATAR_UPLOAD.getMessage(), pictureUrl);
    //
    //     return Result.succ(ResultCode.YES_USER_AVATAR_UPLOAD.getCode(), ResultCode.YES_USER_AVATAR_UPLOAD.getMessage(), pictureUrl);
    // }


    /**
     * 可以用的上传用户头像
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传头像", httpMethod = "POST")
    // @ApiImplicitParam(name = "registerVo", value = "注册信息", dataType = "RegisterVo")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uploadFile(@RequestParam("file") MultipartFile file, SysUserUploadAvatarVo sysUserUploadAvatarVo) {
        LoginUser loginUser = null;

        try {
            // 获取当前用户
            loginUser = UserUtil.getLoginUser();

            // Integer userId = sysUserUploadAvatarVo.getUserId();
            log.info("修改头像的用户为 {}", loginUser.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("用户登录异常！重新登录");
            return Result.fail(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }


        String url = null;
        try {

            // 上传文件
            url = QiniuUpload.uploadFile(file);

            // SysUser sysUser = new SysUser();
            // BeanUtil.copyProperties(sysUserUploadAvatarVo, sysUser);

            loginUser.setUpdateTime(LocalDateTime.now());
            loginUser.setAvatar(url);

            // 1. 先更新缓存。
            tokenService.refresh(loginUser);

            // 2. 再通知消息给mq，更新数据库信息
            amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                    new MQIndexMessage(null, MQIndexMessage.SYS_USER_INFO_UPDATE, loginUser));
            loginUser.setPassword(null);
            log.info("用户名为 {} 的头像图片地址是 {}", loginUser.getUsername(), url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改图片失败");

            return Result.fail(ResultCode.NO_USER_AVATAR_UPLOAD.getCode(), ResultCode.NO_USER_AVATAR_UPLOAD.getMessage());
        }


        return Result.succ(ResultCode.YES_USER_AVATAR_UPLOAD.getCode(), ResultCode.YES_USER_AVATAR_UPLOAD.getMessage(), loginUser);
    }


    @RequestMapping(value = "/addPicture", method = RequestMethod.POST)
    public Result addP(@RequestBody SysUserUploadAvatarVo sysUserUploadAvatarVo) {

        System.out.println(sysUserUploadAvatarVo);
        return Result.succ(ResultCode.YES_USER_AVATAR_UPLOAD.getCode(), ResultCode.YES_USER_AVATAR_UPLOAD.getMessage());
    }


    /**
     * 修改用户信息。修改个人信息
     * MQ通知更新数据库信息
     *
     * @param sysUserVo
     * @return
     */
    @PostMapping("/updateMyInfo")
    @ApiOperation(value = "修改用户信息。修改个人信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUserVo", value = "更新的信息", dataType = "SysUserVo", paramType = "body")
    public Result updateMyInfo(@RequestBody SysUserVo sysUserVo) {

        String token = req.getHeader(AUTHORIZATION);

        if (StrUtil.isEmpty(token)) {
            return Result.fail(ResultCode.NO_USER_UPDATE_INFO.getCode(), "登录信息过期！请重新登录！");
        }

        if (ObjectUtil.isNull(sysUserVo)) {
            return Result.fail(ResultCode.NO_USER_UPDATE_INFO.getCode(), "信息不完整！请重新输入！");
        }


        // 当前登录用户
        LoginUser loginUser = tokenService.getLoginUser(token);

        if (ObjectUtil.isNull(loginUser)) {
            return Result.fail(ResultCode.USER_ACCOUNT_EXPIRED.getCode(), ResultCode.USER_ACCOUNT_EXPIRED.getMessage() + "！请重新登录！");
        }

        BeanUtil.copyProperties(sysUserVo, loginUser);

        loginUser.setUpdateTime(LocalDateTime.now());

        // loginUser.setUpdateTime();
        // Integer userId = loginUser.getUserId();
        // SysUser sysUser = new SysUser();
        //
        // BeanUtil.copyProperties(sysUserVo,sysUser);
        // sysUser.setUserId(userId);
        //
        // boolean updateById = sysUserService.updateById(sysUser);
        try {
            // 1. 先更新缓存。
            tokenService.refresh(loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(
                    "用户名 {} 在 {} 修改信息失败",
                    loginUser.getUsername(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
            return Result.fail(ResultCode.NO_USER_UPDATE_INFO.getCode(), ResultCode.NO_USER_UPDATE_INFO.getMessage());
        }


        // 2. 再通知消息给mq，更新数据库信息
        amqpTemplate.convertAndSend(
                RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(null, MQIndexMessage.SYS_USER_INFO_UPDATE, loginUser));


        log.info(
                "用户名 {} 在 {} 修改信息成功",
                loginUser.getUsername(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        // Boolean isSuccess = sysUserService.updateMyInfo(userId,sysUserVo);

        return Result.succ(ResultCode.YES_USER_UPDATE_INFO.getCode(), ResultCode.YES_USER_UPDATE_INFO.getMessage(), loginUser);
    }


    @PostMapping("/getAddress")
    public Result getAddress(@RequestBody Address address) {

        System.out.println(address);


        Address address1 = new Address();
        address1.setArea("朝阳区");
        address1.setCity("北京市");
        address1.setProvince("北京市");
        return Result.succ(200, "地址修改成功", address1);
    }


    @GetMapping("/findMySellCarSomeInfo")
    @ApiOperation(value = "查看我卖的全部车的信息-部分信息", httpMethod = "GET", notes = "MP的方式查询、查部分信息。")
    // paramType:  path header  query  body form
    @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "Integer", paramType = "query")
    public Result findMySellCarSomeInfo(@RequestParam(defaultValue = "1", name = "currPage") Integer currentPage,
                                        @RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize) {

        // 1. 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        // TODO 先判断用户的角色，是否是商家，不是的话就往下不查了。

        // mp 分页插件
        Page<CarInfo> page = new Page<>(currentPage, pageSize);

        // 查出用户车辆

        // 2. 分页查出用户车辆
        // 2.1 分页查出该用户对应的所有汽车id
        Page<SellerCar> sellerCarPage = new Page<>(currentPage, pageSize);
        Page<SellerCar> sellerCarIdPage = sellerCarService.page(sellerCarPage,
                new QueryWrapper<SellerCar>().eq("user_id", loginUser.getUserId()).select("car_id"));
        // 用户拥有的车辆
        List<SellerCar> sellerCarList = sellerCarIdPage.getRecords();

        // 若该用户没有在卖的车辆，直接返回
        if (sellerCarList.isEmpty()) {
            return Result.succ(ResultCode.NO_CAR_OF_PERSONAL.getCode(), ResultCode.NO_CAR_OF_PERSONAL.getMessage());
        }


        // 2.2 取出用户车辆的id,
        List<Integer> carIdList = new ArrayList<>();//汽车idList
        sellerCarList.forEach(sellerCar -> {
            carIdList.add(sellerCar.getCarId());// 添加到list中
        });
        // 2.3 通过汽车id列表查出汽车表中汽车部分属性信息
        List<CarInfo> carList = carInfoService.list(new QueryWrapper<CarInfo>()
                .in("car_id", carIdList));

        // 按汽车id查出它的全部图片
        // List<MyCarVo> carVos = new LinkedList<>();
        // List<List<CarPicture>> carPictureList = new LinkedList<>();
        /*        carIds.forEach(id -> {
            MyCarVo myCarVo = new MyCarVo();
            List<CarPicture> carPictures = carPictureService.list(new QueryWrapper<CarPicture>().eq("car_id", id));
            // 全部汽车对应的图片信息
            // carPictureList.add(carPictures);
            myCarVo.setCarPictures(carPictures);

            // 找到该图片集合所属的车辆。通过id找
            for (CarInfo carInfo : carInfos) {
                if (id.equals(carInfo.getCarId())) {
                    // id相等、则找到了就退出、
                    myCarVo.setCarInfo(carInfo);
                    break;
                }
            }
            // 加到返回的集合中
            carVos.add(myCarVo);
        });*/

        CarInfoPageVo carInfoPageVo = new CarInfoPageVo();

        carInfoPageVo.setCarInfos(carList);

        carInfoPageVo.setCurrPage(currentPage);
        carInfoPageVo.setPageSize((int) sellerCarIdPage.getSize());
        carInfoPageVo.setTotalCount(sellerCarIdPage.getTotal());

        // 总页码 = （总记录数 + 每页数据大小 -1）/ 每页数据大小；或者向上取整
        carInfoPageVo.setTotalPage((long) Math.ceil(sellerCarIdPage.getTotal() / pageSize));

        // 返回拥有的车辆信息
        return Result.succ(ResultCode.YES_CAR_OF_PERSONAL.getCode(),
                ResultCode.YES_CAR_OF_PERSONAL.getMessage(), carInfoPageVo);

    }


    /**
     * 暂未使用这个接口，使用的上面那个findMySellCarSomeInfo接口
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findMySellCar")
    @ApiOperation(value = "查看我卖的全部车的信息-全部信息", httpMethod = "GET", notes = "Mybatis-plus自己写sql的方式查询。一对多。全部信息，包括图片。本项目暂未用到这个接口")
    // paramType:  path header  query  body form
    @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "Integer", paramType = "query")
    public Result findMySellCar(@RequestParam(defaultValue = "1", name = "currPage") Integer currentPage,
                                @RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize) {
        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        List<SellerCar> myCarList = sellerCarService.list(new QueryWrapper<SellerCar>().eq("user_id", loginUser.getUserId()).select("car_id"));


        Page<CarInfo> page = new Page<>(currentPage, pageSize);

        IPage<CarInfo> carInfoIPage = carInfoService.findMySellCarByPage(page, loginUser.getUserId());

        CarInfoPageVo carInfoPageVo = new CarInfoPageVo();

        carInfoPageVo.setCarInfos(carInfoIPage.getRecords());

        carInfoPageVo.setCurrPage(currentPage);
        carInfoPageVo.setPageSize((int) carInfoIPage.getSize());
        carInfoPageVo.setTotalCount(carInfoIPage.getTotal());

        // 总页码 = （总记录数 + 每页数据大小 -1）/ 每页数据大小；或者向上取整
        carInfoPageVo.setTotalPage((long) Math.ceil(carInfoIPage.getTotal() / pageSize));

        // BeanUtil.copyProperties(carInfoIPage,carInfoPageVo);

        return Result.succ(ResultCode.YES_CAR_OF_PERSONAL.getCode(),
                ResultCode.YES_CAR_OF_PERSONAL.getMessage(), carInfoPageVo);
    }


    @PutMapping("/updateSysUserPassword")
    @ApiOperation(value = "修改密码", httpMethod = "PUT", notes = "Mybatis的方式查询。一对多。")
    // paramType:  path header  query  body form
    @ApiImplicitParam(name = "passwordVo", value = "密码", dataType = "PasswordVo", paramType = "body")
    public Result updateSysUserPassword(@RequestBody PasswordVo passwordVo) {
        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        // 前后端的userId 不一样，
        if (!loginUser.getUserId().equals(passwordVo.getUserId())) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        // 修改密码
        return sysUserService.updateSysUserPassword(passwordVo, loginUser.getPassword());

    }


    /**
     * 收藏汽车
     *
     * @param carId 汽车编号
     * @return
     */
    @GetMapping("/changeMyCollectCar/{carId}")
    @ApiOperation(value = "收藏汽车", httpMethod = "GET")
    @ApiImplicitParam(name = "carId", value = "收藏的汽车id", dataType = "Integer", paramType = "query")
    public Result changeMyCollectCar(@NotNull @PathVariable(name = "carId") Integer carId) {

        if (carId == null) {
            return Result.fail("未选中需要收藏的汽车");
        }

        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        SysUserCollectCars sysUserCollectCars = new SysUserCollectCars();
        sysUserCollectCars.setCarId(carId);
        sysUserCollectCars.setUserId(loginUser.getUserId());
        sysUserCollectCars.setCreateTime(LocalDateTime.now());

        return sysUserService.changeMyCollectCar(sysUserCollectCars);

    }

    @GetMapping("/getMyCollectCarPage")
    @ApiOperation(value = "获取用户收藏的全部汽车分页获取", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "当前页", dataType = "Integer", paramType = "query")
    })
    public Result getMyCollectCarByPage(@RequestParam(defaultValue = "1", name = "currPage") Integer currentPage,
                                        @RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize) {
        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.USER_NOT_LOGIN.getCode(), ResultCode.USER_NOT_LOGIN.getMessage());
        }

        // List<SysUserCollectCars> list = sysUserCollectCarsService.list(new QueryWrapper<SysUserCollectCars>().eq("user_id", loginUser.getUserId()).select("car_id"));

        // 分页查拥有的汽车的ids
        Page<SysUserCollectCars> page = new Page<>(currentPage, pageSize);
        Page<SysUserCollectCars> carsPage = sysUserCollectCarsService.page(page, new QueryWrapper<SysUserCollectCars>().
                eq("user_id", loginUser.getUserId()).select("car_id").orderByDesc("create_time"));

        // 分页得到的数据
        List<SysUserCollectCars> records = carsPage.getRecords();

        // 若该用户没有收藏的车辆，直接返回
        if (records.isEmpty()) {
            return Result.succ(ResultCode.NO_ALL_MY_COLLECT_CARS.getCode(), ResultCode.NO_ALL_MY_COLLECT_CARS.getMessage());
        }

        // 遍历得到carIdList
        List<Integer> carIds = new LinkedList<>();
        records.stream().forEach(sysUserCollectCars -> {
            carIds.add(sysUserCollectCars.getCarId());
        });

        // 通过ids查所有收藏的汽车列表
        List<CarInfo> carInfos = carInfoService.list(new QueryWrapper<CarInfo>().
                in("car_id", carIds).
                select("car_id", "car_brand", "car_price", "car_picture", "car_title",
                        "car_user_time", "car_car_mileage", "car_displacement", "car_gear_box"));

        // 分页信息
        CarInfoPageVo carInfoPageVo = new CarInfoPageVo();
        carInfoPageVo.setCarInfos(carInfos);
        carInfoPageVo.setCurrPage(currentPage);
        carInfoPageVo.setPageSize((int) carsPage.getSize());
        carInfoPageVo.setTotalCount(carsPage.getTotal());

        // 总页码 = （总记录数 + 每页数据大小 -1）/ 每页数据大小；或者向上取整
        carInfoPageVo.setTotalPage((long) Math.ceil(carsPage.getTotal() / pageSize));

        // 返回收藏的车辆
        return Result.succ(ResultCode.YES_ALL_MY_COLLECT_CARS.getCode(), ResultCode.YES_ALL_MY_COLLECT_CARS.getMessage(), carInfoPageVo);
    }

    @ApiOperation(value = "当前汽车是否收藏", httpMethod = "GET")
    @GetMapping("/isCollectCar/{carId}")
    @ApiImplicitParam(name = "carId", value = "当前汽车id", dataType = "Integer", paramType = "query")
    public Result isCollectCar(@NotNull @PathVariable(name = "carId") Integer carId) {
        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }

        // 查看是否已经收藏
        SysUserCollectCars one = sysUserCollectCarsService.getOne(new QueryWrapper<SysUserCollectCars>().
                eq("car_id", carId).
                eq("user_id", loginUser.getUserId()));
        // 没有收藏,返回false
        if (ObjectUtil.isNull(one)) {
            return Result.succ(false);
        }

        // 已经收藏、返回true
        return Result.succ(true);
    }

    @Transactional
    @DeleteMapping("/deleteMyCar/{carId}")
    @ApiOperation(value = "删除我的汽车信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "carId", value = "删除汽车的Id", dataType = "Integer", paramType = "query")
    public Result deleteMyCarByCarId(@NotNull @PathVariable(value = "carId") Integer carId) {

        // 获取当前用户
        LoginUser loginUser = UserUtil.getLoginUser();

        // 当前未登录,登录异常
        if (ObjectUtil.isNull(loginUser)) {
            throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(), ResultCode.NO_USER_EXCEPTION.getMessage());
        }


        // 1. 删除汽车表信息
        carInfoService.removeById(carId);

        // 2. 删除用户汽车表的信息
        sellerCarService.remove(new QueryWrapper<SellerCar>().eq("car_id", carId));

        // 3.1 删除七牛云上的图片
        List<CarPicture> carPictures = carPictureService.list(new QueryWrapper<CarPicture>().eq("car_id", carId).select("car_url"));
        if (!carPictures.isEmpty()) {
            carPictures.forEach(carPicture -> {
                QiniuUpload.deleteFile(carPicture.getCarUrl());
            });
        }

        // 3.2 删除汽车图片中的信息
        carPictureService.remove(new QueryWrapper<CarPicture>().eq("car_id", carId));

        // 4. 通知mq删除es中的信息
        amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE, RabbitConfig.ES_BIND_KEY,
                new MQIndexMessage(carId, MQIndexMessage.REMOVE));

        return Result.succ(ResultCode.YES_DELETE_CAR.getCode(), ResultCode.YES_DELETE_CAR.getMessage(), null);
    }


}
