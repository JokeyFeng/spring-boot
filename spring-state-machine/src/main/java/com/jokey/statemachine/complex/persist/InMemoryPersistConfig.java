package com.jokey.statemachine.complex.persist;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.persist
 * @comment: 本地内存持久化状态机配置
 */
@Component
public class InMemoryPersistConfig {
	
	@Autowired
	private InMemoryStateMachinePersist inMemoryStateMachinePersist;
	
	@Bean("orderMemoryPersister")
	public StateMachinePersister<OrderStates, OrderEvents, String> getPersister() {
		return new DefaultStateMachinePersister<>(inMemoryStateMachinePersist);
	}
}
