package com.jokey.socket.message.handler;

import com.jokey.socket.message.Message;

import javax.websocket.Session;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:34
 * @comment 消息处理器
 * 每个客户端发起的 Message 消息类型，我们会声明对应的 MessageHandler 消息处理器。
 * 这个就类似在 SpringMVC 中，每个 API 接口对应一个 Controller 的 Method 方法
 */
public interface MessageHandler<T extends Message> {
	
	/**
	 * 执行处理消息
	 * 定义了泛型 <T> ，需要是 Message 的实现类。
	 *
	 * @param session 会话
	 * @param message 消息
	 */
	void execute(Session session, T message);
	
	/**
	 * 获取消息类型
	 *
	 * @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
	 */
	String getType();
}
