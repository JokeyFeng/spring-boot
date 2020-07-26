package com.jokey.bingo.niuke.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.string
 * @comment: 链接：https://ac.nowcoder.com/acm/contest/320/H
 * 来源：牛客网
 * <p>
 * 题目描述
 * 对输入的字符串进行排序后输出
 * 输入描述:
 * 输入有两行，第一行n
 * <p>
 * 第二行是n个空格隔开的字符串
 * 输出描述:
 * 输出一行排序后的字符串，空格隔开，无结尾空格
 * 示例1
 * 输入
 * 复制
 * 5
 * c d a bb e
 * 输出
 * 复制
 * a bb c d e
 */
public class Main01 {
	
	public static void main(String[] args) throws IOException {
		//Main01.normal();
		//Main01.bufferStream();
		System.out.println("a".toUpperCase());
	}
	
	/**
	 * 普通做法
	 */
	public static void normal() {
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		String[] words = new String[i];
		for (int n = 0; n < i; n++) {
			words[n] = sc.next();
		}
		Arrays.sort(words);
		for (String s : words) {
			System.out.print(s + " ");
		}
	}
	
	/**
	 * 使用流处理
	 */
	public static void bufferStream() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str;
		while ((str = reader.readLine()) != null) {
			int count = Integer.parseInt(str.trim());
			str = reader.readLine();
			String[] strArr = str.split(" ");
			Arrays.sort(strArr);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < count; i++) {
				sb.append(strArr[i]).append(" ");
			}
			System.out.println(sb.toString().trim());
		}
	}
}
