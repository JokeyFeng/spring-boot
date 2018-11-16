package com.jokey.bingo.service;

import com.jokey.bingo.socket.MessageWebSocketHandler;
import com.jokey.bingo.entity.WebSocketMsgEntity;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQService {

    @Autowired
    private MessageWebSocketHandler messageWebSocketHandler;

    /**
     * @param socketMsgEntity
     * @param deliveryTag
     * @param channel
     * @throws IOException
     */
    public void handleWebSocketMsg(WebSocketMsgEntity socketMsgEntity, long deliveryTag, Channel channel) throws IOException {
        try {
            messageWebSocketHandler.sendMessageToUsers(socketMsgEntity.toJsonString(), socketMsgEntity.getToUserIds());
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
