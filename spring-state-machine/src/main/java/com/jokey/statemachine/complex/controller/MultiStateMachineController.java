package com.jokey.statemachine.complex.controller;

import com.jokey.statemachine.complex.builder.SimpleFormStateMachineBuilder;
import com.jokey.statemachine.complex.builder.OrderStateMachineBuilder;
import com.jokey.statemachine.event.FormEvents;
import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.FormStates;
import com.jokey.statemachine.state.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.controller
 * @comment: 多个状态机的实现
 */
@Slf4j
@RestController
@RequestMapping("/multiStateMachine")
public class MultiStateMachineController {
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private OrderStateMachineBuilder orderStateMachineBuilder;
	
	@Autowired
	private SimpleFormStateMachineBuilder simpleFormStateMachineBuilder;
	
	@PostMapping("/testOrderState")
	public void test() throws Exception {
		//创建状态机
		StateMachine<OrderStates, OrderEvents> orderStateMachine = orderStateMachineBuilder.build(beanFactory);
		log.info(orderStateMachine.getId());
		//创建流程
		orderStateMachine.start();
		//触发支付事件
		orderStateMachine.sendEvent(OrderEvents.PAY);
		//触发收货事件
		orderStateMachine.sendEvent(OrderEvents.RECEIVE);
		//获取最终状态
		log.info("最终状态：{}", orderStateMachine.getState().getId());
	}
	
	@RequestMapping("/testFormState")
	public void testFormState() throws Exception {
		StateMachine<FormStates, FormEvents> stateMachine = simpleFormStateMachineBuilder.build(beanFactory);
		log.info(stateMachine.getId());
		// 创建流程
		stateMachine.start();
		stateMachine.sendEvent(FormEvents.WRITE);
		stateMachine.sendEvent(FormEvents.CONFIRM);
		stateMachine.sendEvent(FormEvents.SUBMIT);
		
		// 获取最终状态
		log.info("最终状态：{}", stateMachine.getState().getId());
	}
}
