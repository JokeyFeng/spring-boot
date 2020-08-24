package com.jokey.bingo.mapper;

import com.jokey.bingo.entity.SuccessBuy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * date:2019/3/11
 * project:spring-boot
 * package:com.jokey.bingo.mapper
 * comment:
 */
@Mapper
@Component
public interface SuccessBuyMapper {

    /**
     * 插入购买明细，可过滤重复
     *
     * @param buyId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("buyId") long buyId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     *
     * @param buyId
     * @return
     */
    SuccessBuy queryByIdWithSecKill(@Param("buyId") long buyId);
}
