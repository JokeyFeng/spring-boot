package com.jokey.bingo.exception;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:秒杀业务相关异常
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
