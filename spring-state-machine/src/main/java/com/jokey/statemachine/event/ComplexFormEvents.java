package com.jokey.statemachine.event;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.event
 * @comment: 复杂表单事件
 */
public enum ComplexFormEvents {
	/**
	 * 填写
	 */
	WRITE,
	/**
	 * 校验
	 */
	CHECK,
	/**
	 * 处理
	 */
	DEAL,
	/**
	 * 提交
	 */
	SUBMIT;
}
