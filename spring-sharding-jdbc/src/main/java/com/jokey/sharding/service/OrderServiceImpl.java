package com.jokey.sharding.service;

import com.jokey.sharding.entity.Config;
import com.jokey.sharding.entity.Order;
import com.jokey.sharding.entity.OrderItem;
import com.jokey.sharding.mapper.ConfigMapper;
import com.jokey.sharding.mapper.OrderItemMapper;
import com.jokey.sharding.mapper.OrderMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.service
 * @comment:
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public Integer saveOrder(Order order) {
		return orderMapper.save(order);
	}
	
	@Override
	public Integer saveOrderItem(OrderItem orderItem, Long userId) {
		try (HintManager hintManager = HintManager.getInstance()) {
			//绑定分库分片键
			hintManager.addDatabaseShardingValue("t_order_item", userId);
			return orderItemMapper.save(orderItem);
		}
	}
	
	@Override
	public Order selectByUserId(Long userId) {
		return orderMapper.selectByUserId(userId);
	}
	
	@Override
	public List<Order> selectOrderJoinOrderItem(Long userId, Long orderId) {
		try (HintManager hintManager = HintManager.getInstance()) {
			hintManager.addDatabaseShardingValue("t_order_item", userId);
			return orderMapper.selectOrderJoinOrderItem(userId, orderId);
		}
	}
	
	@Override
	public List<Order> selectOrderJoinOrderItemNoSharding(Long userId, Long orderId) {
		return orderMapper.selectOrderJoinOrderItem(userId, orderId);
	}
	
	@Override
	public List<Order> selectOrderJoinConfig(Long userId, Long orderId) {
		return orderMapper.selectOrderJoinConfig(userId, orderId);
	}
	
	@Override
	public Integer saveConfig(Config config) {
		return configMapper.save(config);
	}
	
	@Override
	public Config selectConfig(Integer id) {
		return configMapper.selectById(id);
	}
}
