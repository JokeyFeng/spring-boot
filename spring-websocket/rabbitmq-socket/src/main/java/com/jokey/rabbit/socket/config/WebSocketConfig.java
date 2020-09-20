package com.jokey.rabbit.socket.config;

import com.jokey.rabbit.socket.config.handler.MySocketHandshakeHandler;
import com.jokey.rabbit.socket.config.interceptor.AuthHandshakeInterceptor;
import com.jokey.rabbit.socket.config.interceptor.MySocketChannelInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.config
 * @comment:
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);
	
	@Autowired
	private MySocketChannelInterceptor mySocketChannelInterceptor;
	
	@Autowired
	private AuthHandshakeInterceptor authHandshakeInterceptor;
	
	@Autowired
	private MySocketHandshakeHandler mySocketHandshakeHandler;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic", "/queue")
				.setRelayHost("www.rabbitmq.com")
				// rabbitmq-stomp 服务器服务端口
				.setRelayPort(61613)
				.setClientLogin("root")
				.setClientPasscode("123456");
		//定义一对一推送的时候前缀
		registry.setUserDestinationPrefix("/bad/");
		//客户端需要把消息发送到/message/xxx地址
		registry.setApplicationDestinationPrefixes("/message");
		LOGGER.info("init Rabbitmq Web-socket MessageBroker completed.");
	}
	
	/**
	 * 连接站点配置
	 *
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins("*")
				.setHandshakeHandler(mySocketHandshakeHandler)
				.addInterceptors(authHandshakeInterceptor)
				.withSockJS();
		LOGGER.info("init Rabbitmq Web-socket endpoint ");
	}
	
	/**
	 * 输入通道配置
	 *
	 * @param registration
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(mySocketChannelInterceptor);
		registration.taskExecutor()
				.corePoolSize(30)
				.maxPoolSize(60)
				.keepAliveSeconds(60);
	}
	
	/**
	 * 消息传输参数配置
	 *
	 * @param registration
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.setSendTimeLimit(15 * 1000)
				.setSendBufferSizeLimit(512 * 1024)
				.setMessageSizeLimit(128 * 1024);
	}
}
