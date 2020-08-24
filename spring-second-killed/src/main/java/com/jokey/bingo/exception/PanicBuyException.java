package com.jokey.bingo.exception;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:秒杀业务相关异常
 */
public class PanicBuyException extends RuntimeException {
	
	private static final long serialVersionUID = -8948194851410726031L;
	
	public PanicBuyException(String message) {
		super(message);
	}
	
	public PanicBuyException(String message, Throwable cause) {
		super(message, cause);
	}
}
