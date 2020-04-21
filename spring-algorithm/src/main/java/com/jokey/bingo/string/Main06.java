package com.jokey.bingo.string;

import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.string
 * @comment:
 */
public class Main06 {
	
	private final static Object o1 = new Object();
	private final static Object o2 = new Object();
	
	public static void main(String[] args) {
		Thread a = new Thread(new ThreadA());
		Thread b = new Thread(new ThreadB());
		a.start();
		b.start();
		System.out.println("死锁");
		
	}
	
	static class ThreadA extends Thread {
		@Override
		public void run() {
			synchronized (o1) {
				try {
					System.out.println("获得锁o1");
					System.out.println("等待锁o2");
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) {
					try {
						System.out.println("获得锁o2");
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	static class ThreadB implements Runnable {
		
		@Override
		public void run() {
			synchronized (o2) {
				try {
					System.out.println("获得锁o2");
					System.out.println("等待锁o1");
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o1) {
					try {
						System.out.println("获得锁o1");
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

