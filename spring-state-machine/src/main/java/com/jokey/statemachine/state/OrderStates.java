package com.jokey.statemachine.state;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.statemachine.state
 * @comment: 三个状态（待支付、待收货、结束）
 */
public enum OrderStates {
	/**
	 * 待支付
	 */
	UNPAID,
	/**
	 * 待收费
	 */
	WAIT_FOR_RECEIVE,
	/**
	 * 结束
	 */
	DONE;
}
