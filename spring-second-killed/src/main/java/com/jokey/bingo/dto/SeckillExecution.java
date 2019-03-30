package com.jokey.bingo.dto;

import com.jokey.bingo.entity.SeckillStateEnum;
import com.jokey.bingo.entity.SuccessKilled;
import lombok.Data;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:封装秒杀执行结果
 */
@Data
public class SeckillExecution {

    /**
     * ID
     */
    private Long seckillId;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 状态描述
     */
    private String stateInfo;
    /**
     * 秒杀成功对象
     */
    private SuccessKilled successKilled;

    public SeckillExecution(Long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(Long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
