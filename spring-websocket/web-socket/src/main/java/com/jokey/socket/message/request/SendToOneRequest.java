package com.jokey.socket.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:27
 * @comment 发送给指定人的私聊消息的 Message
 */
@Getter
@Setter
public class SendToOneRequest extends AbstractRequest {

    public static final String TYPE = "SEND_TO_ONE_REQUEST";

    /**
     * 发送给的用户
     */
    private String toUser;

    /**
     * 内容
     */
    private String content;
}
