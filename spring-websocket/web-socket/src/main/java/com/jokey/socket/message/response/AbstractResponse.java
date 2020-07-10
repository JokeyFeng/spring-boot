package com.jokey.socket.message.response;

import com.jokey.socket.message.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:14
 * @comment
 */
@Setter
@Getter
public abstract class AbstractResponse implements Message {

    /**
     * 请求标识
     */
    private String requestId;

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;
}
