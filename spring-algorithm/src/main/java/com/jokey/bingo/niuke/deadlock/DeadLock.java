package com.jokey.bingo.niuke.deadlock;

/**
 * @author JokeyFeng
 * @date: 2020/5/20
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.deadlock
 * @comment:
 */
public class DeadLock {
	
	public static void main(String[] args) {
		Object o1 = new Object();
		Object o2 = new Object();
		
		T1 t1 = new T1(o1, o2);
		T1 t2 = new T1(o2, o1);
		new Thread(t1).start();
		new Thread(t2).start();
	}
}
