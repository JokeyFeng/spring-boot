package com.jokey.statemachine.complex.controller;

import com.jokey.statemachine.complex.builder.ComplexFormStateMachineBuilder;
import com.jokey.statemachine.dto.FormReq;
import com.jokey.statemachine.event.ComplexFormEvents;
import com.jokey.statemachine.state.ComplexFormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/9/14
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.controller
 * @comment:
 */
@Slf4j
@RestController
@RequestMapping("/complexForm")
public class ComplexFormStateMachineController {
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private ComplexFormStateMachineBuilder complexFormStateMachineBuilder;
	
	private static final String FORM_NAME = "complexForm";
	
	/**
	 * check成功，成为SUCCESS_FORM
	 *
	 * @throws Exception
	 */
	@PostMapping("/checkSuccessForm")
	public void testSuccessForm() throws Exception {
		StateMachine<ComplexFormStates, ComplexFormEvents> stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		log.info("=============={}===============", stateMachine.getId());
		FormReq form = new FormReq();
		form.setId(111);
		form.setTitle("成功的表单");
		
		System.out.println("-------------------开始---------------");
		stateMachine.start();
		
		Message<ComplexFormEvents> message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	/**
	 * check失败，deal成功，回到FULL_FORM
	 *
	 * @throws Exception
	 */
	@PostMapping("/checkFullForm")
	public void testFullForm() throws Exception {
		StateMachine<ComplexFormStates, ComplexFormEvents> stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		log.info("=============={}===============", stateMachine.getId());
		
		FormReq form = new FormReq();
		form.setId(222);
		form.setTitle(null);
		
		System.out.println("-------------------开始---------------");
		stateMachine.start();
		
		Message<ComplexFormEvents> message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	/**
	 * check失败，deal失败，成为FAILED_FORM
	 *
	 * @throws Exception
	 */
	@PostMapping("/checkFailedForm")
	public void testFailedForm() throws Exception {
		StateMachine<ComplexFormStates, ComplexFormEvents> stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		log.info("=============={}===============", stateMachine.getId());
		
		FormReq form = new FormReq();
		form.setId(333);
		form.setTitle(null);
		
		System.out.println("-------------------开始---------------");
		stateMachine.start();
		
		Message<ComplexFormEvents> message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		form.setTitle("好的表单");
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		TimeUnit.SECONDS.sleep(5);
		
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader(FORM_NAME, form).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
}
