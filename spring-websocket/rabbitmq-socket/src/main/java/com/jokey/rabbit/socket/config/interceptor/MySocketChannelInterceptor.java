package com.jokey.rabbit.socket.config.interceptor;

import com.jokey.rabbit.socket.config.registry.SocketSessionRegistry;
import com.jokey.rabbit.socket.entity.MyPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.config.interceptor
 * @comment: 频道拦截器, 类似管道, 获取消息的一些meta数据
 */
@Component
public class MySocketChannelInterceptor extends ChannelInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MySocketChannelInterceptor.class);
	
	@Autowired
	private SocketSessionRegistry webAgentSessionRegistry;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (Objects.nonNull(headerAccessor) && StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			LOGGER.info("============连接成功==============");
			Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
			if (raw instanceof Map) {
				Object name = ((Map) raw).get("name");
				if (name instanceof LinkedList) {
					String loginName = ((LinkedList) name).get(0).toString();
					//设置当前访问器的认证用户
					headerAccessor.setUser(new MyPrincipal(loginName));
					String sessionId = headerAccessor.getSessionId();
					//fixme 统计用户在线数，可通过redis来实现更好
					webAgentSessionRegistry.registerSessionId(loginName, sessionId);
				}
			}
		} else if (Objects.nonNull(headerAccessor) && StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
			//点击断开连接，这里会执行两次，第二次执行的时候，message.getHeaders.size()=5,第一次是6。直接关闭浏览器，只会执行一次，size是5。
			LOGGER.info("============连接断开==============");
			MyPrincipal principal = (MyPrincipal) message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER);
			//  如果同时发生两个连接，只有都断开才能叫做不在线
			if (message.getHeaders().size() == 5 && principal.getName() != null) {
				String sessionId = headerAccessor.getSessionId();
				webAgentSessionRegistry.unregisterSessionId(principal.getName(), sessionId);
			}
		}
		return super.preSend(message, channel);
	}
	
	/**
	 * 消息发送立即调用
	 *
	 * @param message
	 * @param channel
	 * @param sent
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		//消息头访问器
		StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
		//避免非stomp消息类型，例如心跳检测
		if (stompHeaderAccessor.getCommand() == null) {
			return;
		}
		String sessionId = stompHeaderAccessor.getSessionAttributes().get("sessionId").toString();
		System.out.println("==SocketChannelInterceptor ==>> postSend sessionId = " + sessionId);
		switch (stompHeaderAccessor.getCommand()) {
			case CONNECT:
				connect(sessionId);
				break;
			case DISCONNECT:
				disconnect(sessionId);
				break;
			case SUBSCRIBE:
				
				break;
			
			case UNSUBSCRIBE:
				
				break;
			default:
				break;
		}
		super.postSend(message, channel, sent);
	}
	
	/**
	 * 断开连接
	 *
	 * @param sessionId
	 */
	private void disconnect(String sessionId) {
		//用户下线操作
		//V6UserController.onlineUserMap.remove(sessionId);
	}
	
	/**
	 * 连接成功
	 *
	 * @param sessionId
	 */
	private void connect(String sessionId) {
		System.out.println("connect sessionId=" + sessionId);
	}
	
	/**
	 * 消息发送之后进行调用,是否有异常,进行数据清理
	 *
	 * @param message
	 * @param channel
	 * @param sent
	 * @param ex
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		System.out.println("==SocketChanelInterceptor==>>afterSendCompletion");
		super.afterSendCompletion(message, channel, sent, ex);
	}
}
