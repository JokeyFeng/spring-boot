package com.jokey.bingo.algo.sort.swap;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.swap
 * @comment: 简单选择排序
 */
public class SimpleSelectionSort {
	
	public static int[] sort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		
		for (int i = 0; i < array.length; i++) {
			int minIndex = i;
			for (int j = i; j < array.length; j++) {
				//找到最小的数
				if (array[j] < array[minIndex]) {
					//将最小数的索引保存
					minIndex = j;
				}
			}
			//将最小数和无序区的第一个数交换
			int temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;
		}
		
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = {32, 1, 4, 8, 45, 21, 20, 8, 55, 33, 99};
		array = SimpleSelectionSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
