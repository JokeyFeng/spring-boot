package com.jokey.socket.message.handler;

import com.jokey.socket.message.request.SendToAllRequest;
import com.jokey.socket.message.request.SendToUserRequest;
import com.jokey.socket.message.response.SendResponse;
import com.jokey.socket.utils.WebSocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 16:04
 * @comment
 */
@Component
public class SendToAllHandler implements MessageHandler<SendToAllRequest> {


    @Override
    public void execute(Session session, SendToAllRequest message) {
        // 创建转发的消息
        SendToUserRequest sendToUserRequest = new SendToUserRequest();
        sendToUserRequest.setRequestId(message.getRequestId());
        sendToUserRequest.setContent(message.getContent());
        // 广播发送
        WebSocketUtil.broadcast(SendToUserRequest.TYPE, sendToUserRequest);
        // 发送成功，应答发送人
        SendResponse sendResponse = new SendResponse();
        sendResponse.setCode(0);
        sendResponse.setMessage("发送成功");
        sendResponse.setRequestId(message.getRequestId());
        WebSocketUtil.send(session, SendResponse.TYPE, sendResponse);
    }

    @Override
    public String getType() {
        return SendToAllRequest.TYPE;
    }
}
