package com.jokey.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author JokeyFeng
 * @date: 2020/8/25
 * @project: spring-boot
 * @package: com.jokey.event
 * @comment: 用户注册事件
 */
public class UserRegisterEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -8413499549211168662L;
	
	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param name the object on which the event initially occurred (never {@code null})
	 */
	public UserRegisterEvent(String name) {
		super(name);
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
}
