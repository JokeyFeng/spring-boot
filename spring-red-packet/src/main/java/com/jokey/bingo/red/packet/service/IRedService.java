package com.jokey.bingo.red.packet.service;

import com.jokey.bingo.red.packet.dto.RedPacketDTO;
import com.jokey.bingo.red.packet.entity.RedDetail;
import com.jokey.bingo.red.packet.entity.RedRecord;
import com.jokey.bingo.red.packet.entity.RedRobRecord;
import com.jokey.bingo.red.packet.mapper.RedDetailMapper;
import com.jokey.bingo.red.packet.mapper.RedRecordMapper;
import com.jokey.bingo.red.packet.mapper.RedRobRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.service
 * @comment: 红包业务逻辑处理过程实现
 */
@Component
public class IRedService {
	
	@Resource
	private RedRecordMapper redRecordMapper;
	
	@Resource
	private RedDetailMapper redDetailMapper;
	
	@Resource
	private RedRobRecordMapper redRobRecordMapper;
	
	private ExecutorService executorService;
	
	@PostConstruct
	public void init() {
		executorService = new ThreadPoolExecutor(
				2,
				4,
				60,
				TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue(50));
	}
	
	/**
	 * 保存红包唯一标识串，红包个数，随机金额列表等信息到数据库
	 *
	 * @param dto
	 * @param redId
	 * @param redPacketList
	 */
	public void recoderRedPacket(RedPacketDTO dto, String redId, List<Integer> redPacketList) {
		executorService.execute(() -> {
			//保存发红包记录信息到数据库
			RedRecord redRecord = new RedRecord();
			redRecord.setUserId(dto.getUserId());
			redRecord.setRedPacket(redId);
			redRecord.setTotal(dto.getTotal());
			redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
			redRecord.setCreateTime(LocalDateTime.now());
			redRecordMapper.insertSelective(redRecord);
			
			//保存红包随机金额明细到数据库中
			redPacketList.forEach(amount -> {
				RedDetail detail = new RedDetail();
				detail.setIsActive((byte) 1);
				detail.setRecordId(redRecord.getId());
				detail.setCreateTime(LocalDateTime.now());
				detail.setAmount(BigDecimal.valueOf(amount));
				redDetailMapper.insertSelective(detail);
			});
		});
	}
	
	/**
	 * 记录抢红包时用户抢到的红包金额等信息到数据库
	 *
	 * @param userId
	 * @param redId
	 * @param amount
	 */
	public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) {
		executorService.execute(() -> {
			RedRobRecord robRecord = new RedRobRecord();
			robRecord.setUserId(userId);
			robRecord.setAmount(amount);
			robRecord.setRedPacket(redId);
			robRecord.setIsActive((byte) 1);
			robRecord.setRobTime(LocalDateTime.now());
			redRobRecordMapper.insertSelective(robRecord);
		});
	}
}
