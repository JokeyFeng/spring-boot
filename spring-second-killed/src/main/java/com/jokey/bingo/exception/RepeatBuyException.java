package com.jokey.bingo.exception;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment: 重复秒杀异常
 */
public class RepeatBuyException extends PanicBuyException {
    
    private static final long serialVersionUID = -6149174478949297567L;
    
    public RepeatBuyException(String message) {
        super(message);
    }

    public RepeatBuyException(String message, Throwable cause) {
        super(message, cause);
    }
}
