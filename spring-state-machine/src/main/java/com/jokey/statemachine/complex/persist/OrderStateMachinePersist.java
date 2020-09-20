package com.jokey.statemachine.complex.persist;

import com.jokey.statemachine.dto.OrderReq;
import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * @date: 2020/9/14
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.persist
 * @comment: 伪持久化和中间段的状态机
 * 我们设想一个业务场景，就比如订单吧，我们一般的设计都会把订单状态存到订单表里面，
 * 其他的业务信息也都有表保存，而状态机的主要作用其实是规范整个订单业务流程的状态和事件，
 * 所以状态机要不要保存真的不重要，我们只需要从订单表里面把状态取出来，知道当前是什么状态，
 * 然后伴随着业务继续流浪到下一个状态节点就好了。我们先实现一个StateMachinePersist，
 * 因为我不想真的持久化，所以就敷衍一下，持久化是什么，啥也不干。
 * 不持久化的持久化类是为啥呢，主要就是为了取一个任何状态节点的状态机。
 */
@Slf4j
@Component
public class OrderStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents, OrderReq> {
	
	
	@Override
	public void write(StateMachineContext<OrderStates, OrderEvents> stateMachineContext, OrderReq orderReq) throws Exception {
		log.warn("很抱歉，我就是持久化来自订单[{}]的状态机[{}]", orderReq.getOrderId(), stateMachineContext.getId());
	}
	
	/**
	 * 不持久化的持久化类是为啥呢，主要就是为了取一个任何状态节点的状态机。
	 *
	 * @param orderReq
	 * @return
	 * @throws Exception
	 */
	@Override
	public StateMachineContext<OrderStates, OrderEvents> read(OrderReq orderReq) throws Exception {
		StateMachineContext<OrderStates, OrderEvents> result =
				new DefaultStateMachineContext<>(OrderStates.valueOf(orderReq.getOrderState()), null, null, null, null, "orderMachine");
		return result;
	}
}
