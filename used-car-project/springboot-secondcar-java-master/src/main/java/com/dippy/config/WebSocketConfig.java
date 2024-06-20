package com.dippy.config;

import cn.hutool.core.util.ObjectUtil;
import com.dippy.common.handle.MyException;
import com.dippy.common.result.ResultCode;
import com.dippy.dto.LoginUser;
import com.dippy.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${token.key}")
    public String tokenKey;

    @Autowired
    private TokenService tokenService;

    /**
     * 添加端点 这样可以在网页连接webSocket
     * 也就是配置websocket的服务地址，并且可以指定是否使用socketJS
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 1. 将ws/ep路径注册为端点，用户连接这个端点就可以进行websocket通讯，支持socketJS
        // 2. setAllowedOrigins: 允许跨域
        // 3. withSockJS: 支持socketJs访问
        registry.addEndpoint("ws/ep").setAllowedOrigins("*").withSockJS();
    }


    /**
     * 不用JWT的时候才用配这个。
     * 这个主要是为了获取到JWT，以免被拦截
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                // 判断是否是连接，若是则获取token,并设置用户对象
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // 获取前端传来的token
                    String token = accessor.getFirstNativeHeader(tokenKey);
                    // 通过token获取当前登录用户
                    LoginUser loginUser = tokenService.getLoginUser(token);
                    // 当前登录用户为null,有异常，则重新登录
                    if (ObjectUtil.isNull(loginUser)) {
                       throw new MyException(ResultCode.NO_USER_EXCEPTION.getCode(),ResultCode.NO_USER_EXCEPTION.getMessage());
                    }

                    // 已经登录、则设置用户对象
                    accessor.setUser(SecurityContextHolder.getContext().getAuthentication());
                }
                return message;
            }
        });
    }


    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 配置代理域，可以配置多个，配置的目的地前缀为/queue，可以在配置域上向客户端推送消息。即前端通过/queue接收

        registry.enableSimpleBroker("/queue");
    }
}
