package com.jokey.statemachine.complex.builder;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.builder
 * @comment: 订单流程状态机创建者
 */
@Slf4j
@Component
public class OrderStateMachineBuilder {
	
	private static final String MACHINE_ID = "orderMachine";
	
	/**
	 * 构建状态机
	 *
	 * @param beanFactory Spring容器的bean工厂
	 * @return
	 */
	public StateMachine<OrderStates, OrderEvents> build(BeanFactory beanFactory) throws Exception {
		StateMachineBuilder.Builder<OrderStates, OrderEvents> stateMachineBuilder = StateMachineBuilder.builder();
		
		log.info("==================构建订单状态机==================");
		//machineId是状态机的配置类和事件实现类的关联，和它关联的是OrderEventConfig
		stateMachineBuilder.configureConfiguration()
				.withConfiguration()
				.machineId(MACHINE_ID)
				.beanFactory(beanFactory);
		
		stateMachineBuilder.configureStates()
				.withStates()
				.initial(OrderStates.UNPAID)
				.states(EnumSet.allOf(OrderStates.class));
		
		stateMachineBuilder.configureTransitions()
				.withExternal()
				.source(OrderStates.UNPAID).target(OrderStates.WAIT_FOR_RECEIVE)
				.event(OrderEvents.PAY).action(this.action())
				.and()
				.withExternal()
				.source(OrderStates.WAIT_FOR_RECEIVE).target(OrderStates.DONE)
				.event(OrderEvents.RECEIVE);
		
		return stateMachineBuilder.build();
	}
	
	@Bean
	public Action<OrderStates, OrderEvents> action() {
		return stateContext -> log.info("状态机应用上下文：{}", stateContext);
	}
}
