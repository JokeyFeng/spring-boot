package com.jokey.bingo.handler;

import com.jokey.bingo.event.LongEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @author JokeyFeng
 * @date: 2020/3/16
 * @project: spring-boot
 * @package: com.jokey.bingo.handler
 * @comment: 需要一个事件消费者，也就是一个事件处理器。这个事件处理器简单地把事件中存储的数据打印到终端
 */
public class LongEventHandler implements EventHandler<LongEvent> {
	
	private String clientName;
	
	public LongEventHandler(String clientName) {
		this.clientName = clientName;
	}
	
	/**
	 * 事件监听，类似观察者模式
	 *
	 * @param event
	 * @param sequence
	 * @param endOfBatch
	 * @throws Exception
	 */
	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		//向Client推送消息
		System.out.println(Thread.currentThread().getName() + "==>> " + this.clientName + event.getValue());
	}
}
