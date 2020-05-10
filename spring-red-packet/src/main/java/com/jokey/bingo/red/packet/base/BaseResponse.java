package com.jokey.bingo.red.packet.base;

import lombok.Data;

/**
 * @author JokeyFeng
 * @date: 2020/5/9
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.base
 * @comment:
 */
@Data
public class BaseResponse<T> {
	
	private Integer code;
	
	private String message;
	
	private T data;
	
	private BaseResponse(ResponseStatusEnum status, T data) {
		this.data = data;
		this.code = status.getCode();
		this.message = status.getMessage();
	}
	
	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(ResponseStatusEnum.success, data);
	}
	
	public static <T> BaseResponse<T> fail(T data) {
		return new BaseResponse<>(ResponseStatusEnum.fail, data);
	}
}
