package com.jokey.bingo.mq;

import com.alibaba.fastjson.JSON;
import com.jokey.bingo.entity.WebSocketMsgEntity;
import com.jokey.bingo.queue.AbstractMessageQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yiheni
 */
@Component
public class RabbitMessageQueue extends AbstractMessageQueue {
	
	
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 构造方法注入rabbitTemplate
	 */
	@Autowired
	public RabbitMessageQueue(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public void send(WebSocketMsgEntity msg) {
		//没有指定exchange，则使用默认名为“”的exchange，binding名与queue名相同
		rabbitTemplate.convertAndSend(RabbitMQConfig.WEB_SOCKET_MSG_DIRECT_EXCHANGE, RabbitMQConfig.WEB_SOCKET_MSG_ROUTING_KEY, JSON.toJSONString(msg));
	}
}
