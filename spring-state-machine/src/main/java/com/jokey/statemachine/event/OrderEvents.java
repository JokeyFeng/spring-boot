package com.jokey.statemachine.event;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.statemachine.event
 * @comment: 两个引起状态迁移的事件（支付、收货）
 * 其中支付事件PAY会触发状态从待支付UNPAID状态到待收货WAITING_FOR_RECEIVE状态的迁移，
 * 而收货事件RECEIVE会触发状态从待收货WAITING_FOR_RECEIVE状态到结束DONE状态的迁移。
 */
public enum OrderEvents {

	/**
	 * 支付
	 */
	PAY,
	/**
	 * 收费
	 */
	RECEIVE;
}
