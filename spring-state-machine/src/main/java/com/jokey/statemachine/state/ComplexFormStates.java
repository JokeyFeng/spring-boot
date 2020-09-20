package com.jokey.statemachine.state;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.state
 * @comment: 复杂表单状态
 */
public enum ComplexFormStates {
	/**
	 * 空白表单
	 */
	BLANK_FORM,
	/**
	 * 填写完表单
	 */
	FULL_FORM,
	/**
	 * 表单校验判断
	 */
	CHECK_CHOICE,
	/**
	 * 表单处理校验
	 */
	DEAL_CHOICE,
	/**
	 * 待处理表单
	 */
	DEAL_FORM,
	/**
	 * 交验完表单
	 */
	CONFIRM_FORM,
	/**
	 * 成功表单
	 */
	SUCCESS_FORM,
	/**
	 * 失败表单
	 */
	FAILED_FORM,
	;
}
