package com.jokey.bingo.socket;

import com.jokey.bingo.interceptor.MyHandshakeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author jokey
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //配置webSocket路径
        registry.addHandler(messageWebSocketHandler(), "/msg-websocket")
                .addInterceptors(new MyHandshakeInterceptor())
                .setAllowedOrigins("*");
        //配置webSocket路径 支持前端使用socketJs
        registry.addHandler(messageWebSocketHandler(), "/sockjs/msg-websocket")
                .setAllowedOrigins("*")
                .addInterceptors(new MyHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public MessageWebSocketHandler messageWebSocketHandler() {
        logger.info("......创建MessageWebSocketHandler......");
        return new MessageWebSocketHandler();
    }
}
