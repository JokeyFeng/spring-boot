package com.jokey.bingo.niuke.string;

import java.util.Scanner;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.string
 * @comment:
 */
public class Main05 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int ans = 0, x;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				x = sc.nextInt();
				ans += x;
			}
		}
		System.out.println(ans);
	}
}
