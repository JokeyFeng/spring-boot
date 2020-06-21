package com.jokey.bingo.algo.sort.swap;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.swap
 * @comment: 冒泡排序
 */
public class BubbleSort {
	
	public static int[] sort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		//外层循环一次为一趟排序
		for (int i = 0; i < array.length; i++) {
			//设置一个标识，判断这趟排序是否发生交换，如果未发生交换，说明数组已经有序，不必要再排序了
			boolean isSwap = false;
			//内层循环一次为一次相邻的比较，array.length - 1 - i是因为执行完i轮，数组后面的i个元素都已经有序，不必要继续比较。
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j + 1] < array[j]) {
					int temp = array[j + 1];
					array[j + 1] = array[j];
					array[j] = temp;
					isSwap = true;
				}
			}
			if (!isSwap) {
				break;
			}
		}
		
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = {3, 38, 5, 15, 48, 26, 27, 4, 36, 19, 50, 48, 46};
		array = BubbleSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
