package com.jokey.socket.message.request;

import com.jokey.socket.message.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:11
 * @comment
 */
@Getter
@Setter
public abstract class AbstractRequest implements Message {

    /**
     * 请求唯一标识id
     */
    private String requestId = UUID.randomUUID().toString().replace("-", "");
}
