package com.jokey.bingo.entity;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public enum SeckillStateEnum {
    /**
     * 1:秒杀成功
     */
    SUCCESS(1, "秒杀成功"),
    /**
     * 0:秒杀结束
     */
    END(0, "秒杀结束"),
    /**
     * -1:重复秒杀
     */
    REPEAT_KILL(-1, "重复秒杀"),
    /**
     * -2:系统异常
     */
    INNER_ERROR(-2, "系统异常"),
    /**
     * -3:数据篡改
     */
    DATA_REWRITE(-3, "数据篡改");

    private int state;

    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SeckillStateEnum stateOf(int state) {
        for (SeckillStateEnum e : values()) {
            if (e.getState() == state) {
                return e;
            }
        }
        return null;
    }
}
