package com.jokey.bingo.red.packet.service;

import com.jokey.bingo.red.packet.base.RedPacketUtil;
import com.jokey.bingo.red.packet.base.RedisKey;
import com.jokey.bingo.red.packet.dto.RedPacketDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.service
 * @comment:
 */
@Slf4j
@Component
public class IRedPacketService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@Autowired
	private IRedService redService;
	
	/**
	 * 发红包核心业务逻辑实现
	 *
	 * @param dto
	 * @return
	 */
	public String handOut(RedPacketDTO dto) {
		if (dto.getAmount() > 0 && dto.getTotal() > 0) {
			//采用二倍均值法生成随机红包列表
			List<Integer> packetList = RedPacketUtil.divideRedPacket(dto.getAmount(), dto.getTotal());
			//生成红包全局唯一标识串
			String timestamp = String.valueOf(System.nanoTime());
			String redId = RedisKey.key(RedisKey.RED_PACKET_LIST, dto.getUserId().toString(), timestamp);
			//将随机金额列表存入缓存list中
			packetList.forEach(amount -> redisTemplate.boundListOps(redId).leftPush(amount));
			//根据缓存key的前缀与其他信息拼接成一个新的用于存储红包总数的key
			String redTotalKey = RedisKey.key(redId, RedisKey.AMOUNT_TOTAL);
			//将红包总个数存入缓存中
			stringRedisTemplate.opsForValue().set(redTotalKey, dto.getTotal().toString());
			//异步记录红包的全局唯一标识串，红包个数与随机金额列表信息到数据库中
			redService.recoderRedPacket(dto, redId, packetList);
			//返回数据
			return redId;
		}
		
		return null;
	}
	
	/**
	 * 抢红包业务逻辑实现
	 *
	 * @param userId 当前抢红包用户id
	 * @param redId  redis红包全局唯一标识串
	 * @return 返回抢到的红包金额或者抢不到红包金额null
	 */
	public BigDecimal rob(Integer userId, String redId) {
		//定义redis操作组件的值操作订单
		//在处理用户抢红包之前，需要先判断当前用户是否已经抢过该红包了
		//如果已经抢过，直接返回红包金额，并在前端显示出来
		String robHashKey = RedisKey.key(redId, RedisKey.ROB_PACKET_USER);
		Object obj = redisTemplate.boundHashOps(robHashKey).get(userId);
		if (Objects.nonNull(obj)) {
			return new BigDecimal(obj.toString());
		}
		//"点红包"业务逻辑-主要用于判断缓存系统中是否仍然有红包，即剩余红包个数是否大于0
		boolean hasRedPacket = this.click(redId);
		//进入拆红包业务逻辑
		if (hasRedPacket) {
			//通过分布式锁来控制list红包队列
			String lockKey = RedisKey.key(redId, userId.toString(), RedisKey.RED_PACKET_LOCK);
			Boolean setResult = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, redId, 60, TimeUnit.MINUTES);
			if (Objects.isNull(setResult) || !setResult) {
				log.error("用户:{} 抢红包获取分布式锁失败：{}", userId, lockKey);
				throw new RuntimeException("红包只能抢一次哦");
			}
			//从小红包随机金额列表中弹出一个随机金额的红包
			Object redAmount = redisTemplate.opsForList().rightPop(redId);
			//当前用户抢到一个红包了，可以进入后续的更新缓存，把信息记录到数据库中
			if (Objects.nonNull(redAmount)) {
				//更新缓存系统中剩余的红包个数，即红包个数-1
				String redTotalKey = RedisKey.key(redId, RedisKey.AMOUNT_TOTAL);
				stringRedisTemplate.opsForValue().decrement(redTotalKey, 1);
				//将抢到红包时的用户账号信息及抢到的金额等信息记入数据库
				BigDecimal result = new BigDecimal(redAmount.toString());
				redService.recordRobRedPacket(userId, redId, result);
				//将当前抢到红包的用户设置进入缓存中，用于表示当前用户已经抢过红包了
				redisTemplate.boundHashOps(robHashKey).putIfAbsent(userId, result);
				redisTemplate.expire(robHashKey, 24L, TimeUnit.HOURS);
				log.info("当前用户抢到红包了:userId={},key={},amount={}", userId, redId, result);
				return result;
			}
		}
		
		//删除分布式锁
		//Boolean delete = stringRedisTemplate.delete(lockKey);
		//log.warn("删除用户:{}分布式锁：{}==>>{}", userId, lockKey, delete);
		
		//表示当前用户没有抢到红包
		//return null;
		throw new RuntimeException("哈哈，您来晚了，红包已被抢光了");
	}
	
	/**
	 * 抢红包逻辑
	 *
	 * @param redId
	 * @return
	 */
	private boolean click(String redId) {
		//定义用于查询缓存系统中红包剩余个数的key
		String redTotalKey = RedisKey.key(redId, RedisKey.AMOUNT_TOTAL);
		//获取缓存中红包剩余的个数
		String total = stringRedisTemplate.opsForValue().get(redTotalKey);
		//判断红包剩余个数total是否大于0，如果大于0，则返回true，代表还有红包
		return Objects.nonNull(total) && Integer.valueOf(total) > 0;
	}
}
