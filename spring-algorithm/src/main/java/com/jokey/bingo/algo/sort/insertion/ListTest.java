package com.jokey.bingo.algo.sort.insertion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JokeyFeng
 * @date: 2020/5/19
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.insertion
 * @comment:
 */
public class ListTest {
	
	public void a(List<String> aList, List<String> bList) {
		aList.forEach(a -> {
			if (!bList.contains(a)) {
				bList.add(a);
			}
		});
		System.out.println(bList);
	}
	
	public static void main(String[] args) {
		List<String> aList = new ArrayList<>();
		List<String> bList = new ArrayList<>();
		
		aList.add("a");
		aList.add("b");
		aList.add("c");
		aList.add("d");
		
		bList.add("e");
		bList.add("f");
		bList.add("g");
		bList.add("h");
		ListTest test = new ListTest();
		test.a(aList, bList);
		System.out.println(bList);
	}
}
