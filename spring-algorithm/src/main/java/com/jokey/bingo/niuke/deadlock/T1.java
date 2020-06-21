package com.jokey.bingo.niuke.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/5/20
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.deadlock
 * @comment:
 */
public class T1 implements Runnable {
	
	private Object o1;
	private Object o2;
	
	T1(Object o1, Object o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	
	@Override
	public void run() {
		synchronized (o1) {
			System.out.println("T1已经获得锁：" + o1.hashCode());
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("T1等待获取锁:" + o2.hashCode());
			synchronized (o2) {
				System.out.println("T1已经获取锁:" + o2.hashCode());
			}
		}
	}
}