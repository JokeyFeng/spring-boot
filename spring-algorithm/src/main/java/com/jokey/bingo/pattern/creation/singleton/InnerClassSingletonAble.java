package com.jokey.bingo.pattern.creation.singleton;

import java.io.*;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.pattern.creation.singleton
 * @comment: 静态内部类实现单例的增强
 * @see InnerClassSingleton 实现的单例无法保证反射或者反序列化可以生成新的对象
 * <p>
 * 通过以下两点做法解决问题：
 * 1、针对反射，解决起来比较简单，可以在构造方法中判断一下InnerClassSingleton.INSTANCE，如果不为null，则抛出异常。
 * 2、针对反序列化，可以实现序列化接口Serializable,重写readResolve方法，返回单例对象InnerClassSingleton。
 */
public class InnerClassSingletonAble implements Serializable {
	/**
	 * 标识
	 */
	private String name;
	
	private InnerClassSingletonAble() {
		if (InnerClass.INSTANCE != null) {
			throw new RuntimeException("can not be invoked");
		}
		name = String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 内部类持有外部类的对象实例
	 */
	private static class InnerClass {
		private static final InnerClassSingletonAble INSTANCE = new InnerClassSingletonAble();
	}
	
	public static InnerClassSingletonAble getInstance() {
		return InnerClass.INSTANCE;
	}
	
	public void print() {
		System.out.println("InnerClass 懒加载：" + name);
	}
	
	/**
	 * 重写
	 *
	 * @return
	 */
	private Object readResolve() {
		return InnerClass.INSTANCE;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		//获取对象实例
		InnerClassSingletonAble instance = InnerClassSingletonAble.getInstance();
		instance.print();
		
		//序列化
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file"));
		oos.writeObject(instance);
		oos.close();
		//反序列化
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file"));
		InnerClassSingletonAble newInstance = (InnerClassSingletonAble) ois.readObject();
		newInstance.print();
		System.out.println(instance == newInstance);
		
		//反射
		InnerClassSingletonAble reflectInstance = InnerClassSingletonAble.class.newInstance();
		reflectInstance.print();
		System.out.println(instance == reflectInstance);
	}
	
}
