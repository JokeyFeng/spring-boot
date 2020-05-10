package com.jokey.bingo.red.packet.base;

import lombok.Getter;

/**
 * @author JokeyFeng
 * @date: 2020/5/9
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.base
 * @comment:
 */
@Getter
public enum ResponseStatusEnum {
	/**
	 * 0：成功
	 */
	success(0, "成功"),
	invalid_param(201, "非法参数"),
	invalid_grant_type(202, "非法的授权类型"),
	fail(-1, "失败");
	
	private final Integer code;
	
	private final String message;
	
	ResponseStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
