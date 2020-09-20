package com.jokey.statemachine.complex.controller;

import com.jokey.statemachine.complex.builder.OrderStateMachineBuilder;
import com.jokey.statemachine.dto.OrderReq;
import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/9/9
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.controller
 * @comment: 状态机持久化
 */
@Slf4j
@RestController
@RequestMapping("/persist")
public class PersistStateMachineController {
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private OrderStateMachineBuilder orderStateMachineBuilder;
	
	/**
	 * 演示过Message接口实现参数传递
	 *
	 * @param orderId
	 * @throws Exception
	 */
	@PostMapping("/testOrder")
	public void testOrder(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		log.info("======{}======", stateMachine.getId());
		//创建流程
		stateMachine.start();
		//触发PAY事件
		stateMachine.sendEvent(OrderEvents.PAY);
		//触发RECEIVE事件
		OrderReq orderReq = new OrderReq();
		orderReq.setOrderId(orderId);
		orderReq.setBuyer("Jokey");
		orderReq.setPhone("13622255841");
		orderReq.setAddress("白云区同和地铁站");
		orderReq.setOrderAction("收到货了");
		Message<OrderEvents> eventsMessage =
				MessageBuilder.withPayload(OrderEvents.RECEIVE)
						.setHeader("order", orderReq)
						.setHeader("receiveResult", "已收货").build();
		stateMachine.sendEvent(eventsMessage);
		//获取最终的状态
		log.info("[{}]订单状态机的最终状态:{}", orderId, stateMachine.getState().getId());
	}
	
	//==========================演示状态机本地内存持久化==========================
	
	@Resource(name = "orderMemoryPersister")
	private StateMachinePersister<OrderStates, OrderEvents, String> orderMemoryPersister;
	
	/**
	 * 本地内存持久化保存状态机
	 *
	 * @param orderId
	 * @throws Exception
	 */
	@PostMapping("/memorySaveStateMachine")
	public void saveStateMachine(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		OrderReq orderReq = new OrderReq();
		orderReq.setOrderId(orderId);
		orderReq.setOrderAction("收货完成");
		orderReq.setAddress("白云区同和地铁站");
		orderReq.setPhone("15626152363");
		orderReq.setBuyer("Jokey");
		//发送PAY事件
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY)
				.setHeader("order", orderReq)
				.setHeader("payResult", "支付成功").build();
		stateMachine.sendEvent(message);
		//持久化保存状态机
		orderMemoryPersister.persist(stateMachine, orderReq.getOrderId());
	}
	
	/**
	 * 从本地内存中取出状态机
	 *
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/memoryGetStateMachine")
	public Map<String, OrderStates> getStateMachine(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		stateMachine = orderMemoryPersister.restore(stateMachine, orderId);
		log.info("恢复状态机后的状态为：{}", stateMachine.getState().getId());
		Map<String, OrderStates> map = new HashMap<>(1);
		map.put("orderState", stateMachine.getState().getId());
		return map;
	}
	
	
	//==============================演示状态机在Redis中持久化====================================
	
	@Resource(name = "orderRedisPersister")
	private StateMachinePersister<OrderStates, OrderEvents, String> orderRedisPersister;
	
	/**
	 * Redis缓存持久化保存状态机
	 *
	 * @param orderId
	 * @throws Exception
	 */
	@PostMapping("/redisSaveStateMachine")
	public void redisSaveStateMachine(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		OrderReq orderReq = new OrderReq();
		orderReq.setOrderId(orderId);
		orderReq.setOrderAction("支付成功");
		orderReq.setAddress("白云区同和地铁站");
		orderReq.setPhone("15626152363");
		orderReq.setBuyer("Bingo");
		//发送PAY事件
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY)
				.setHeader("order", orderReq)
				.setHeader("payResult", "支付成功").build();
		stateMachine.sendEvent(message);
		//持久化stateMachine
		orderRedisPersister.persist(stateMachine, orderReq.getOrderId());
	}
	
	/**
	 * 从Redis缓存中取出状态机
	 *
	 * @param orderId
	 * @throws Exception
	 */
	@PostMapping("/redisGetStateMachine")
	public Map<String, OrderStates> testRestore(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		orderRedisPersister.restore(stateMachine, orderId);
		log.info("恢复状态机后的状态为：{}", stateMachine.getState().getId());
		Map<String, OrderStates> map = new HashMap<>(1);
		map.put("orderState", stateMachine.getState().getId());
		return map;
	}
	
	
	//=================演示不需要持久化的状态机，根据订单状态构造一个任何状态节点的状态机==============
	
	@Resource
	private StateMachinePersister<OrderStates, OrderEvents, OrderReq> orderPersister;
	
	/**
	 * 用builder建了一个新的状态机，用restore过了一手，就已经是一个到达order指定状态的老司机状态机了。
	 * 在这里，持久化不是本意，让状态机能够随时抓换到任意状态节点才是目的。
	 * 在实际的企业开发中，不可能所有情况都是从头到尾的按状态流程来，会有很多意外，
	 * 比如历史数据，故障重启后的遗留流程......，所以这种可以任意调节状态的才是我们需要的状态机。
	 *
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/restoreStateMachine")
	public StateMachine restore(String orderId) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		//构造订单
		OrderReq orderReq = new OrderReq();
		orderReq.setOrderId(orderId);
		orderReq.setOrderState(OrderStates.WAIT_FOR_RECEIVE.toString());
		//恢复状态机
		orderPersister.restore(stateMachine, orderReq);
		log.warn("恢复后的状态：{}", stateMachine.getState().getId());
		return stateMachine;
		
	}
}
