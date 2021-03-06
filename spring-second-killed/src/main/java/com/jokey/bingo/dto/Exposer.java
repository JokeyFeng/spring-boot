package com.jokey.bingo.dto;

import lombok.Data;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment: 暴露秒杀地址DTO
 */
@Data
public class Exposer {

    /**
     * 暴露
     */
    private Boolean exposed;

    /**
     * 加密盐值
     */
    private String md5;
    /**
     * ID
     */
    private Long buyId;

    /**
     * 当前系统时间：毫秒
     */
    private Long now;

    /**
     * 秒杀开始时间
     */
    private Long start;

    /**
     * 秒杀结束时间
     */
    private Long end;

    public Exposer(Boolean exposed, Long buyId) {
        this.exposed = exposed;
        this.buyId = buyId;
    }

    public Exposer(Boolean exposed, Long buyId, Long now, Long start, Long end) {
        this.exposed = exposed;
        this.buyId = buyId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(Boolean exposed, String md5, Long buyId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.buyId = buyId;
    }
}
