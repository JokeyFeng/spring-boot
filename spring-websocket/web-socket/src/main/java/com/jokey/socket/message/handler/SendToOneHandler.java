package com.jokey.socket.message.handler;

import com.jokey.socket.message.request.SendToOneRequest;
import com.jokey.socket.message.request.SendToUserRequest;
import com.jokey.socket.message.response.SendResponse;
import com.jokey.socket.utils.WebSocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 15:58
 * @comment 处理 SendToOneRequest消息
 */
@Component
public class SendToOneHandler implements MessageHandler<SendToOneRequest> {

    @Override
    public void execute(Session session, SendToOneRequest message) {
        // 创建转发的消息
        SendToUserRequest sendToUserRequest = new SendToUserRequest();
        sendToUserRequest.setRequestId(message.getRequestId());
        sendToUserRequest.setContent(message.getContent());
        // 广播发送
        boolean success = WebSocketUtil.send(message.getToUser(), SendToUserRequest.TYPE, sendToUserRequest);
        // 这里，假装直接成功
        SendResponse sendResponse = new SendResponse();
        sendResponse.setRequestId(message.getRequestId());
        if (success) {
            sendResponse.setCode(0);
            sendResponse.setMessage("发送成功");
        } else {
            sendResponse.setCode(1);
            sendResponse.setMessage("发送失败");
        }
        WebSocketUtil.send(session, SendResponse.TYPE, sendResponse);
    }

    @Override
    public String getType() {
        return SendToOneRequest.TYPE;
    }
}
