package com.jokey.socket.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jokey.socket.message.Message;
import com.jokey.socket.message.handler.MessageHandler;
import com.jokey.socket.message.request.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Zhengjingfeng
 * @created 2020/7/10 14:47
 * @comment
 */
@Controller
@ServerEndpoint("/")
public class WebsocketServerEndpoint implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 消息类型与 MessageHandler 的映射
     * <p>
     * 注意，这里设置成静态变量。虽然说 WebsocketServerEndpoint 是单例，但是 Spring Boot 还是会为每个 WebSocket 创建一个 WebsocketServerEndpoint Bean 。
     */
    private static final Map<String, MessageHandler> MESSAGE_HANDLERS = new HashMap<>();

    /**
     * 注入应用上下文
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 建立连接
     *
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("[onOpen][session({}) 接入]", session);
        // 1、解析accessToken
        List<String> accessTokenList = session.getRequestParameterMap().get("accessToken");
        String accessToken = CollectionUtils.isEmpty(accessTokenList) ? null : accessTokenList.get(0);
        // 2、创建AuthRequest
        AuthRequest authRequest = new AuthRequest();
        authRequest.setAccessToken(accessToken);
        MessageHandler<AuthRequest> messageHandler = MESSAGE_HANDLERS.get(AuthRequest.TYPE);
        if (Objects.isNull(messageHandler)) {
            logger.error("[onOpen][认证消息类型，不存在消息处理器]");
            return;
        }
        messageHandler.execute(session, authRequest);
    }

    /**
     * 接收消息
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("[onOpen][session({}) 接收到一条消息({})]", session, message);
        try {
        // 1、获取消息类型
        JSONObject jsonMessage = JSON.parseObject(message);
        String messageType = jsonMessage.getString("type");
        // 2、获取消息处理器
        MessageHandler messageHandler = MESSAGE_HANDLERS.get(messageType);
        if (Objects.isNull(messageHandler)) {
            logger.error("[onMessage][消息类型({}) 不存在消息处理器]", messageType);
            return;
        }

        // <3> 解析消息
        Class<? extends Message> messageClass = this.getMessageClass(messageHandler);
        // <4> 处理消息
        Message messageObj = JSON.parseObject(jsonMessage.getString("body"), messageClass);
        messageHandler.execute(session, messageObj);
        } catch (Throwable throwable) {
            logger.info("[onMessage][session({}) message({}) 发生异常]", session, throwable);
        }
    }

    /**
     * 通过解析其类上的泛型，获得消息类型对应的Class类
     *
     * @param messageHandler
     * @return
     */
    private Class<? extends Message> getMessageClass(MessageHandler messageHandler) {
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(messageHandler);
        // 获得接口的 Type 数组
        Type[] genericInterfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        if ((Objects.isNull(genericInterfaces) || genericInterfaces.length == 0) && Objects.nonNull(superclass)) {
            genericInterfaces = superclass.getGenericInterfaces();
            //superclass = targetClass.getSuperclass()
        }
        if (Objects.nonNull(genericInterfaces)) {
            // 遍历泛型数组
            for (Type type : genericInterfaces) {
                // 要求type是泛型
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是MessageHandler接口
                    if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
    }

    /**
     * 关闭连接
     *
     * @param session
     * @param closeReason
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info("[onClose][session({}) 连接关闭。关闭原因是({})}]", session, closeReason);
    }

    /**
     * 实现
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("[onClose][session({}) 发生异常]", session, throwable);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values().forEach(item -> MESSAGE_HANDLERS.put(item.getType(), item));
        logger.info("[afterPropertiesSet][消息处理器数量：{}]", MESSAGE_HANDLERS.size());
    }
}
