package com.jokey.statemachine.complex.builder;

import com.jokey.statemachine.event.FormEvents;
import com.jokey.statemachine.state.FormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.builder
 * @comment: 简单表单状态机创建者
 */
@Slf4j
@Component
public class SimpleFormStateMachineBuilder {
	
	private static final String MACHINE_ID = "simpleFormMachine";
	
	public StateMachine<FormStates, FormEvents> build(BeanFactory beanFactory) throws Exception {
		StateMachineBuilder.Builder<FormStates, FormEvents> stateMachineBuilder = StateMachineBuilder.builder();
		log.info("==================构建表单状态机==================");
		//MACHINE_ID是状态机的配置类和事件实现类的关联，和它关联的是DocApprovalEventConfig
		stateMachineBuilder.configureConfiguration()
				.withConfiguration()
				.machineId(MACHINE_ID)
				.beanFactory(beanFactory);
		
		stateMachineBuilder.configureStates()
				.withStates()
				.initial(FormStates.BLANK_FORM)
				.states(EnumSet.allOf(FormStates.class));
		
		stateMachineBuilder.configureTransitions()
				.withExternal()
				.source(FormStates.BLANK_FORM).target(FormStates.FULL_FORM)
				.event(FormEvents.WRITE)
				.and()
				.withExternal()
				.source(FormStates.FULL_FORM).target(FormStates.CONFIRM_FORM)
				.event(FormEvents.CONFIRM)
				.and()
				.withExternal()
				.source(FormStates.CONFIRM_FORM).target(FormStates.SUCCESS_FORM)
				.event(FormEvents.SUBMIT);
		
		return stateMachineBuilder.build();
	}
	
	
}
