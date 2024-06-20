package com.dippy.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@Slf4j
@RestController
public class KaptchaController extends BaseController {

    // 验证码的串
    @Value("${kaptcha.key}")
    private String KAPTCHA_SESSION_KEY;

    // kaptcha生成图片验证码
    @Autowired
    private Producer producer;

    // private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 生成验证码
     *
     * @param resp
     * @throws IOException
     */
    @PermitAll//注解放行
    @GetMapping("/captcha.jpg")
    public void kaptcha(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 验证码的文字
        String getCaptcha = producer.createText();
        // 图片
        BufferedImage image = producer.createImage(getCaptcha);

        /*
            登录注册是一次会话的，利用这个将验证码存入session中。
             验证码也可以用shiro会话管理器进行存储
        */
        req.getSession().setAttribute(KAPTCHA_SESSION_KEY, getCaptcha);
        // ServletWebRequest webRequest = new ServletWebRequest(req);

        // sessionStrategy.setAttribute(webRequest, KAPTCHA_SESSION_KEY, text);
        // String getCaptcha = (String) sessionStrategy.getAttribute(webRequest, KAPTCHA_SESSION_KEY);

        log.info("生成的getCaptcha: {}" ,getCaptcha);

        // 让浏览器不缓存这个验证码，因为每次刷新都要更新验证码
        resp.setHeader("Cache-Control", "no-store, no-cache");
        // 设置resp流为图片
        resp.setContentType("image/jpeg");
        // 验证码输出到页面上
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }
}
