package com.jokey.publisher;

import com.jokey.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author JokeyFeng
 * @date: 2020/8/25
 * @project: spring-boot
 * @package: com.jokey.publisher
 * @comment: 用户注册服务 (事件发布者)
 * <1> 服务必须交给 Spring 容器托管
 * <2> ApplicationEventPublisherAware 是由 Spring 提供的用于为 Service 注入 ApplicationEventPublisher 事件发布器的接口，使用这个接口，我们自己的 Service 就拥有了发布事件的能力。
 * <3> 用户注册后，不再是显示调用其他的业务 Service，而是发布一个用户注册事件。
 */
@Service
public class UserRegisterPublisher implements ApplicationEventPublisherAware {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserRegisterPublisher.class);
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	/**
	 * 用户注册
	 *
	 * @param name
	 */
	public void register(String name) {
		LOGGER.info("用户：{} 已注册！！！", name);
		//发布注册事件
		applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
	}
}
