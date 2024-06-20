package com.dippy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.common.result.Result;
import com.dippy.entity.SysUser;
import com.dippy.entity.SysUserCollectCars;
import com.dippy.es.vo.AddressVo;
import com.dippy.util.PageUtils;
import com.dippy.vo.LoginVO;
import com.dippy.vo.PasswordVo;
import com.dippy.vo.RegisterVo;
import com.dippy.vo.SysUserVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 *
 * @author dippy
 * @email ${email}
 * @date 2021-04-09 23:20:07
 */
public interface SysUserService extends IService<SysUser> {

    PageUtils queryPage(Map<String, Object> params);

    String uploadAvg(String filePath,String fileName);

    String addressFill(AddressVo addressVo);

    /**
     * 登录后返回token
     * @param loginVO
     * @param request
     * @return
     */
    Result login(LoginVO loginVO, HttpServletRequest request);

    Boolean register(RegisterVo registerVo);

    String uploadPicture(MultipartFile multipartFile);

    /**
     * 修改个人信息
     * @param sysUserVo
     * @return
     */
    Boolean updateMyInfo(SysUserVo sysUserVo);

    /**
     * 更新密码
     * @param passwordVo 旧、新密码、当前登录id
     * @param password 已经加密的旧密码
     * @return
     */
    Result updateSysUserPassword(PasswordVo passwordVo,String password);


    /**
     * 用户收藏汽车或取消收藏
     * @param sysUserCollectCars
     * @return
     */
    Result changeMyCollectCar(SysUserCollectCars sysUserCollectCars);
}

