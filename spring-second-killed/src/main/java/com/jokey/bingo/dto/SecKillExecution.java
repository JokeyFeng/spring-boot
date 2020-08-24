package com.jokey.bingo.dto;

import com.jokey.bingo.entity.PanicBuyStateEnum;
import com.jokey.bingo.entity.SuccessBuy;
import lombok.Data;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:封装秒杀执行结果
 */
@Data
public class SecKillExecution {

    /**
     * ID
     */
    private Long secKillId;
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
    private SuccessBuy successBuy;

    public SecKillExecution(Long secKillId, PanicBuyStateEnum stateEnum, SuccessBuy successBuy) {
        this.secKillId = secKillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successBuy = successBuy;
    }

    public SecKillExecution(Long secKillId, PanicBuyStateEnum stateEnum) {
        this.secKillId = secKillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
