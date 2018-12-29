package com.jokey.bingo.mq;

import com.jokey.bingo.entity.WebSocketMsgEntity;
import com.jokey.bingo.queue.AbstractMessageQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author yiheni
 */
public class RabbitMessageQueue extends AbstractMessageQueue {

    @Value("${rabbitmq.websocket.msg.queue}")
    private String webSocketMsgQueue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(WebSocketMsgEntity entity) {
        //没有指定exchange，则使用默认名为“”的exchange，binding名与queue名相同
        rabbitTemplate.convertAndSend(webSocketMsgQueue, entity);
    }
}
