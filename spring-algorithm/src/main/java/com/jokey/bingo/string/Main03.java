package com.jokey.bingo.string;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.string
 * @comment:
 */
public class Main03 {
	
	static {
		System.out.println("a");
	}
	
	{
		System.out.println("b");
	}
	
	private final String c = c();
	private final String d;
	
	private static String c() {
		System.out.println("c");
		return "c";
	}
	
	Main03(final String val) {
		this.d = val;
		System.out.println("d");
	}
	
	Main03() {
		this("");
		System.out.println("e");
	}
	
	{
		System.out.println("f");
	}
	
	
}

class A extends Main03 {
	static {
		System.out.println("g");
	}
	
	{
		System.out.println("h");
	}
	
	public A() {
		System.out.println("i");
	}
	
	public static void main(String[] args) {
		new A();
	}
}