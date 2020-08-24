package com.jokey.rabbit.socket.config.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.config.handler
 * @comment:
 */
@Component
public class MySocketHandshakeHandler extends DefaultHandshakeHandler {
	/**
	 * 验证用户信息是否合法
	 *
	 * @param request
	 * @param wsHandler
	 * @param attributes
	 * @return
	 */
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		System.out.println("=============用户信息合法性校验==============");
		return super.determineUser(request, wsHandler, attributes);
	}
}
