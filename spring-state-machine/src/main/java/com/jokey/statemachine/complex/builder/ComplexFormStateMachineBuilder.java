package com.jokey.statemachine.complex.builder;

import com.jokey.statemachine.complex.action.ComplexFormChoiceAction;
import com.jokey.statemachine.complex.guard.ComplexFormCheckChoiceGuard;
import com.jokey.statemachine.complex.guard.ComplexFormDealChoiceGuard;
import com.jokey.statemachine.event.ComplexFormEvents;
import com.jokey.statemachine.state.ComplexFormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.builder
 * @comment: 复杂表单状态机创造者
 */
@Slf4j
@Component
public class ComplexFormStateMachineBuilder {
	
	private static final String MACHINE_ID = "complexFormStateMachine";
	
	public StateMachine<ComplexFormStates, ComplexFormEvents> build(BeanFactory beanFactory) throws Exception {
		StateMachineBuilder.Builder<ComplexFormStates, ComplexFormEvents> stateMachineBuilder = StateMachineBuilder.builder();
		log.info("========构建{}状态机============", MACHINE_ID);
		stateMachineBuilder.configureConfiguration()
				.withConfiguration()
				.machineId(MACHINE_ID)
				.beanFactory(beanFactory);
		
		stateMachineBuilder.configureStates()
				.withStates()
				.initial(ComplexFormStates.BLANK_FORM)
				.choice(ComplexFormStates.CHECK_CHOICE)
				.choice(ComplexFormStates.DEAL_CHOICE)
				.states(EnumSet.allOf(ComplexFormStates.class));
		
		stateMachineBuilder.configureTransitions()
				.withExternal()
				.source(ComplexFormStates.BLANK_FORM).target(ComplexFormStates.FULL_FORM)
				.event(ComplexFormEvents.WRITE)
				.and()
				.withExternal()
				.source(ComplexFormStates.FULL_FORM).target(ComplexFormStates.CHECK_CHOICE)
				.event(ComplexFormEvents.CHECK)
				.and()
				.withChoice()
				.source(ComplexFormStates.CHECK_CHOICE)
				.first(ComplexFormStates.CONFIRM_FORM, new ComplexFormCheckChoiceGuard(), new ComplexFormChoiceAction())
				// ComplexFormChoiceAction可以执行多个
				//.first(ComplexFormStates.CONFIRM_FORM, new ComplexFormCheckChoiceGuard(), new ComplexFormChoiceAction(),new ComplexFormChoiceAction())
				.last(ComplexFormStates.DEAL_FORM, new ComplexFormChoiceAction())
				.and()
				.withExternal()
				.source(ComplexFormStates.CONFIRM_FORM)
				.target(ComplexFormStates.SUCCESS_FORM)
				.event(ComplexFormEvents.SUBMIT)
				.and()
				.withExternal()
				.source(ComplexFormStates.DEAL_FORM)
				.target(ComplexFormStates.DEAL_CHOICE)
				.event(ComplexFormEvents.DEAL)
				.and()
				.withChoice()
				.source(ComplexFormStates.DEAL_CHOICE)
				.first(ComplexFormStates.FULL_FORM, new ComplexFormDealChoiceGuard())
				.last(ComplexFormStates.FAILED_FORM);
		
		return stateMachineBuilder.build();
		
	}
}
