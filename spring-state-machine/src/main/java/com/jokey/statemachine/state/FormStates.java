package com.jokey.statemachine.state;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.state
 * @comment: 公文审批流程状态
 */
public enum FormStates {
	/**
	 * 空白表单
	 */
	BLANK_FORM,
	/**
	 * 填写完成表单
	 */
	FULL_FORM,
	/**
	 * 校验表单
	 */
	CONFIRM_FORM,
	/**
	 * 成功表单
	 */
	SUCCESS_FORM;
}
