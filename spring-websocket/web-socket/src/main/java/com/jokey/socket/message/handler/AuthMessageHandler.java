package com.jokey.socket.message.handler;

import com.jokey.socket.message.request.AuthRequest;
import com.jokey.socket.message.request.UserJoinNoticeRequest;
import com.jokey.socket.message.response.AuthResponse;
import com.jokey.socket.utils.WebSocketUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.Session;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:36
 * @comment
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, AuthRequest message) {
        AuthResponse response = new AuthResponse();
        response.setRequestId(message.getRequestId());
        if (!StringUtils.hasText(message.getAccessToken())) {
            response.setCode(1);
            response.setMessage("认证accessToken未传入");
            WebSocketUtil.send(session, AuthRequest.TYPE, response);
            return;
        }
        //TODO 验证accessToken合法性

        // 判断是否认证成功。这里，假装直接成功
        response.setCode(0);
        response.setMessage("认证成功");
        WebSocketUtil.send(session, AuthResponse.TYPE, response);

        // 添加到 WebSocketUtil 中
        WebSocketUtil.addSession(session, message.getAccessToken());

        // 通知所有人，某个人加入了。这个是可选逻辑，仅仅是为了演示
        // 考虑到代码简化，我们先直接使用 accessToken 作为 User
        UserJoinNoticeRequest notice = new UserJoinNoticeRequest();
        notice.setRequestId(message.getRequestId());
        notice.setNickname(message.getAccessToken());
        WebSocketUtil.broadcast(UserJoinNoticeRequest.TYPE, notice);
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
