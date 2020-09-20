package com.jokey.statemachine.event;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.event
 * @comment: 公文审批流程事件
 */
public enum FormEvents {
	/**
	 * 填写
	 */
	WRITE,
	/**
	 * 校验
	 */
	CONFIRM,
	/**
	 * 提交
	 */
	SUBMIT;
}
