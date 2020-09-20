package com.jokey.statemachine.single.controller;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.statemachine.single.controller
 * @comment: 单个状态机
 */
@RestController
@RequestMapping("/singleStateMachine")
public class TestController {
	
	@Resource
	private StateMachine<OrderStates, OrderEvents> stateMachine;
	
	/**
	 * 发起支付
	 */
	@PostMapping("/create")
	public void create() {
		stateMachine.start();
		stateMachine.sendEvent(OrderEvents.PAY);
	}
	
	
	/**
	 * 发起支付
	 */
	@PostMapping("/pay")
	public void pay() {
		stateMachine.start();
		stateMachine.sendEvent(OrderEvents.PAY);
	}
	
	/**
	 * 收到货物
	 */
	@PostMapping("/receive")
	public void receive() {
		stateMachine.start();
		stateMachine.sendEvent(OrderEvents.RECEIVE);
	}
}
