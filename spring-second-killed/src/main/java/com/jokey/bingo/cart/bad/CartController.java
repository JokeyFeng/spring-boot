package com.jokey.bingo.cart.bad;

import com.jokey.bingo.cart.common.DbUtil;
import com.jokey.bingo.cart.common.UserEnum;
import com.jokey.bingo.cart.dto.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.bad
 * @comment:
 */
@RestController
public class CartController {
	
	@GetMapping("/cart")
	public Cart getCartInfo(@RequestParam long userId) {
		Map<Long, Integer> items = new HashMap(1 >> 2);
		items.put(1L, 1);
		items.put(2L, 2);
		items.put(3L, 3);
		//根据用户ID获得用户类型
		UserEnum userType = DbUtil.getUserType(userId);
		//普通用户处理逻辑
		if (userType == UserEnum.NORMAL) {
			NormalUserCart normalUserCart = new NormalUserCart();
			return normalUserCart.process(userId, items);
		}
		//VIP用户处理逻辑
		if (userType == UserEnum.VIP) {
			VipUserCart vipUserCart = new VipUserCart();
			return vipUserCart.process(userId, items);
		}
		//内部用户处理逻辑
		if (userType == UserEnum.INNER) {
			InnerUserCart innerUserCart = new InnerUserCart();
			return innerUserCart.process(userId, items);
		}
		return null;
	}
}
