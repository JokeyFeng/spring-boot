package com.jokey.bingo.mapper;

import com.jokey.bingo.entity.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author JokeyFeng
 * date:2019/3/11
 * project:spring-boot
 * package:com.jokey.bingo.mapper
 * comment:
 */
@Mapper
public interface SuccessKilledMapper {

    /**
     * 插入购买明细，可过滤重复
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId);
}
