package com.jokey.bingo.cart.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.bad
 * @comment:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbUtil {
	
	public static BigDecimal getItemPrice(Long itemId) {
		return BigDecimal.valueOf(itemId * 1.5);
	}
	
	/**
	 * 查询VIP用户享受的折扣
	 *
	 * @param userId
	 * @return
	 */
	public static Integer getUserCouponPercent(long userId) {
		return ThreadLocalRandom.current().nextInt(5, 40);
	}
	
	/**
	 * 模拟查询用户所属的类型
	 *
	 * @param userId
	 * @return
	 */
	public static UserEnum getUserType(long userId) {
		if (userId % 3 == 0) {
			return UserEnum.NORMAL;
		}
		
		if (userId % 3 == 1) {
			return UserEnum.VIP;
		}
		
		return UserEnum.INNER;
	}
	
}
