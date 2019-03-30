package com.jokey.bingo.service;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SeckillExecution;
import com.jokey.bingo.entity.Seckill;
import com.jokey.bingo.entity.SeckillStateEnum;
import com.jokey.bingo.entity.SuccessKilled;
import com.jokey.bingo.exception.RepeatKillException;
import com.jokey.bingo.exception.SeckillCloseException;
import com.jokey.bingo.exception.SeckillException;
import com.jokey.bingo.mapper.SeckillMapper;
import com.jokey.bingo.mapper.SuccessKilledMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    private static String SALT = "asq42Z{P{()(#$^&**asdc";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillMapper.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.before(startTime)
                || nowTime.after(endTime)) {
            return new Exposer(false, seckillId, startTime.getTime(), endTime.getTime(), nowTime.getTime());
        }

        //转化特定字符串的过程，不可逆
        String digest = this.getMD5(seckillId);
        return new Exposer(true, digest, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String digest) throws SeckillException, RepeatKillException, SeckillException {
        if (digest == null || digest.equals(this.getMD5(seckillId))) {
            throw new SeckillException(SeckillStateEnum.DATA_REWRITE.getStateInfo());
        }

        //执行秒杀逻辑：扣减库存，记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
            if (updateCount < 1) {
                //没有更新到记录，说明秒杀结束(库存没了，时间结束了等)
                throw new SeckillCloseException(SeckillStateEnum.END.getStateInfo());
            } else {
                //记录购买行为
                int insertCount = successKilledMapper.insertSuccessKilled(seckillId, userPhone);
                if (insertCount < 1) {
                    //重复秒杀
                    throw new RepeatKillException(SeckillStateEnum.REPEAT_KILL.getStateInfo());
                } else {
                    SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException c) {
            throw c;
        } catch (RepeatKillException r) {
            throw r;
        } catch (
                Exception e) {
            log.error("执行秒杀逻辑出错:{}", e.getMessage());
            throw new SeckillException("秒杀活动内部错误:{}" + e.getMessage());
        }
    }
}
