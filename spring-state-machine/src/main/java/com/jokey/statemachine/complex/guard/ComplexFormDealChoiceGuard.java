package com.jokey.statemachine.complex.guard;

import com.jokey.statemachine.dto.FormReq;
import com.jokey.statemachine.event.ComplexFormEvents;
import com.jokey.statemachine.state.ComplexFormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * @author JokeyFeng
 * @date: 2020/9/12
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.guard
 * @comment:
 */
@Slf4j
public class ComplexFormDealChoiceGuard implements Guard<ComplexFormStates, ComplexFormEvents> {
	
	@Override
	public boolean evaluate(StateContext<ComplexFormStates, ComplexFormEvents> stateContext) {
		log.warn("=====================entry into ComplexFormDealChoiceGuard=====================");
		FormReq form = stateContext.getMessage().getHeaders().get("complexForm", FormReq.class);
		if ((form.getTitle() == null)||(form.getTitle().contains("坏"))) {
			return false;
		} else {
			return true;
		}
	}
}
