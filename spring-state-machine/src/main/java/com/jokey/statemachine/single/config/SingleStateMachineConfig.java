package com.jokey.statemachine.single.config;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.statemachine.single.controller.config
 * @comment: 状态机配置
 */
@Configuration
@EnableStateMachine //启用Spring StateMachine状态机功能
public class SingleStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {
	
	/**
	 * 为当前的状态机指定了状态监听器，其中listener()则是调用了下一个内容创建的监听器实例，用来处理各个各个发生的状态迁移事件。
	 *
	 * @param config
	 * @throws Exception
	 */
	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
		config.withConfiguration()
				.autoStartup(true)
				.listener(this.listener());
	}
	
	/**
	 * 初始化当前状态机拥有哪些状态，
	 * 其中initial(OrderStates.UNPAID)定义了初始状态为UNPAID，
	 * states(EnumSet.allOf(OrderStates.class))则指定了使用上一步中定义的所有状态作为该状态机的状态定义。
	 *
	 * @param states
	 * @throws Exception
	 */
	@Override
	public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
		//super.configure(states)
		states.withStates()
				.initial(OrderStates.UNPAID)
				.states(EnumSet.allOf(OrderStates.class));
	}
	
	/**
	 * 初始化当前状态机有哪些状态迁移动作，其中命名中我们很容易理解每一个迁移动作，
	 * 都有来源状态source，目标状态target以及触发事件event。
	 *
	 * @param transitions
	 * @throws Exception
	 */
	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
		//super.configure(transitions)
		transitions
				.withExternal()
				.source(OrderStates.UNPAID)
				.target(OrderStates.WAIT_FOR_RECEIVE)
				.event(OrderEvents.PAY)
				.and()
				.withExternal()
				.source(OrderStates.WAIT_FOR_RECEIVE)
				.target(OrderStates.DONE)
				.event(OrderEvents.RECEIVE);
	}
	
	/**
	 * 创建StateMachineListener状态监听器的实例，在该实例中会定义具体的状态迁移处理逻辑，
	 * 实现中只是做了一些输出，实际业务场景会有更复杂的逻辑，
	 * 所以通常情况下，我们可以将该实例的定义放到独立的类定义中，并用注入的方式加载进来。
	 *
	 * @return
	 */
	@Bean
	public StateMachineListener<OrderStates, OrderEvents> listener() {
		return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
			@Override
			public void transition(Transition<OrderStates, OrderEvents> transition) {
				//super.transition(transition)
				if (transition.getTarget().getId() == OrderStates.UNPAID) {
					System.out.println("订单创建，待支付");
					return;
				}
				
				if (transition.getSource().getId() == OrderStates.UNPAID
						&& transition.getTarget().getId() == OrderStates.WAIT_FOR_RECEIVE) {
					System.out.println("用户完成支付，待收货");
					return;
				}
				if (transition.getSource().getId() == OrderStates.WAIT_FOR_RECEIVE
						&& transition.getTarget().getId() == OrderStates.DONE) {
					System.out.println("用户已收到货，订单完成");
					return;
				}
			}
		};
	}
}
