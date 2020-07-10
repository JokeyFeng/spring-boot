package com.jokey.socket.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:28
 * @comment 发送给所有人的群聊消息的 Message
 */
@Getter
@Setter
public class SendToAllRequest extends AbstractRequest {

    public static final String TYPE = "SEND_TO_ALL_REQUEST";

    /**
     * 内容
     */
    private String content;
}
