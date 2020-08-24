package com.jokey.publisher;

import com.jokey.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author JokeyFeng
 * @date: 2020/8/25
 * @project: spring-boot
 * @package: com.jokey.publisher
 * @comment: 邮件服务（用户注册事件的订阅者）
 * <1> 事件订阅者的服务同样需要托管于 Spring 容器
 * <2> ApplicationListener<E extends ApplicationEvent> 接口是由 Spring 提供的事件订阅者必须实现的接口，我们一般把该 Service 关心的事件类型作为泛型传入。
 * <3> 处理事件，通过 event.getSource() 即可拿到事件的具体内容，在本例中便是用户的姓名。
 */
@Service
public class EmailServiceListener implements ApplicationListener<UserRegisterEvent> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceListener.class);
	
	@Override
	public void onApplicationEvent(UserRegisterEvent event) {
		LOGGER.info("邮件服务接到通知，给{}发送邮件通知...", event.getSource());
	}
}
