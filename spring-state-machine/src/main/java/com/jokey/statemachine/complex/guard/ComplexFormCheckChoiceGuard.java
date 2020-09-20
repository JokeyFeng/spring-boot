package com.jokey.statemachine.complex.guard;

import com.jokey.statemachine.dto.FormReq;
import com.jokey.statemachine.event.ComplexFormEvents;
import com.jokey.statemachine.state.ComplexFormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.Objects;

/**
 * @author JokeyFeng
 * @date: 2020/9/12
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.guard
 * @comment: Guard承担choice的判断功能，在Spring StateMachine中本来是用来保证状态跳转过程的，在choice是为了判断代码而存在的
 */
@Slf4j
public class ComplexFormCheckChoiceGuard implements Guard<ComplexFormStates, ComplexFormEvents> {
	
	@Override
	public boolean evaluate(StateContext<ComplexFormStates, ComplexFormEvents> stateContext) {
		log.warn("=======================entry into ComplexFormCheckChoiceGuard=================");
		FormReq formReq = stateContext.getMessage().getHeaders().get("complexForm", FormReq.class);
		return Objects.nonNull(formReq.getTitle());
	}
}
