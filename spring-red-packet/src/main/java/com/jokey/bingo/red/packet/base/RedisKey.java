package com.jokey.bingo.red.packet.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.base
 * @comment:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKey {
	
	/**
	 * 生成key
	 *
	 * @param keys
	 * @return
	 */
	public static String key(String... keys) {
		return String.join(":", keys).toLowerCase();
	}
	
	/**
	 * 记录抢到红包的用户及金额
	 */
	public static final String ROB_PACKET_USER = "rob";
	/**
	 * 红包剩余个数
	 */
	public static final String AMOUNT_TOTAL = "total";
	/**
	 * 红包随机金额列表
	 */
	public static final String RED_PACKET_LIST = "redis:red:packet";
	/**
	 * 锁住用户防止重复操作
	 */
	public static final String RED_PACKET_LOCK = "lock";
	
	
}
