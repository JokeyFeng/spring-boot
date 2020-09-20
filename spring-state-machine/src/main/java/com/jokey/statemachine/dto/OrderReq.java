package com.jokey.statemachine.dto;

import lombok.Data;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.dto
 * @comment:
 */
@Data
public class OrderReq {
	
	private String orderId;
	
	private String buyer;
	
	private String address;
	
	private String phone;
	
	private String orderAction;
	/**
	 * 订单状态
	 */
	private String orderState;
}
