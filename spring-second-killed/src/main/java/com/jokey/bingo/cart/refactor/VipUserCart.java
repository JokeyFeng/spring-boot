package com.jokey.bingo.cart.refactor;

import com.jokey.bingo.cart.common.DbUtil;
import com.jokey.bingo.cart.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.refactor
 * @comment: VIP 用户的购物车 VipUserCart，直接继承了 NormalUserCart，只需要修改多买优惠策略
 */
@Service("VipUserCart")
public class VipUserCart extends NormalUserCart {
	/**
	 * 购买两家以上相同商品，第三件开始享受一定的折扣
	 *
	 * @param userId
	 * @param item
	 */
	@Override
	void processCouponPrice(long userId, Item item) {
		if (item.getQuantity() > 2) {
			item.setCouponPrice(item.getPrice()
					.multiply(BigDecimal.valueOf(100 - DbUtil.getUserCouponPercent(userId)).divide(new BigDecimal("100")))
					.multiply(BigDecimal.valueOf(item.getQuantity() - 2)));
		} else {
			item.setCouponPrice(BigDecimal.ZERO);
		}
	}
	
}
