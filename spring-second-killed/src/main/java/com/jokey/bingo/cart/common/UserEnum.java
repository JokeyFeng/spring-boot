package com.jokey.bingo.cart.common;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.common
 * @comment: 用户类型枚举
 */
public enum UserEnum {
	/**
	 * 普通用户
	 */
	NORMAL("Normal"),
	/**
	 * VIP用户
	 */
	VIP("Vip"),
	/**
	 * 内部用户
	 */
	INNER("Inner");
	
	private final String code;
	
	UserEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
