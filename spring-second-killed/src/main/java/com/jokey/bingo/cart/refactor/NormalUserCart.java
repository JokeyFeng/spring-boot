package com.jokey.bingo.cart.refactor;

import com.jokey.bingo.cart.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.refactor
 * @comment: 普通用户的购物车 NormalUserCart，实现的是 0 优惠和 10% 运费的逻辑
 */
@Service("NormalUserCart")
public class NormalUserCart extends AbstractCart {
	
	@Override
	void processCouponPrice(long userId, Item item) {
		item.setCouponPrice(BigDecimal.ZERO);
	}
	
	@Override
	void processDeliveryPrice(long userId, Item item) {
		item.setDeliveryPrice(item.getPrice()
				.multiply(BigDecimal.valueOf(item.getQuantity())
						.multiply(new BigDecimal("0.1"))));
	}
}
