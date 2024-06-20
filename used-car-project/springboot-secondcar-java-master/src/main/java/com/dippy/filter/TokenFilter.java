package com.dippy.filter;

import com.dippy.dto.LoginUser;
import com.dippy.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 */
@Component
public class TokenFilter extends OncePerRequestFilter {


    // public static final String TOKEN_KEY = "authorization";
    @Value("{token.key}")
    public String TOKEN_KEY;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    // 快过期时间
    private static final Long MINUTES_10 = 10 * 60 * 1000L;

    /**
     * 每次检查是否已经登录、从上下文中获取登录用户
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        // 当前token不为空
        if (StringUtils.isNotBlank(token)) {
            // 根据JWTtoken查询登录用户
            LoginUser loginUser = tokenService.getLoginUser(token);
            if (loginUser != null) {
                // 检查登录时间
                loginUser = checkLoginTime(loginUser);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());

                // 通过上下文可以拿到当前登录用户
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 校验时间<br>
     * 过期时间与当前时间对比，临近过期10分钟内的话，自动刷新缓存
     *
     * @param loginUser
     * @return
     */
    private LoginUser checkLoginTime(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        // token快过期了，则根据当前用户和当前时间更新token
        if (expireTime - currentTime <= MINUTES_10) {
            // 1. 查出当前token
            String token = loginUser.getToken();
            // 2. 类似于重新登录刷新token
            loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
            // 3. 更新token
            loginUser.setToken(token);
            tokenService.refresh(loginUser);
        }
        return loginUser;
    }

    /**
     * 根据参数或者header获取token
     *
     * @param request
     * @return
     */
    public static  String getToken(HttpServletRequest request) {
        // String token = request.getParameter(TOKEN_KEY);
        String token = request.getParameter("authorization");

        if (StringUtils.isBlank(token)) {
            token = request.getHeader("authorization");
        }

        return token;
    }

}
