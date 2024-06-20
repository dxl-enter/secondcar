package com.dippy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.dto.LoginUser;
import com.dippy.es.repository.CarInfoRepository;
import com.dippy.es.service.SearchService;
import com.dippy.filter.TokenFilter;
import com.dippy.service.*;
import com.dippy.util.JwtUtils;
import com.dippy.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    // 验证码的串
    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    @Autowired
    public HttpServletRequest req;

    public HttpServletResponse response;

    @Autowired
    public UserInfoService userInfoService;

    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public TokenFilter tokenFilter;

    @Autowired
    public TokenService tokenService;

    @Autowired
    public CarInfoService carInfoService;

    // es
    @Autowired
    public SearchService searchService;

    //rabbitmq
    @Autowired
    public AmqpTemplate amqpTemplate;

    // ES
    @Autowired
    public CarInfoRepository carInfoRepository;

    // 汽车图片
    @Autowired
    public CarPictureService carPictureService;

    // 地址
    @Autowired
    public RegionService regionService;
    @Autowired
    public ShAreaService shAreaService;

    @Autowired
    public SysUserService sysUserService;

    @Autowired
    public SysRoleUserService sysRoleUserService;

    @Autowired
    public SysUserAddressService sysUserAddressService;

    @Autowired
    public SellerCarService sellerCarService;

    @Autowired
    public SysUserCollectCarsService sysUserCollectCarsService;

    @Autowired
    public SysUserChatMessageService sysUserChatMessageService;

    @Autowired
    public SysOrderService sysOrderService;


    // jwt请求头
    @Value("${token.key}")
    public String AUTHORIZATION;

    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 10);
        return new Page(pn, size);
    }


    /**
     * 获取当前登录的用户信息
     *
     * @return 获取当前登录的用户信息
     */
    @ApiOperation(value = "当前登录用户")
    // @GetMapping("/current")
    public LoginUser currentUser() {
        LoginUser currentUser = UserUtil.getLoginUser();
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(currentUser, loginUser);
        loginUser.setPassword(null);
        return currentUser;
    }

    /**
     * 获取登录的用户ID
     * @return ID
     */
    // protected Long getProfileId() {
    //     return getProfile().getId();
    // }

}
