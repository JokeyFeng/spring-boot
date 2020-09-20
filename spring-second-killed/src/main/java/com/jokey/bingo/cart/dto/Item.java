package com.jokey.bingo.cart.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.dto
 * @comment: 购物车中的商品
 */
@Data
public class Item implements Serializable {
	
	private static final long serialVersionUID = -7582487391037349492L;
	/**
	 * 商品ID
	 */
	private long id;
	/**
	 * 商品数量
	 */
	private int quantity;
	/**
	 * 商品单价
	 */
	private BigDecimal price;
	/**
	 * 商品优惠
	 */
	private BigDecimal couponPrice;
	/**
	 * 商品运费
	 */
	private BigDecimal deliveryPrice;
}
