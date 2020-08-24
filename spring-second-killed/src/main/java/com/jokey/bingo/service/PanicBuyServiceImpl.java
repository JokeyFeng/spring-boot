package com.jokey.bingo.service;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SecKillExecution;
import com.jokey.bingo.entity.PanicBuyEntity;
import com.jokey.bingo.entity.PanicBuyStateEnum;
import com.jokey.bingo.entity.SuccessBuy;
import com.jokey.bingo.exception.RepeatBuyException;
import com.jokey.bingo.exception.PanicBuyCloseException;
import com.jokey.bingo.exception.PanicBuyException;
import com.jokey.bingo.mapper.PanicBuyMapper;
import com.jokey.bingo.mapper.SuccessBuyMapper;
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
public class PanicBuyServiceImpl implements PanicBuyService {
	
	@Autowired
	private PanicBuyMapper panicBuyMapper;
	
	@Autowired
	private SuccessBuyMapper successBuyMapper;
	
	private static String SALT = "asq42Z{P{()(#$^&**asdc";
	
	@Override
	public List<PanicBuyEntity> getSecKillList() {
		return panicBuyMapper.queryAll(0, 4);
	}
	
	@Override
	public PanicBuyEntity getById(long buyId) {
		return panicBuyMapper.queryById(buyId);
	}
	
	@Override
	public Exposer exportSecKillUrl(long buyId) {
		PanicBuyEntity panicBuyEntity = panicBuyMapper.queryById(buyId);
		if (panicBuyEntity == null) {
			return new Exposer(false, buyId);
		}
		
		Date startTime = panicBuyEntity.getStartTime();
		Date endTime = panicBuyEntity.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if (nowTime.before(startTime) || nowTime.after(endTime)) {
			return new Exposer(false, buyId, startTime.getTime(), endTime.getTime(), nowTime.getTime());
		}
		
		//转化特定字符串的过程，不可逆
		String digest = this.getMD5(buyId);
		return new Exposer(true, digest, buyId);
	}
	
	private String getMD5(long buyId) {
		String base = buyId + "/" + SALT;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SecKillExecution executeSecKill(long buyId, long userPhone, String digest) throws PanicBuyException {
		if (digest == null || !digest.equals(this.getMD5(buyId))) {
			throw new PanicBuyException(PanicBuyStateEnum.DATA_REWRITE.getStateInfo());
		}
		
		//执行秒杀逻辑：扣减库存，记录购买行为
		Date nowTime = new Date();
		try {
			//减库存
			int updateCount = panicBuyMapper.reduceNumber(buyId, nowTime);
			if (updateCount < 1) {
				//没有更新到记录，说明秒杀结束(库存没了，时间结束了等)
				throw new PanicBuyCloseException(PanicBuyStateEnum.END.getStateInfo());
			} else {
				//记录购买行为
				int insertCount = successBuyMapper.insertSuccessKilled(buyId, userPhone);
				if (insertCount < 1) {
					//重复秒杀
					throw new RepeatBuyException(PanicBuyStateEnum.REPEAT_KILL.getStateInfo());
				} else {
					SuccessBuy successBuy = successBuyMapper.queryByIdWithSecKill(buyId);
					return new SecKillExecution(buyId, PanicBuyStateEnum.SUCCESS, successBuy);
				}
			}
		} catch (PanicBuyCloseException | RepeatBuyException c) {
			throw c;
		} catch (Exception e) {
			log.error("执行秒杀逻辑出错:{}", e.getMessage());
			throw new PanicBuyException("秒杀活动内部错误:{}" + e.getMessage());
		}
	}
}
