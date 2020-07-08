package com.jokey.sharding;

import com.jokey.sharding.entity.Config;
import com.jokey.sharding.entity.Order;
import com.jokey.sharding.entity.OrderItem;
import com.jokey.sharding.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingApplicationTest {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 广播表保存
	 * 对所有数据源进行广播
	 */
	@Test
	public void testSaveConfig() {
		for (int i = 40; i < 50; i++) {
			Config config = new Config();
			config.setCreateTime(LocalDateTime.now());
			config.setLastModifyTime(LocalDateTime.now());
			config.setRemark("config->" + i);
			orderService.saveConfig(config);
		}
		System.out.println(LocalDateTime.now());
	}
	
	/**
	 * 广播表查询
	 * 随机选择数据源
	 *
	 * @param
	 * @return void
	 * @author hujy
	 * @date 2019-09-20 11:23
	 */
	@Test
	public void testSelectConfig() {
		Config config1 = orderService.selectConfig(5);
		System.out.println(config1);
		
		Config config2 = orderService.selectConfig(7);
		System.out.println(config2);
	}
	
	/**
	 * 与广播表关联
	 *
	 * @param
	 * @return void
	 * @author hujy
	 * @date 2019-09-20 11:24
	 */
	@Test
	public void testSelectOrderJoinConfig() {
		List<Order> o1 = orderService.selectOrderJoinConfig(1002L, 10002L);
		System.out.println(o1);
		List<Order> o2 = orderService.selectOrderJoinConfig(1007L, 10007L);
		System.out.println(o2);
	}
	
	/**
	 * 路由保存
	 */
	@Test
	public void testSaveOrder() {
		for (int i = 0; i < 10; i++) {
			long orderId = 10000 + i;
			long userId = 1000 + i;
			
			Order o = new Order();
			o.setOrderId(orderId);
			o.setUserId(userId);
			o.setConfigId(i);
			o.setRemark("save.order");
			orderService.saveOrder(o);
			
			OrderItem oi = new OrderItem();
			oi.setOrderId(orderId);
			oi.setRemark("save.orderItem");
			orderService.saveOrderItem(oi, userId);
		}
	}
	
	/**
	 * 根据分片键查询
	 */
	@Test
	public void testSelectByUserId() {
		long userId = 1002;
		Order o1 = orderService.selectByUserId(userId);
		System.out.println(o1);
		
		userId = 1003;
		Order o2 = orderService.selectByUserId(userId);
		System.out.println(o2);
	}
	
	/**
	 * 与分片子表关联
	 */
	@Test
	public void testSelectOrderJoinOrderItem() {
		// 指定了子表分片规则
		List<Order> o1 = orderService.selectOrderJoinOrderItem(1002L, 10002L);
		System.out.println(o1);
		// 未指定子表分片规则：导致子表的全路由
		List<Order> o2 = orderService.selectOrderJoinOrderItemNoSharding(1002L, 10002L);
		System.out.println(o2);
	}
	
	@Test
	public void testSelectByUserList() {
		List<Long> userList = Arrays.asList(1002L, 1003L, 1004L, 1005L, 1006L);
		List<Order> orderList = orderService.selectByUserList(userList);
		System.out.println(orderList);
	}
	
}
