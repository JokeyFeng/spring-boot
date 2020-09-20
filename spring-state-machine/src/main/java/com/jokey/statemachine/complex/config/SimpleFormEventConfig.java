package com.jokey.statemachine.complex.config;

import com.jokey.statemachine.event.FormEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.config
 * @comment:
 */
@Slf4j
@WithStateMachine(id = "simpleFormMachine")
public class SimpleFormEventConfig {
	
	/**
	 * 当前状态BLANK_FORM
	 */
	@OnTransition(target = "BLANK_FORM")
	public void create() {
		log.info("---空白表单---");
	}
	
	/**
	 * BLANK_FORM->FULL_FORM 执行的动作
	 */
	@OnTransition(source = "BLANK_FORM", target = "FULL_FORM")
	public void write(Message<FormEvents> message) {
		log.info("---填写完表单---");
	}
	
	/**
	 * FULL_FORM->CONFIRM_FORM 执行的动作
	 */
	@OnTransition(source = "FULL_FORM", target = "CONFIRM_FORM")
	public void confirm(Message<FormEvents> message) {
		log.info("---校验表单---");
	}
	
	/**
	 * CONFIRM_FORM->SUCCESS_FORM 执行的动作
	 */
	@OnTransition(source = "CONFIRM_FORM", target = "SUCCESS_FORM")
	public void submit(Message<FormEvents> message) {
		log.info("---表单提交成功---");
	}
}
