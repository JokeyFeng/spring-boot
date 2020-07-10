package com.jokey.socket.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:32
 * @comment 发送消息给一个用户的 Message
 * 在服务端接收到发送消息的请求，需要转发消息给对应的人
 * 相比 SendToOneRequest 来说，少一个 toUser 字段。因为，我们可以通过 WebSocket 连接，已经知道发送给谁了。
 */
@Getter
@Setter
public class SendToUserRequest extends AbstractRequest {

    public static final String TYPE = "SEND_TO_USER_REQUEST";

    /**
     * 内容
     */
    private String content;
}
