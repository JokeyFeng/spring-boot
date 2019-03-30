package com.jokey.bingo.service;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SeckillExecution;
import com.jokey.bingo.entity.*;
import com.jokey.bingo.exception.RepeatKillException;
import com.jokey.bingo.exception.SeckillException;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * @param seckillId
     * @param userPhone
     * @param digest
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String digest) throws SeckillException, RepeatKillException, SeckillException;
}
