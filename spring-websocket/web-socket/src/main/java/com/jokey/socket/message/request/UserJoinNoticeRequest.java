package com.jokey.socket.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:26
 * @comment 用户成功认证之后，会广播用户加入群聊的通知 Message
 */
@Getter
@Setter
public class UserJoinNoticeRequest extends AbstractRequest {

    public static final String TYPE = "USER_JOIN_NOTICE_REQUEST";

    /**
     * 昵称
     */
    private String nickname;
}
