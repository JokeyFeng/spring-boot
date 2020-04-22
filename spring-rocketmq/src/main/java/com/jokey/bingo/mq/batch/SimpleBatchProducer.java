package com.jokey.bingo.mq.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JokeyFeng
 * @date: 2020/4/20
 * @project: spring-boot
 * @package: com.jokey.bingo.mq.batch
 * @comment: 批量发送消息
 */
public class SimpleBatchProducer {
	
	public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer producer = new DefaultMQProducer("normal_message_producer_group");
		producer.setNamesrvAddr("192.168.12.151:9876");
		producer.start();
		//组装消息
		List<Message> messages = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Message message = new Message(
					"BatchNormalMessageTopic",
					"batchSending",
					UUID.randomUUID().toString(),
					("Hello-" + i).getBytes());
			messages.add(message);
		}
		SendResult sendResult = producer.send(messages);
		producer.shutdown();
		System.out.println(sendResult);
	}
}
