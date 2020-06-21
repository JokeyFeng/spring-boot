package com.jokey.bingo.niuke.string;

import java.util.Arrays;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.string
 * @comment:
 */
public class Main07 {
	public static void main(String[] args) {
		Arrays.stream(new int[]{1, 0, 0, 8, 6})
				.filter(x -> x % 2 == 0)
				.peek(x -> System.out.println("peek：" + x))
				.map(x -> x * 10)
				.distinct()
				.forEach(System.out::println);
		System.out.println("=========================================");
		Arrays.stream(new int[]{1, 0, 0, 8, 6})
				.filter(x -> x % 2 == 0)
				//.peek(System.out::println)
				.map(x -> x * 10)
				.distinct()
				.forEach(System.out::println);
	}
}
