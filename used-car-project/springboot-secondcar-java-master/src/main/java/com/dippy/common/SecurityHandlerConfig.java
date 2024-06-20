package com.dippy.common;

import cn.hutool.core.util.ObjectUtil;
import com.dippy.common.result.Result;
import com.dippy.dto.LoginUser;
import com.dippy.dto.Token;
import com.dippy.filter.TokenFilter;
import com.dippy.service.TokenService;
import com.dippy.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * spring security处理器，
 * 登录成功
 * 登录失败
 * 退出登录
 * 异常处理
 */
@Configuration
@Slf4j
public class SecurityHandlerConfig {

    // public String LOGIN_URL = "/yueChi/sysUser/login";
    public String LOGIN_URL = "/carInfo/carInfoIndex";
    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenFilter tokenFilter;

    @Value("${token.key}")
    public String AUTHORIZATION;

    // 上一次请求页面
    @Autowired
    private SecurityProperties securityProperties;
    /*

    登录成功处理器需要继承 SavedRequestAwareAuthenticationSuccessHandler 类.这个类记住了上一次的请求路径。

    若是前后端分离项目则实现接口既可。
     */


    /**
     * 登陆成功，返回Token
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                log.info("执行了SecurityHandlerConfig登录成功处理器------");
                // 获取登录用户
                // UserInfo loginUser1 = (UserInfo) authentication.getPrincipal();

                LoginUser loginUser = (LoginUser) authentication.getPrincipal();

                // 获取token
                Token token = tokenService.saveToken(loginUser);


                // 设置响应头
                response.setHeader(AUTHORIZATION, token.getToken());
                response.setHeader("Access-control-Expose-Headers", AUTHORIZATION);
                // 设置跨域
                // response.setHeader("Access-Control-Allow-Origin", "http://localhost");
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                // 允许带有cookie访问
                response.setHeader("Access-Control-Allow-Methods", "*");
                response.setContentType("application/json;charset=utf-8");
/*                Access to XMLHttpRequest at 'http://localhost:8090/yueChi/sysUser/login' from origin 'http://localhost:8080'
                has been blocked by CORS policy:
                The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*'
                when the request's credentials mode is 'include'. The credentials mode of requests initiated
                by the XMLHttpRequest is controlled by the withCredentials attribute.*/

/*
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080/");
                Access to XMLHttpRequest at 'http://localhost:8090/yueChi/sysUser/login' from origin 'http://localhost:8080'
                has been blocked by CORS policy: The 'Access-Control-Allow-Origin' header has a value 'http://localhost:8090/'
                that is not equal to the supplied origin.*/


/*                      response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
          No 'Access-Control-Allow-Origin' header is present on the requested resource.*/



                RequestCache cache = new HttpSessionRequestCache();
                SavedRequest savedRequest = cache.getRequest(request, response);

                // response.getWriter().write(JSONUtil.toJsonStr(loginUser));
                // response.sendRedirect("/carInfo/carInfoIndex");

               if (ObjectUtil.isNull(savedRequest)) {
                    log.error("登录前的url是空的");
                    request.getRequestDispatcher("/yueChi/sysUser/success").forward(request, response);
                    // response.sendRedirect("/carInfo/carInfoIndex");
                }

                String url = savedRequest.getRedirectUrl();
                request.getRequestDispatcher(url).forward(request, response);
                response.sendRedirect(url);

                // // 返回的结果集
                // Result.succ(HttpStatus.OK.value(), "登录成功1111", token.getToken());


                // ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), token);

                // response.sendRedirect("redirect:/carInfo/carInfoIndex");

                // 转发
                // TODO 为什么转发到carInfoIndex不能进行跳转.因为只能使用post、而/carInfo/carInfoIndex是get请求
                // request.getRequestDispatcher("/yueChi/sysUser/success").forward(request, response);

