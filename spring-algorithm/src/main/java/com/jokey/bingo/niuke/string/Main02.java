package com.jokey.bingo.niuke.string;

import java.io.*;

/**
 * @author JokeyFeng
 * @date: 2020/4/12
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke.string
 * @comment: 链接：https://ac.nowcoder.com/acm/contest/320/A
 * 来源：牛客网
 * <p>
 * 题目描述
 * 计算a+b
 * <p>
 * 输入描述:
 * 输入包括两个正整数a,b(1 <= a, b <= 10^9),输入数据包括多组。
 * 输出描述:
 * 输出a+b的结果
 * 示例1
 * 输入
 * 复制
 * 1 5
 * 10 20
 * 输出
 * 复制
 * 6
 * 30
 */
public class Main02 {
	
	public static void main(String[] args) throws IOException {
		//Main02.bufferStream();
		Main02.streamToken();
	}
	
	public static void bufferStream() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		//int count = 0;
		while (!reader.ready()) {
			String[] strs = reader.readLine().split(" ");
			int[] nums = new int[2];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.parseInt(strs[i]);
			}
			sb.append(nums[0] + nums[1]);
			if (!reader.ready()) {
				sb.append("\n");
			}
			/*count++;
			if (count == 2) {
				break;
			}*/
		}
		System.out.println(sb.toString());
		reader.close();
	}
	
	public static void streamToken() throws IOException {
		StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		
		int a, b, count = 0;
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.println(a + b);
			/*count++;
			if (count == 2) {
				break;
			}*/
		}
		out.flush();
	}
}
