package com.dippy.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.dippy.dto.LoginUser;
import com.dippy.dto.Token;
import com.dippy.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token存到redis的实现类<br>
 * jwt实现的token
 */
@Primary
@Service
@Slf4j
public class TokenServiceJWTImpl implements TokenService {

    // private static final Logger log = LoggerFactory.getLogger("adminLogger");

    /**
     * token过期秒数
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;

    @Autowired
    private RedisTemplate<String, LoginUser> redisTemplate;
    /**
     * 私钥
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    private static Key KEY = null;

    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    /**
     * 生成JWTToken
     *
     * @param loginUser
     * @return token
     */
    @Override
    public Token saveToken(LoginUser loginUser) {
        // UUID作为KEY保存到redis中
        loginUser.setToken(UUID.randomUUID().toString());
        // log.info("生成的UUID为：{}",loginUser.getToken());
        // 辅助函数，登录用户保存到缓存中
        cacheLoginUser(loginUser);
        // // 登陆日志
        // logService.save(loginUser.getId(), "登陆", true, null);

        // 生成JWTtoken
        String jwtToken = createJWTToken(loginUser);
        log.info("生成的jwttoken为：{}", jwtToken);

        // 登录成功后，返回token。在SecurityHandlerConfig登录成功的处理器中调用
        return new Token(jwtToken, loginUser.getLoginTime());
    }

    /**
     * 生成jwt
     *
     * @param loginUser
     * @return
     */
    private String createJWTToken(LoginUser loginUser) {
        Map<String, Object> claims = new HashMap<>();
        // 也可以放userID。
        // claims.put("userID",loginUser.getUserId());
        claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;
    }

    /**
     * 登录用户保存到Redis缓存中
     *
     * @param loginUser
     */
    private void cacheLoginUser(LoginUser loginUser) {
        // 缓存登录时间
        loginUser.setLoginTime(System.currentTimeMillis());
        // 缓存过期时间
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
        // 根据uuid将loginUser缓存
        redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);

        // redisTemplate.opsForValue()
        // redisTemplate.opsForValue().set("键名", loginUser,expireSeconds,TimeUnit.SECONDS);


    }

    /**
     * 更新缓存的用户信息
     */
    @Override
    public void refresh(LoginUser loginUser) {
        cacheLoginUser(loginUser);
    }

    /**
     * 根据Token获取当前登录用户
     *
     * @param jwtToken
     * @return
     */
    @Override
    public LoginUser getLoginUser(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
        }

        return null;
    }

    /**
     * 删除Token
     *
     * @param jwtToken
     * @return
     */
    @Override
    public boolean deleteToken(String jwtToken) {
        log.info("delete jwtToken:" + jwtToken);

        String uuid = getUUIDFromJWT(jwtToken);
        log.info("delete uuid:" + uuid);
        if (uuid != null) {
            String key = getTokenKey(uuid);
            log.info("delete key:" + key);
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            if (loginUser != null) {
                redisTemplate.delete(key);
                // // 退出日志
                // logService.save(loginUser.getId(), "退出", true, null);
                return true;
            }
        }

        return false;
    }

    /**
     * 获取UUID
     *
     * @param uuid
     * @return
     */
    private String getTokenKey(String uuid) {
        return "tokens:" + uuid;
    }

    /**
     * jwtSecret
     * 单例
     *
     * @return
     */
    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (TokenServiceJWTImpl.class) {
                if (KEY == null) {// 双重锁
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }

        return KEY;
    }

    /**
     * 解析Token
     *
     * @param jwtToken
     * @return
     */
    private String getUUIDFromJWT(String jwtToken) {
        if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
            return null;
        }

        try {
            Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
            // 返回，通过key（LOGIN_USER_KEY）查出来的jwtClaims值转换成的字符串
            return MapUtil.get(jwtClaims, LOGIN_USER_KEY, String.class);
            // return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
        } catch (ExpiredJwtException e) {
            log.error("JWT: {}已过期", jwtToken);
            return null;
        } catch (Exception e) {
            log.error("{}", (Object) e.getStackTrace());
        }

        return null;
    }
}
