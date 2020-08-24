package com.jokey.bingo.exception;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
public class PanicBuyCloseException extends PanicBuyException {
    
    private static final long serialVersionUID = 7813309708487371190L;
    
    public PanicBuyCloseException(String message) {
        super(message);
    }

    public PanicBuyCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
