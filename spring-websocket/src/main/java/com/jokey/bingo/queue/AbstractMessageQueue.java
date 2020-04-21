package com.jokey.bingo.queue;

import com.jokey.bingo.entity.WebSocketMsgEntity;

/**
 * @author yiheni
 */
public abstract class AbstractMessageQueue {

    /**
     * 发消息
     *
     * @param entity
     */
    public abstract void send(WebSocketMsgEntity entity);
}
