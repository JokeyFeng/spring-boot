package com.jokey.bingo.pattern.creation.singleton;

import java.io.*;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.pattern.creation.singleton
 * @comment: 静态内部类实现单例
 * <p>
 * 实现原理：
 * 因为只有当调用InnerLazy.INSTANCE时，才会对 InnerClass 类进行初始化，然后才会调用 InnerClassSingleton 的构造方法，这也是由类加载机制保证的：
 * 遇到 new 、getstatic、putstatic 或者 invokestatic 这 4 条字节码指令时，如果没有对类进行初始化，则需要先触发其初始化。
 * 这4个指令对应的 Java 场景是：使用 new 新建一个 Java 对象，访问或者设置一个类的静态字段，访问一个类的静态方法的时候。
 * <p>
 * 缺点：如果使用反射或者反序列化，依旧可以强制生成新的实例。
 */
public class InnerClassSingleton implements Serializable {
	
	/**
	 * 私有化构造方法
	 */
	private InnerClassSingleton() {
		name = String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 静态内部类持有外部类的实例对象
	 */
	private static class InnerClass {
		private static InnerClassSingleton INSTANCE = new InnerClassSingleton();
	}
	
	public static InnerClassSingleton getInstance() {
		return InnerClass.INSTANCE;
	}
	
	//====================举例说明==================
	private String name;
	
	public void print() {
		System.out.println("懒加载：" + name);
	}
	
	public static void main(String[] args) throws Exception {
		InnerClassSingleton instance = InnerClassSingleton.getInstance();
		instance.print();
		
		//反射
		InnerClassSingleton reflectInstance = InnerClassSingleton.class.newInstance();
		reflectInstance.print();
		System.out.println(instance == reflectInstance);
		
		//序列化
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file"));
		oos.writeObject(instance);
		oos.close();
		//反序列化
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file"));
		InnerClassSingleton newInstance = (InnerClassSingleton) ois.readObject();
		newInstance.print();
		System.out.println(instance == newInstance);
	}
}