                // request.getRequestDispatcher("/userInfo/login").forward(request, response);
            }
        };
    }

    /**
     * 登录失败
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) {

                String msg = null;
                if (exception instanceof BadCredentialsException) {
                    msg = "密码错误";
                } else {
                    msg = exception.getMessage();
                }
                log.error("登录失败！{}", msg);
                // 返回的结果集
                Result result = new Result(HttpStatus.UNAUTHORIZED.value(), msg, msg);
                // Result.fail(HttpStatus.UNAUTHORIZED.value(), msg, msg);
                
                // Result result1 = new Result(HttpStatus.UNAUTHORIZED.value(), msg,msg);
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), result);
            }
        };
    }

    /**
     * 权限不足
     *
     * @return
     */
    @Bean(name = "authenticationSuccessHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException {
                log.error("SpringSecurity异常处理执行了----权限不足----");
                //响应状态
                // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                //返回json格式
                response.setHeader("Content-Type", "application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
                writer.flush();
                writer.close();
            }
        };
    }

    /**
     * 异常处理 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) {
                log.info("请先登录");
                // 返回的结果集
                // Result.fail(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
                // ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), "请先登录");
            }
        };
    }

    /**
     * 退出处理
     *
     * @return
     */
    // @Bean
    // public LogoutSuccessHandler logoutSussHandler() {
    //     return new LogoutSuccessHandler() {
    //
    //         @Override
    //         public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    //                                     Authentication authentication) throws IOException, ServletException {
    //
    //             try {
    //                 String token = request.getHeader(AUTHORIZATION);
    //                 tokenService.deleteToken(token);
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //
    //             // String token = TokenFilter.getToken(request);
    //             // tokenService.deleteToken(token);
    //
    //             // ResponseUtil.responseJson(response, HttpStatus.OK.value(), "退出");
    //         }
    //     };
    //
    // }

    // @Bean
    // public LogoutSuccessHandler logoutSussHandler() {
    //     return new LogoutSuccessHandler() {
    //         @Override
    //         public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    //                                     Authentication authentication) {
    //             // 根据request域获取头token的值
    //             String token = request.getHeader(AUTHORIZATION);
    //             /*     if (token == null) {
    //                 try {
    //                     // 重定向
    //                     // response.sendRedirect("/userInfo/login");
    //                     log.error("退出异常!");
    //                     response.getWriter().write("退出异常！");
    //                     // 转发
    //                     request.getRequestDispatcher("/").forward(request, response);
    //                     // Result.succ(HttpStatus.OK.value(), "退出成功", "退出成功");
    //                     return;
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 } catch (ServletException e) {
    //                     e.printStackTrace();
    //                 }
    //             }*/
    //
    //             LoginUser loginUser = tokenService.getLoginUser(token);
    //             /*if (loginUser == null) {
    //                 try {
    //                     // 转发
    //                     response.getWriter().write("退出异常！");
    //                     request.getRequestDispatcher("/").forward(request, response);
    //                     // request.getRequestDispatcher("/userInfo/success").forward(request, response);
    //                     // response.sendRedirect(LOGIN_URL);
    //                     // 登录异常返回的结果集
    //                     return;
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 } catch (ServletException e) {
    //                     e.printStackTrace();
    //                 }
    //             }*/
    //
    //             // String token = tokenFilter.getToken(request);
    //             boolean deleteToken = tokenService.deleteToken(token);
    //
    //             log.info("当前登录用户: {} 于 {} 退出了", loginUser.getUsername(), DateUtil.now());
    //             if (deleteToken) {
    //                 // 返回的结果集
    //                 Result.succ(HttpStatus.OK.value(), "退出成功", "退出成功");
    //                 try {
    //                     request.getRequestDispatcher(LOGIN_URL).forward(request, response);
    //                     //重定向到退出成功登陆页面
    //                     // response.sendRedirect(LOGIN_URL);
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 } catch (ServletException e) {
    //                     e.printStackTrace();
    //                 }
    //             } else {
    //                 try {
    //                     // Result.succ(HttpStatus.PRECONDITION_FAILED.value(), "退出失败请检查是否已经登录！", "退出失败，请检查是否已经登录");
    //                     response.getWriter().write("退出异常");
    //                     //重定向到退出成功登陆页面
    //                     // request.getRequestDispatcher("/userInfo/success").forward(request, response);
    //                     // request.getRequestDispatcher("/").forward(request, response);
    //
    //                     // response.sendRedirect(LOGIN_URL);
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //
    //             ResponseUtil.responseJson(response, HttpStatus.OK.value(), "退出成功");
    //         }
    //     };
    //
    // }

}
