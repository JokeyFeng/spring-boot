package com.jokey.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:01
 * @comment
 */
@Configuration
// @EnableWebSocket // 无需添加该注解，因为我们并不是使用 Spring WebSocket
public class WebsocketConfiguration {

    /**
     * 该 Bean 的作用，是扫描添加有 @ServerEndpoint 注解的 Bean
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
