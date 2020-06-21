package com.jokey.sharding.service;

import com.jokey.sharding.entity.Config;
import com.jokey.sharding.entity.Order;
import com.jokey.sharding.entity.OrderItem;

import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.service
 * @comment:
 */
public interface OrderService {
	/**
	 * 保存订单
	 *
	 * @param order
	 * @return
	 */
	Integer saveOrder(Order order);
	
	/**
	 * 保存订单明细
	 *
	 * @param orderItem
	 * @param userId
	 * @return
	 */
	Integer saveOrderItem(OrderItem orderItem, Long userId);
	
	/**
	 * 根据用户ID查询订单
	 *
	 * @param userId
	 * @return
	 */
	Order selectByUserId(Long userId);
	
	/**
	 * 关联用户ID查询订单
	 *
	 * @param userId
	 * @param orderId
	 * @return
	 */
	List<Order> selectOrderJoinOrderItem(Long userId, Long orderId);
	
	/**
	 * 不关联用户ID查询订单
	 *
	 * @param userId
	 * @param orderId
	 * @return
	 */
	List<Order> selectOrderJoinOrderItemNoSharding(Long userId, Long orderId);
	
	/**
	 * 查询
	 *
	 * @param userId
	 * @param orderId
	 * @return
	 */
	List<Order> selectOrderJoinConfig(Long userId, Long orderId);
	
	/**
	 * 保存广播表
	 *
	 * @param config
	 * @return
	 */
	Integer saveConfig(Config config);
	
	/**
	 * 查询
	 *
	 * @param id
	 * @return
	 */
	Config selectConfig(Integer id);
}
