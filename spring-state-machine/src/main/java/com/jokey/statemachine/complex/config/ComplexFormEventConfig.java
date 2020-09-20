package com.jokey.statemachine.complex.config;

import com.jokey.statemachine.event.ComplexFormEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.config
 * @comment:
 */
@Slf4j
@WithStateMachine(id = "complexFormStateMachine")
public class ComplexFormEventConfig {
	
	@OnTransition(target = "BLANK_FORM")
	public void create(Message<ComplexFormEvents> message){
		log.warn("======================创建空白表单=================");
	}
	
	@OnTransition(source = "BLANK_FORM",target = "FULL_FORM")
	public void write(Message<ComplexFormEvents> message){
		log.warn("======================填写完毕复杂的表单==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	@OnTransition(source = "FULL_FORM",target = "CHECK_CHOICE")
	public void check(Message<ComplexFormEvents> message){
		log.warn("======================校验复杂的表单==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	/**
	 * 不会执行
	 * @param message
	 */
	@OnTransition(source = "CHECK_CHOICE",target = "CONFIRM_FORM")
	public void checkToConfirm(Message<ComplexFormEvents> message){
		log.warn("======================校验表单到待提交表单(choice true)==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	/**
	 * 不会执行
	 * @param message
	 */
	@OnTransition(source = "CHECK_CHOICE",target = "DEAL_FORM")
	public void checkToDeal(Message<ComplexFormEvents> message){
		log.warn("======================校验表单到待提交表单(choice false)==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	@OnTransition(source = "DEAL_FORM",target = "DEAL_CHOICE")
	public void deal(Message<ComplexFormEvents> message){
		log.warn("======================处理复杂表单==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	/**
	 * 不会执行
	 * @param message
	 */
	@OnTransition(source = "DEAL_CHOICE",target = "FAILED_FORM")
	public void dealToFail(Message<ComplexFormEvents> message){
		log.warn("======================处理复杂表单失败(choice false)==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	/**
	 * 不会执行
	 * @param message
	 */
	@OnTransition(source = "DEAL_CHOICE",target = "FULL_FORM")
	public void dealToFull(Message<ComplexFormEvents> message){
		log.warn("======================处理复杂表单到重新填写表单(choice true)==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
	@OnTransition(source = "CONFIRM_FORM",target = "SUCCESS_FORM")
	public void submit(Message<ComplexFormEvents> message){
		log.warn("======================表单提交成功==================");
		log.info("传递的参数：{}",message.getHeaders().get("complexForm"));
	}
	
}
