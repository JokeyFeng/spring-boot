package com.jokey.bingo.delay;

/**
 * @author Zhengjingfeng
 * @created 2020/2/20 15:43
 * @comment 延时消息处理器抽象类
 */
public abstract class AbstractDelayedTaskHandler {
    /**
     * 处理延时消息
     */
    public abstract void handleDelayMessage();
}