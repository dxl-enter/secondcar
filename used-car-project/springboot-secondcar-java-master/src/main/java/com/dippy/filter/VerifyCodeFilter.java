package com.dippy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 先校验验证码的过滤器
 */
@Component
@Slf4j
public class VerifyCodeFilter extends OncePerRequestFilter {

    // 登录的url
    private String defaultFileFilerProcessUrl = "/sysUser/login";

    // session中存的验证码对应的key
    private String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    // session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Value("${login.needVerifyCode}")
    private Boolean needsVerifyCode;

    // @Override
    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    //     HttpServletRequest req = (HttpServletRequest) request;
    //     HttpServletResponse resp = (HttpServletResponse) response;
    //     // 如果是登录请求，则校验验证码
    //     if ("POST".equalsIgnoreCase(req.getMethod()) && defaultFileFilerProcessUrl.equals(req.getServletPath())) {
    //         // 验证码验证
    //         // 获取前端输入的验证码
    //         String requestCaptcha = req.getParameter("code");
    //         // 获取后端生成的验证码
    //         String getCaptcha = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
    //         log.info("后端的验证码为： {}", getCaptcha);
    //         if (getCaptcha.isEmpty())
    //             throw new AuthenticationServiceException("session的验证码是空的");
    //         if (StringUtils.isEmpty(requestCaptcha))
    //             throw new AuthenticationServiceException("验证码不能为空");
    //         if (!getCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase()))
    //             throw new AuthenticationServiceException("验证码错误");
    //     }
    //     // 不是登录请求放行
    //     chain.doFilter(req, resp);
    // }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果是登录请求，并且开启验证码，则校验验证码
        if (needsVerifyCode && "POST".equalsIgnoreCase(request.getMethod()) && defaultFileFilerProcessUrl.equals(request.getServletPath())) {
            // 验证码验证
            // 获取前端输入的验证码
            String requestCaptcha = request.getParameter("code");
            // 获取后端生成的验证码
            // String getCaptcha = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
            ServletWebRequest webRequest = new ServletWebRequest(request);
            String getCaptcha = (String) sessionStrategy.getAttribute(webRequest, KAPTCHA_SESSION_KEY);
            log.info("后端的验证码为： {}", getCaptcha);
            if (getCaptcha.isEmpty())
                throw new AuthenticationServiceException("session的验证码是空的");
            if (StringUtils.isEmpty(requestCaptcha))
                throw new AuthenticationServiceException("验证码不能为空");
            if (!getCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase()))
                throw new AuthenticationServiceException("验证码错误");
        }
        // 不是登录请求放行
        filterChain.doFilter(request, response);
    }



    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //     // 如果是登录请求，则校验验证码
    //     if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFileFilerProcessUrl.equals(request.getServletPath())) {
    //         // 验证码验证
    //         // 获取前端输入的验证码
    //         String requestCaptcha = request.getParameter("code");
    //
    //
    //         // 获取请求体。
    //         BufferedReader br = request.getReader();
    //         String inputLine;
    //         String str = "";
    //         try {
    //             while ((inputLine = br.readLine()) != null) {
    //                 str += inputLine;
    //             }
    //             br.close();
    //         } catch (IOException e) {
    //             System.out.println("IOException: " + e);
    //         }
    //         // {"username":"admin","password":"admin","code":"yy8a3","rememberMe":false}
    //         // System.out.println("str:" + str);
    //
    //         // String[] split = str.split(",");
    //         // String[] split2 = split[2].split(":");
    //         // String replace = split2[1].replace("\"", "");
    //
    //
    //         // 获取后端生成的验证码
    //         String getCaptcha = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
    //         // ServletWebRequest webRequest = new ServletWebRequest(request);
    //         // String getCaptcha = (String) sessionStrategy.getAttribute(webRequest, KAPTCHA_SESSION_KEY);
    //         log.info("getSession后端的验证码为： {}", getCaptcha);
    //         if (getCaptcha.isEmpty())
    //             throw new AuthenticationServiceException("session的验证码是空的");
    //         if (StringUtils.isEmpty(requestCaptcha))
    //             throw new AuthenticationServiceException("验证码不能为空");
    //         if (!getCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase()))
    //             throw new AuthenticationServiceException("验证码错误");
    //     }
    //     // 不是登录请求放行
    //     filterChain.doFilter(request, response);
    // }



}
