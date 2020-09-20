package com.jokey.statemachine.complex.persist;

import com.jokey.statemachine.dto.OrderReq;
import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

/**
 * @author JokeyFeng
 * @date: 2020/9/14
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.persist
 * @comment:
 */
@Configuration
public class OrderPersistConfig {
	
	@Autowired
	private OrderStateMachinePersist orderStateMachinePersist;
	
	@Bean(name = "orderPersister")
	public StateMachinePersister<OrderStates, OrderEvents, OrderReq> orderPersister() {
		return new DefaultStateMachinePersister<>(orderStateMachinePersist);
	}
}
