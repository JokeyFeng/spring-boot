package com.jokey.bingo.pattern.creation.singleton;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.pattern.creation.singleton
 * @comment: 双重锁校验生成单例，原理：利用volatile关键字禁止指令重排序
 * <p>
 * 这里使用 volatile，主要是为了禁止指令重排序。
 * <p>
 * 主要就是针对 instance = new Lazy(); 这1行命令，在 JVM 中至少对应3条指令：
 * 1. 给 instance 分配内存空间。
 * 2. 调用 Lazy 的构造方法等来初始化 instance。
 * 3. 将 instance 对象指向分配的内存空间（执行完这一步，instance 就不是 null 了）。
 * <p>
 * 这里需要注意，JVM 会对指令进行优化排序，就是第 2 步与第 3 步的顺序是不一定的，可能是 1-2-3 ，也可能是 1-3-2 。
 * <p>
 * 如果是后者，可能1个线程执行完 1-3 之后，另一个线程进入了
 * <p>
 * 双重检查的必要性：
 * 第二个 if 判定：是为了保证当有两个线程同时通过了第一个 if 判定，一个线程获取到锁，生成了 Lazy 的一个实例，然后第二个线程获取到锁，
 * 如果没有第二个 if 判断，那么此时会再次生成生成 Lazy 的一个实例。
 * 第一个 if 判定：是为了保证多线程同时执行，如果没有第一个 if 判断，所有线程都会串行执行，效率低下。
 */
public class DoubleCheckSingleton {
	
	private volatile static DoubleCheckSingleton INSTANCE = null;
	
	/**
	 * 私有化构造方法
	 */
	private DoubleCheckSingleton() {
	}
	
	public static DoubleCheckSingleton getINSTANCE() {
		if (INSTANCE == null) {
			synchronized (DoubleCheckSingleton.class) {
				if (INSTANCE == null) {
					INSTANCE = new DoubleCheckSingleton();
				}
			}
		}
		return INSTANCE;
	}
}
