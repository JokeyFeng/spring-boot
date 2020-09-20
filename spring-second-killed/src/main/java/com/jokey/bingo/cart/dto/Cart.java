package com.jokey.bingo.cart.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/9/6
 * @project: spring-boot
 * @package: com.jokey.bingo.cart.dto
 * @comment: 购物车
 */
@Data
public class Cart implements Serializable {
	
	private static final long serialVersionUID = -6441720285420457008L;
	
	/**
	 * 商品清单
	 */
	private List<Item> items = new ArrayList<>();
	/**
	 * 总优惠
	 */
	private BigDecimal totalDiscount;
	/**
	 * 商品总价
	 */
	private BigDecimal totalItemPrice;
	/**
	 * 总运费
	 */
	private BigDecimal totalDeliveryPrice;
	/**
	 * 应付总价
	 */
	private BigDecimal payPrice;
}
