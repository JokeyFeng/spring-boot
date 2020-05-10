package com.jokey.bingo.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author jokey
 */
@EnableRabbit
@Configuration
public class RabbitMQConfig {
	
	/*@Value("${rabbitmq.websocket.msg.queue}")
	private String webSocketMsgQueue;
	
	@Autowired
	private RabbitProperties rabbitProperties;
	
	@Bean
	public ConnectionFactory connectionFactory() throws IOException {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setUsername(rabbitProperties.getUsername());
		factory.setPassword(rabbitProperties.getPassword());
		factory.setHost(rabbitProperties.getHost());
		factory.setPort(Integer.valueOf(rabbitProperties.getPort()));
		factory.setPublisherConfirms(true);
		
		//设置队列参数，是否持久化、队列TTL、队列消息TTL等
		factory.createConnection().createChannel(false).queueDeclare(webSocketMsgQueue, true, false, false, null);
		
		return factory;
	}
	
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	*//**
	 * 必须是prototype类型
	 *
	 * @return
	 * @throws IOException
	 *//*
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() throws IOException {
		return new RabbitTemplate(connectionFactory());
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() throws IOException {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}*/
	
	/**
	 * websocket 消息队列
	 */
	public static final String WEB_SOCKET_MSG_QUEUE = "websocket_msg_queue";
	
	/**
	 * 消息交换机
	 */
	public static final String WEB_SOCKET_MSG_DIRECT_EXCHANGE = "websocket_msg_direct_exchange";
	
	/**
	 * 消息交换机
	 */
	public static final String WEB_SOCKET_MSG_FANOUT_EXCHANGE = "websocket_msg_fanout_exchange";
	
	/**
	 * 消息路由键
	 */
	public static final String WEB_SOCKET_MSG_ROUTING_KEY = "websocket_msg_routing_key";
	
	/**
	 * 消息队列
	 *
	 * @return
	 */
	@Bean
	public Queue msgQueue() {
		return new Queue(WEB_SOCKET_MSG_QUEUE);
	}
	
	/**
	 * 把消息路由到那些binding key与routing key完全匹配的Queue中。
	 *
	 * @return
	 */
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(WEB_SOCKET_MSG_DIRECT_EXCHANGE);
	}
	
	/**
	 * 广播 把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。
	 *
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(WEB_SOCKET_MSG_FANOUT_EXCHANGE);
	}
	
	/**
	 * 消息队列绑定消息交换机
	 *
	 * @return
	 */
	@Bean
	public Binding directBinding() {
		return BindingBuilder.bind(msgQueue()).to(directExchange()).with(WEB_SOCKET_MSG_ROUTING_KEY);
	}
	
	/**
	 * 消息队列绑定消息交换机
	 *
	 * @return
	 */
	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(msgQueue()).to(fanoutExchange());
	}
}
