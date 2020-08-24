package com.jokey.bingo.service;

import com.jokey.bingo.dto.Exposer;
import com.jokey.bingo.dto.SecKillExecution;
import com.jokey.bingo.entity.PanicBuyEntity;
import com.jokey.bingo.exception.PanicBuyException;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/16
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public interface PanicBuyService {
	
	/**
	 * 查询所有秒杀记录
	 *
	 * @return
	 */
	List<PanicBuyEntity> getSecKillList();
	
	/**
	 * 查询单个秒杀记录
	 *
	 * @param buyId
	 * @return
	 */
	PanicBuyEntity getById(long buyId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址，
	 * 否则输出系统时间和秒杀时间
	 *
	 * @param buyId
	 * @return
	 */
	Exposer exportSecKillUrl(long buyId);
	
	/**
	 * 执行秒杀
	 *
	 * @param buyId
	 * @param userPhone
	 * @param digest
	 * @return
	 * @throws PanicBuyException
	 */
	SecKillExecution executeSecKill(long buyId, long userPhone, String digest) throws PanicBuyException;
}
