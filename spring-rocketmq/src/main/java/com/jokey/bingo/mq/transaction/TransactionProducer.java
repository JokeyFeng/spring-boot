package com.jokey.bingo.mq.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/4/21
 * @project: spring-boot
 * @package: com.jokey.bingo.mq.transaction
 * @comment:
 */
public class TransactionProducer {
	
	public static void main(String[] args) throws MQClientException, InterruptedException {
		TransactionListener transactionListener = new LocalTransactionListener();
		TransactionMQProducer producer = new TransactionMQProducer("transaction_message_producer_group");
		ExecutorService executorService = new ThreadPoolExecutor(
				2, 5,
				100, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(2000),
				r -> {
					Thread thread = new Thread(r);
					thread.setName("client-transaction-msg-check-thread");
					return thread;
				});
		producer.setNamesrvAddr("192.168.12.151:9876");
		producer.setExecutorService(executorService);
		producer.setTransactionListener(transactionListener);
		producer.start();
		
		String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
		for (int i = 0; i < 10; i++) {
			try {
				Message msg =
						new Message("TransactionMassageTopic", tags[i % tags.length], "KEY-" + i,
								("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.sendMessageInTransaction(msg, null);
				System.out.printf("%s%n", sendResult);
				
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (MQClientException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < 100000; i++) {
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		producer.shutdown();
	}
}
