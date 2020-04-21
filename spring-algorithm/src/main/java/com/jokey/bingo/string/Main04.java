package com.jokey.bingo.string;

import java.util.Scanner;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.string
 * @comment:
 */
public class Main04 {
	public static void main(String[] args) {
		
		// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int a = in.nextInt();
			int b = in.nextInt();
			System.out.println(a + b);
		}
	}
}
