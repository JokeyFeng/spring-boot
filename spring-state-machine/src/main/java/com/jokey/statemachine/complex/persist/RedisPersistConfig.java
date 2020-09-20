package com.jokey.statemachine.complex.persist;

import com.jokey.statemachine.event.OrderEvents;
import com.jokey.statemachine.state.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

/**
 * @author JokeyFeng
 * @date: 2020/9/11
 * @project: spring-boot
 * @package: com.jokey.statemachine.complex.persist
 * @comment: 通过Redis持久化保存状态机的状态
 */
@Configuration
public class RedisPersistConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	/**
	 * 注入RedisStateMachinePersister对象
	 *
	 * @return
	 */
	@Bean(name = "orderRedisPersister")
	public RedisStateMachinePersister<OrderStates, OrderEvents> redisPersister() {
		return new RedisStateMachinePersister<>(this.redisPersist());
	}
	
	/**
	 * 通过redisConnectionFactory创建StateMachinePersist
	 *
	 * @return
	 */
	private StateMachinePersist<OrderStates, OrderEvents, String> redisPersist() {
		RedisStateMachineContextRepository<OrderStates, OrderEvents> redisRepository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
		return new RepositoryStateMachinePersist<>(redisRepository);
	}
}
