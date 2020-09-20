package com.jokey.statemachine.complex.persist;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.persist
 * @comment: 在内存持久化状态机
 */
@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents, String> {
	
	private Map<String, StateMachineContext<OrderStates, OrderEvents>> map = new ConcurrentHashMap<>();
	
	@Override
	public void write(StateMachineContext<OrderStates, OrderEvents> stateMachineContext, String key) throws Exception {
		map.put(key, stateMachineContext);
	}
	
	@Override
	public StateMachineContext<OrderStates, OrderEvents> read(String key) throws Exception {
		return map.get(key);
	}
}
