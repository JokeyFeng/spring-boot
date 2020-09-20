package com.jokey.bingo.cart.refactor;

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
 * @package: com.jokey.bingo.cart.refactor
 * @comment: 该抽象类实现购物车的通用逻辑，额外定义两个抽象方法让子类实现。
 * 其中，processCouponPrice方法用于计算商品折扣，processDeliveryPrice方法用于计算运费
 */
public abstract class AbstractCart {
	/**
	 * 处理购物车的大量重复逻辑在父类实现
	 *
	 * @param userId
	 * @param items
	 * @return
	 */
	public Cart process(long userId, Map<Long, Integer> items) {
		Cart cart = new Cart();
		List<Item> itemList = new ArrayList<>();
		items.forEach((itemId, quantity) -> {
			Item item = new Item();
			item.setId(itemId);
			item.setPrice(DbUtil.getItemPrice(itemId));
			item.setQuantity(quantity);
			itemList.add(item);
		});
		cart.setItems(itemList);
		//让每个子类处理每一个商品的优惠和运费
		itemList.forEach(item -> {
			processCouponPrice(userId, item);
			processDeliveryPrice(userId, item);
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
	
	/**
	 * 处理商品优惠的逻辑留给子类实现
	 *
	 * @param userId
	 * @param item
	 */
	abstract void processCouponPrice(long userId, Item item);
	
	/**
	 * 处理配送费的逻辑留给子类实现
	 *
	 * @param userId
	 * @param item
	 */
	abstract void processDeliveryPrice(long userId, Item item);
}
