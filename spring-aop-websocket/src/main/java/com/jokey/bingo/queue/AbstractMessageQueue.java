package com.jokey.bingo.queue;

import com.jokey.bingo.entity.WebSocketMsgEntity;

public abstract class AbstractMessageQueue {

    public abstract void send(WebSocketMsgEntity entity);
}
