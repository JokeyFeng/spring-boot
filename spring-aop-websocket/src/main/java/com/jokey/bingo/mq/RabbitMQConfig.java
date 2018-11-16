package com.jokey.bingo.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * @author jokey
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private String port;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.websocket.msg.queue}")
    private String webSocketMsgQueue;

    @Bean
    public ConnectionFactory connectionFactory() throws IOException {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
//        factory.setVirtualHost("test")
        factory.setHost(host);
        factory.setPort(Integer.valueOf(port));
        factory.setPublisherConfirms(true);

        //设置队列参数，是否持久化、队列TTL、队列消息TTL等
        factory.createConnection().createChannel(false).queueDeclare(webSocketMsgQueue, true, false, false, null);

        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() throws IOException {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() throws IOException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
