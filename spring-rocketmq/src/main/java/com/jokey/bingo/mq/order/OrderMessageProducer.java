package com.jokey.bingo.mq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/4/20
 * @project: spring-boot
 * @package: com.jokey.bingo.mq.order
 * @comment:
 */
public class OrderMessageProducer {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			DefaultMQProducer producer = new DefaultMQProducer("order_message_producer_group");
			producer.setNamesrvAddr("192.168.12.151:9876");
			producer.start();
			
			String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
			for (int i = 0; i < 100; i++) {
				int orderId = i % 10;
				Message msg =
						new Message("OrderMessageTopic", tags[i % tags.length], "KEY" + i,
								("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						Integer orderId = (Integer) arg;
						int index = orderId % mqs.size();
						return mqs.get(index);
					}
				}, orderId);
				
				System.out.printf("%s%n", sendResult);
			}
			
			producer.shutdown();
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
