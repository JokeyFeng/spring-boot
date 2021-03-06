package com.jokey.statemachine.single.config;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.statemachine
 * @comment:
 * 对于状态监听器，Spring StateMachine还提供了优雅的注解配置实现方式，
 * 所有StateMachineListener接口中定义的事件都能通过注解的方式来进行配置实现。
 * 比如，我们可以将之前实现的状态监听器(com.jokey.statemachine.single.controller.config.StateMachineConfig#listener())用注解配置来做进一步的简化
 */
@WithStateMachine
public class EventConfig {
	
	@OnTransition(target = "UNPAID")
	public void create(){
		System.out.println("---------订单创建，待支付");
	}
	
	@OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
	public void pay() {
		System.out.println("---------用户完成支付，待收货");
	}
	
	@OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
	public void receive() {
		System.out.println("---------用户已收货，订单完成");
	}
	
}
