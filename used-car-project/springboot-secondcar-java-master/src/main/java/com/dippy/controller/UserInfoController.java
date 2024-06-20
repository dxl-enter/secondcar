package com.dippy.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dippy.common.result.Result;
import com.dippy.entity.UserInfo;
import com.dippy.util.UserUtil;
import com.dippy.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dippy
 * @since 2020-12-05
 */
@Slf4j
@RestController
@RequestMapping("/userInfo")
@Api(value = "模块1", tags = "测试接口1")
public class UserInfoController extends BaseController {

    @GetMapping("/index")
    @ApiOperation(value = "用户列表", notes = "查询所有用户信息")
    public Result index() {
        List<UserInfo> userInfoList = userInfoService.list();
        log.error("test1");
        return Result.succ(userInfoList);
    }

    @PostMapping("/fail")
    @ApiOperation(value = "用户列表", notes = "登录失败页面")
    public Result about() {
        log.info("登录失败页面");
        return Result.fail("登录失败！");
    }

    // @GetMapping("/login")
    // @ApiOperation(value = "", notes = "")
    // public Result login() {
    //     // List<UserInfo> userInfoList = userInfoService.list();
    //     log.error(" 重新登录");
    //     return Result.succ("请重新登录！");
    // }

    // //PreAuthorize允许角色以ROLE_开头，也可以不以ROLE_开头，但是配置类不允许以ROLE_开头
    // // @PreAuthorize("hasRole('adc')")
    // @PostMapping("/success")
    // @ApiOperation(value = "用户列表", notes = "登录成功页面")
    // public Result home() {
    //     log.info("登录成功页面");
    //
    //     // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     // LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    //
    //     // 当前登录用户
    //     LoginUser currentUser = currentUser();
    //
    //     LoginUser loginUser = new LoginUser();
    //     BeanUtils.copyProperties(currentUser, loginUser);
    //     loginUser.setPassword(null);
    //
    //     // 返回登录成功的用户信息
    //     return Result.succ(loginUser);
    // }

    /**
     * 获取当前登录的用户信息
     *
     * @return 获取当前登录的用户信息
     */
    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public Result getCurrentUser() {
        // 可能存在尚未登录的
        if (UserUtil.getLoginUser() == null) {
            log.info("尚未登录");
            return Result.fail(401, "您尚未登录！", null);
        }

        log.info("当前登录用户为：{}" + UserUtil.getLoginUser().getUsername());
        return Result.succ(UserUtil.getLoginUser());
    }

    // @PostMapping("/login")
    public Result login(@Validated LoginVO loginVo, HttpServletResponse response) {

        log.info("controller中的login---登录信息{}---", loginVo);
        // 1. 通过username查数据库
        UserInfo user = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("username", loginVo.getUsername()));

        // 2. 用户是否为存在。可以使用断言，也可以自己抛异常
        Assert.notNull(user, "用户不存在");

        // 3. 判断密码正不正确md5加密
        // if (!user.getPassword().equals(SecureUtil.md5(loginVo.getPassword()))) {
        //     // 可以直接返回，也可以抛出异常
        //     return Result.fail("密码不正确");
        // }
        if (!user.getPassword().equals(loginVo.getPassword())) {
            // 可以直接返回，也可以抛出异常
            return Result.fail("密码不正确");
        }

        // 4. 账号密码都正确。生成jwt
        String jwt = jwtUtils.generateToken(user.getId());// 这个jwtUtils中需要传入id，可以用别的jwtUtils对应传入相应参数就行

        // 5. 放在请求头hander中
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        // 6. 返回用户信息。用MapUtil来简化书写，最终都是包装成Map
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())

                .map()
        );
    }




}
