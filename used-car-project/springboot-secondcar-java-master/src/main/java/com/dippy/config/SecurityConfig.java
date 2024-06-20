package com.dippy.config;


import com.dippy.filter.TokenFilter;
import com.dippy.filter.VerifyCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 记住我超时时间
    @Value("${rememberMe.out-time}")
    public int REMEMBERME_TIME ;

    @Autowired
    private DataSource dataSource;
    // 自定义的成功失败的处理器
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    // 自定义的登录失败的处理器
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    // @Autowired
    // private  CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler;
    // 自定义的退出的处理器
    // @Autowired
    // private LogoutSuccessHandler logoutSuccessHandler;

    // 自定义的SpringSecurity异常处理的处理器
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    // 自定义的权限不足处理。
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // token过滤器
    @Autowired
    private TokenFilter tokenFilter;

    // 校验验证码的过滤器
    @Autowired
    private VerifyCodeFilter validateCodeFilter;

    // 记住我
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    // 自定义登录逻辑
    @Autowired
    private UserDetailsService userDetailsService;

    // @Bean
    // CorsConfigurationSource corsConfigurationSource(){
    //     return httpServletRequest -> {
    //         CorsConfiguration cfg = new CorsConfiguration();
    //         cfg.addAllowedHeader("*");
    //         cfg.addAllowedMethod("*");
    //         cfg.addAllowedOrigin("http://localhost:8090");
    //         cfg.setAllowCredentials(true);
    //         cfg.checkOrigin("http://localhost:8090");
    //         return cfg;
    //     };
    // }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        //表单提交
        http.formLogin()
                //自定义入参
                .usernameParameter("username")
                .passwordParameter("password")

                // // 登录的页面
                // .loginPage("http://localhost:8080/sysUser/login")

                // 登录的url
                .loginProcessingUrl("/yueChi/sysUser/login")

                // 登录成功跳转的页面,不能和自定义登录成功处理器共存
                // .successForwardUrl("/userInfo/home")
                //自定义登录成功处理器
                .successHandler(authenticationSuccessHandler)

                // 登录失败跳转的页面
                // .failureForwardUrl("/userInfo/about")
                // 自定义登录失败处理器
                .failureHandler(authenticationFailureHandler)

                .and()

                // 授权认证
                .authorizeRequests()
                // 任何都要认证
                .antMatchers().authenticated()
                // 放行登录页面
                .antMatchers("/yueChi/sysUser/login").permitAll()
                .antMatchers("/yueChi/sysUser/logout").permitAll()
                .antMatchers("/carInfo/carInfoIndex").permitAll()
                .antMatchers("/carInfo/searchListPage").permitAll()
                 // 放行汽车详细页
                .antMatchers("/carDetail/**").permitAll()

                .antMatchers("/").permitAll()

                // 放行注销页面
                // .antMatchers("/logout").permitAll()
                // 放行失败页面
                .antMatchers("/userInfo/fail").permitAll()
                // 验证码//通过security过滤连放行
                .antMatchers("/captcha.jpg/**").permitAll()
                // 放行聊天
                .antMatchers("/ws/**").permitAll()

                // 放行的资源，图片css,不需要认证的url等
                .antMatchers("/", "/*.html", "/favicon.ico", "/css/**", "/js/**", "/fonts/**", "/img/**",
                        "/v2/api-docs/**", "/swagger-resources/**", "/webjars/**", "/pages/**", "/druid/**",
                        "/statics/**")
                .permitAll()

                // 基于权限
                // 登录成功页面需要的权限
                // .antMatchers("/userInfo/home").hasAuthority("pre")

                // 关闭csrf
                .and()
                .csrf().disable()
                // 异常处理器
                .exceptionHandling()
                // 没有登录
                .authenticationEntryPoint(authenticationEntryPoint)
                // 异常：权限不足
                .accessDeniedHandler(accessDeniedHandler)

        ;
        //记住我
        http.rememberMe()
                // 参数名
                .rememberMeParameter("rememberMe")
                //设置数据源
                .tokenRepository(persistentTokenRepository)
                //超时时间
                .tokenValiditySeconds(REMEMBERME_TIME)
                //自定义登录逻辑
                .userDetailsService(userDetailsService);

        // 退出
        http.logout()
                .logoutUrl("/api/yueChi/sysUser/logout")
                // .logoutSuccessHandler(logoutSuccessHandler)
                .logoutSuccessUrl("/carInfo/carInfoIndex");

        /* http.logout()
                // .clearAuthentication(true)
                .logoutUrl("/yueChi/sysUser/logout")
                .logoutSuccessUrl("/carInfo/carInfoIndex");*/


        // // 基于token，所以不需要session，但是验证码需要session
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // //将我们自定义的验证码过滤器，配置。自定义验证码过滤器，在其中验证输入的验证码和保存在session中的验证码是否一致
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

        // 过滤token
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 密码加密和解密
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 配置记住我功能所需要的表
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动建表，第一次启动时开启，第二次启动时注释掉
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 认证信息管理，
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 验证密码
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        // auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
        // auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /**
     * 放行验证码
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/captcha.jpg");//不通过security过滤连放行
    }

}
