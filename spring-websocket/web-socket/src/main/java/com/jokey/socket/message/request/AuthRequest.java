package com.jokey.socket.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:21
 * @comment 用户认证请求
 */
public class AuthRequest extends AbstractRequest {

    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     * 在 WebSocket 协议中，我们也需要认证当前连接，用户身份是什么。
     * 一般情况下，我们采用用户调用 HTTP 登录接口，登录成功后返回的访问令牌 accessToken
     */
    @Getter
    @Setter
    private String accessToken;
}
