package com.jokey.bingo.mq.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author JokeyFeng
 * @date: 2020/4/20
 * @project: spring-boot
 * @package: com.jokey.bingo.mq.batch
 * @comment:
 */
public class SimpleBatchConsumer {
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("normal_message_consumer_group");
		consumer.setNamesrvAddr("192.168.12.151:9876");
		consumer.subscribe("BatchNormalMessageTopic", "*");
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.setConsumeMessageBatchMaxSize(2);
		consumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
			System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
