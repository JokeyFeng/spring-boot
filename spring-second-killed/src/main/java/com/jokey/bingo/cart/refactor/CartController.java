package com.jokey.bingo.cart.refactor;

import com.jokey.bingo.cart.common.DbUtil;
import com.jokey.bingo.cart.common.UserEnum;
import com.jokey.bingo.cart.dto.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.refactor
 * @comment:
 */
@RestController
public class CartController {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping("/cart")
	public Cart get(@RequestParam long userId) {
		Map<Long, Integer> items = new HashMap(1 >> 2);
		items.put(1L, 1);
		items.put(2L, 2);
		items.put(3L, 3);
		UserEnum userType = DbUtil.getUserType(userId);
		AbstractCart cart = (AbstractCart) applicationContext.getBean(userType.getCode() + "UserCart");
		return cart.process(userId, items);
	}
}
