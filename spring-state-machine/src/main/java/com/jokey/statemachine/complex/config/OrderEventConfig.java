package com.jokey.statemachine.complex.config;

import com.jokey.statemachine.event.OrderEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.config
 * @comment: 这个id对应的就是OrderStateMachineBuilder里面的MACHINE_ID，被builder写到.machineId(MACHINE_ID)里面。
 * 这样，OrderStateMachineBuilder对应上一章的StateMachineConfig多个状态机的实现版本，
 * OrderEventConfig对应上一章的OrderSingleEventConfig，基本一样，只是和OrderStateMachineBuilder通过machine_id做了关联
 */
@Slf4j
@WithStateMachine(id = "orderMachine")
public class OrderEventConfig {
	
	@OnTransition(target = "UNPAID")
	public void create() {
		log.info("------订单创建，待支付------");
	}
	
	@OnTransition(source = "UNPAID", target = "WAIT_FOR_RECEIVE")
	public void pay(Message<OrderEvents> message) {
		log.info("------用户完成支付，待收货-------");
		log.info("监听到支付事件，支付结果：{}", message.getHeaders().get("payResult"));
		log.info("发生支付事件后，订单信息：{}", message.getHeaders().get("order"));
	}
	
	@OnTransition(source = "WAIT_FOR_RECEIVE", target = "DONE")
	public void receive(Message<OrderEvents> message) {
		log.info("-----用户已收货，订单完成-------");
		log.info("监听到收货事件，收货结果：{}", message.getHeaders().get("receiveResult"));
		log.info("发生收货事件后，订单信息：{}", message.getHeaders().get("order"));
	}
}
