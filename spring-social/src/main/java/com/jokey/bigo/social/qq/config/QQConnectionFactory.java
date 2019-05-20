package com.jokey.bigo.social.qq.config;

import com.jokey.bigo.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author Zhengjingfeng
 * @created 2019/5/20 15:28
 * @comment 连接服务提供商的工厂类
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
