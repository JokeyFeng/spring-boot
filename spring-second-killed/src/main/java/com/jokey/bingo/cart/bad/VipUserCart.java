package com.jokey.bingo.cart.bad;

import com.jokey.bingo.cart.common.DbUtil;
import com.jokey.bingo.cart.dto.Cart;
import com.jokey.bingo.cart.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.bad
 * @comment: VIP用户购物车逻辑
 */
public class VipUserCart {
	
	public Cart process(long userId, Map<Long, Integer> items) {
		Cart cart = new Cart();
		//把map的购物车转为item列表
		List<Item> itemList = new ArrayList<>();
		items.forEach((itemId, quantity) -> {
			Item item = new Item();
			item.setId(itemId);
			item.setQuantity(quantity);
			item.setPrice(DbUtil.getItemPrice(itemId));
			itemList.add(item);
		});
		cart.setItems(itemList);
		
		//处理运费和商品优惠
		itemList.forEach(item -> {
			//运费为商品总价的10%
			item.setDeliveryPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()).multiply(new BigDecimal("0.1"))));
			//购买两家以上相同商品，第三件开始享受一件折扣
			if (item.getQuantity() > 2) {
				item.setCouponPrice(item.getPrice()
						.multiply(BigDecimal.valueOf(100 - DbUtil.getUserCouponPercent(userId)).divide(new BigDecimal("100")))
						.multiply(BigDecimal.valueOf(item.getQuantity() - 2)));
			} else {
				item.setCouponPrice(BigDecimal.ZERO);
			}
		});
		//计算商品总价
		cart.setTotalItemPrice(cart.getItems().stream().map(item -> BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add));
		//计算运费总价
		cart.setTotalDeliveryPrice(cart.getItems().stream().map(Item::getDeliveryPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		//计算商品优惠
		cart.setTotalDiscount(cart.getItems().stream().map(Item::getCouponPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		//应付总价=商品总价+运费总价-总优惠
		cart.setPayPrice(cart.getTotalItemPrice().add(cart.getTotalDeliveryPrice()).subtract(cart.getTotalDiscount()));
		return cart;
	}
}
