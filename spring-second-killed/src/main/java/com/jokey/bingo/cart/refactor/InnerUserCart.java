package com.jokey.bingo.cart.refactor;

import com.jokey.bingo.cart.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.refactor
 * @comment: 内部用户购物车 InternalUserCart 是最简单的，直接设置 0 运费和 0 折扣即可
 */
@Service("InnerUserCart")
public class InnerUserCart extends AbstractCart {
	
	@Override
	void processCouponPrice(long userId, Item item) {
		item.setCouponPrice(BigDecimal.ZERO);
	}
	
	@Override
	void processDeliveryPrice(long userId, Item item) {
		item.setDeliveryPrice(BigDecimal.ZERO);
	}
}
