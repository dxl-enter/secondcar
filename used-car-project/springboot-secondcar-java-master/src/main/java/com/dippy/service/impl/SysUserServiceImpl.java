package com.dippy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.common.handle.MyException;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import com.dippy.entity.*;
import com.dippy.es.vo.AddressVo;
import com.dippy.mapper.RegionMapper;
import com.dippy.mapper.SysRoleUserMapper;
import com.dippy.mapper.SysUserCollectCarMapper;
import com.dippy.mapper.SysUserMapper;
import com.dippy.service.SysUserService;
import com.dippy.util.PageUtils;
import com.dippy.util.QiniuCloudUtil;
import com.dippy.util.Query;
import com.dippy.util.RedisUtil;
import com.dippy.vo.LoginVO;
import com.dippy.vo.PasswordVo;
import com.dippy.vo.RegisterVo;
import com.dippy.vo.SysUserVo;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;


@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    // 设置需要操作的账号的AK和SK
    @Value("qiniuyun.access-key")
    private String ACCESSKEY;
    @Value("qiniuyun.secret-key")
    private String SECRETKEY;

    // 要上传的空间
    @Value("qiniuyun.bucket-name")
    private String BUCKETNAME;

    @Value("qiniuyun.qiniuURL")
    private String QINIUURL;

    @Value("${address.region}")
    private String region;// 全部详细信息

    @Value("${redisKeys.myCollectCars}")
    private String myCollectCars;// redis中用户收藏的汽车的键

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RegionMapper regionMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    // 用户收藏的汽车
    @Autowired
    private SysUserCollectCarMapper sysUserCollectCarMapper;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                new QueryWrapper<SysUser>()
        );

        return new PageUtils(page);
    }

    @Override
    public String uploadAvg(String filePath, String fileName) {

        String upload = null;
        QiniuCloudUtil qiniuCloudUtil = new QiniuCloudUtil();
        try {
            upload = QiniuCloudUtil.upload(filePath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return upload;
    }

    @Override
    public String addressFill(AddressVo addressVo) {

        if (ObjectUtil.isNull(addressVo)) {
            throw new MyException(ResultCode.EXCEPTION_NO_ADDRESS.getCode(), ResultCode.EXCEPTION_NO_ADDRESS.getMessage());
        }

        // 当前登录用户
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("user_id", addressVo.getId()).select("address_id").isNotNull("address_id"));

        // 用户的地址
        // redisUtil.sGet();
        // redisUtil.hHasKey()
        Region region = regionMapper.selectById(addressVo.getAreaId());
        region.getSname();


        return null;
    }

    @Override
    public Result login(LoginVO loginVO, HttpServletRequest request) {


        return null;
    }

    /**
     * 注册
     *
     * @return 成功与否
     */
    @Transactional // 两表、事务控制
    @Override
    public Boolean register(RegisterVo registerVo) {

        // 加密
        String encode = passwordEncoder.encode(registerVo.getPassword());
        registerVo.setPassword(encode);
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(registerVo, sysUser);
        sysUser.setCreateTime(LocalDateTime.now());
        // 是否锁定、默认不锁定.1正常，0锁定，2作废
        sysUser.setIsLock(1);
        // 插入
        try {
            this.sysUserMapper.insert(sysUser);
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("{} 注册失败, 错误信息：\\n {}", registerVo.getUsername(), e);
            throw new MyException(ResultCode.NO_USER_REGISTER.getCode(), ResultCode.NO_USER_REGISTER.getMessage());
        }

        // SysUser sysUser1 = this.sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", registerVo.getUsername()).select("user_id"));

        // 并将role_user表更新
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(registerVo.getRoleId());
        sysRoleUser.setUserId(sysUser.getUserId());// 获取插入后的用户的id

        int insert = 0;
        try {
            insert = this.sysRoleUserMapper.insert(sysRoleUser);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 注册失败, 错误信息：\\n {}", registerVo.getUsername(), e);
            throw new MyException(ResultCode.NO_USER_REGISTER.getCode(), ResultCode.NO_USER_REGISTER.getMessage());
        }


        return insert != 0;
    }

    /**
     * 上传头像
     *
     * @param multipartFile
     * @return
     */


    @Override
    public String uploadPicture(MultipartFile multipartFile) {
        String qiniuUrl = QINIUURL;//七牛的外链域名，测试域名
        Configuration configuration = new Configuration(com.qiniu.storage.Region.region2());
        UploadManager uploadManager = new UploadManager(configuration);
        String accessKey = ACCESSKEY;//AK
        String secretKey = SECRETKEY;//SK
        String bucket = BUCKETNAME;//你的存储空间名称
        String key = getRandomCharacterAndNumber(10) + ".png";//生成随机文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String uptoken = auth.uploadToken(bucket);
        String responseUrl = "";
        try {
            byte[] localFile = multipartFile.getBytes();
            Response response = uploadManager.put(localFile, key, uptoken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            responseUrl = responseUrl + qiniuUrl + putRet.key;
        } catch (QiniuException e) {
            Response r = e.response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseUrl;
    }

    @Override
    public Boolean updateMyInfo(SysUserVo sysUserVo) {

        // getLoginUser
        SysUserAddress sysUserAddress = new SysUserAddress();
        BeanUtil.copyProperties(sysUserVo, sysUserAddress);


        return null;
    }


    /**
     * 更改密码
     *
     * @param passwordVo 旧、新密码、当前登录id
     * @param password   已经加密的旧密码
     * @return
     */
    @Override
    public Result updateSysUserPassword(PasswordVo passwordVo, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean matches = encoder.matches(passwordVo.getOldPass(), password);

        // 判断旧密码是否正确
        if (!matches) {
            // 不正确，抛出密码不正确
            throw new MyException(ResultCode.USER_CREDENTIALS_ERROR.getCode(), ResultCode.USER_CREDENTIALS_ERROR.getMessage());
            // return Result.fail(ResultCode.USER_CREDENTIALS_ERROR.getCode(), ResultCode.USER_CREDENTIALS_ERROR.getMessage());
        }
        // 正确则根据id更新密码
        // 密码加密
        password = encoder.encode(passwordVo.getPassword());
        int update = sysUserMapper.update(null,
                new UpdateWrapper<SysUser>()
                        .eq("user_id", passwordVo.getUserId())
                        .set("password", password)
        );
        // 更新成功
        return update == 1 ? Result.succ(ResultCode.YES_UPDATE_PASSWORD.getCode(), ResultCode.YES_UPDATE_PASSWORD.getMessage()) :
                Result.fail(ResultCode.NO_UPDATE_PASSWORD.getCode(), ResultCode.NO_UPDATE_PASSWORD.getMessage());

    }


    /**
     * 用户收藏汽车或取消收藏
     * @param sysUserCollectCars
     * @return
     */
    @Override
    public Result changeMyCollectCar(SysUserCollectCars sysUserCollectCars) {
        // 1. 查看用户收藏汽车的这个
        SysUserCollectCars selectOne = sysUserCollectCarMapper.selectOne(new QueryWrapper<SysUserCollectCars>()
                .eq("user_id", sysUserCollectCars.getUserId())
                .eq("car_id", sysUserCollectCars.getCarId())
                .select("car_id")
        );

        // 2. 没有则加入收藏
        if (ObjectUtil.isNull(selectOne)) {
            sysUserCollectCarMapper.insert(sysUserCollectCars);
            // 数据库和缓存一致性
            redisTemplate.boundValueOps(myCollectCars).set(1);
            return Result.succ(ResultCode.YES_COLLECT_CAR.getCode(), ResultCode.YES_COLLECT_CAR.getMessage(), true);
        }

        // 3. 有就把收藏的取消。
        sysUserCollectCarMapper.delete(new QueryWrapper<SysUserCollectCars>()
                .eq("user_id", sysUserCollectCars.getUserId())
                .eq("car_id", sysUserCollectCars.getCarId())
        );
        redisTemplate.boundValueOps(myCollectCars).set(-1);
        return Result.succ(ResultCode.NO_COLLECT_CAR.getCode(),ResultCode.NO_COLLECT_CAR.getMessage(),false);
    }


    public static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
