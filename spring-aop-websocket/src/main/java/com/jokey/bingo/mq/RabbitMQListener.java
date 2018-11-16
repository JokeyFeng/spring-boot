package com.jokey.bingo.mq;

import com.jokey.bingo.entity.WebSocketMsgEntity;
import com.jokey.bingo.service.RabbitMQService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author jokey
 */
@Component
public class RabbitMQListener {

    @Autowired
    private RabbitMQService mqService;

    /**
     * WebSocket推送监听器
     *
     * @param socketMsgEntity
     * @param deliveryTag
     * @param channel
     */
    @RabbitListener(queues = "websocket_msg_queue")
    public void webSocketMsgListener(@Payload WebSocketMsgEntity socketMsgEntity, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        mqService.handleWebSocketMsg(socketMsgEntity, deliveryTag, channel);
    }
}
