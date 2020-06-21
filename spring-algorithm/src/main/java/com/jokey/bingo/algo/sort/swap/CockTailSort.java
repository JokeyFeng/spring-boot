package com.jokey.bingo.algo.sort.swap;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.swap
 * @comment: 鸡尾酒排序
 */
public class CockTailSort {
	
	public static int[] sort(int[] array) {
		//定义左右边界点
		int left = 0, right = array.length - 1;
		while (left < right) {
			for (int i = left; i < right; i++) {
				if (array[i + 1] < array[i]) {
					int temp = array[i + 1];
					array[i + 1] = array[i];
					array[i] = temp;
				}
			}
			right--;
			
			for (int i = right; i > left; i--) {
				if (array[i - 1] > array[i]) {
					int temp = array[i - 1];
					array[i - 1] = array[i];
					array[i] = temp;
				}
			}
			left++;
		}
		
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = {3, 38, 5, 15, 48, 26, 27, 4, 36, 19, 50, 48, 46};
		array = CockTailSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
