package com.jokey.bingo.algo.sort.insertion;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.insertion
 * @comment: 直接插入排序
 */
public class InsertionSort {
	
	/*public static int[] sort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		int current;
		for (int i = 1; i < array.length; i++) {
			current = array[i];
			int preIndex = i - 1;
			while (preIndex >= 0 && current < array[preIndex]) {
				array[preIndex + 1] = array[preIndex];
				preIndex--;
			}
			array[preIndex + 1] = current;
		}
		return array;
	}
	*/
	
	public static int[] sort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = i; j > 0; j--) {
				if (array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				} /*else {
					//此时插入的数据已经到达正确位置
					break;
				}*/
			}
		}
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = {3, 38, 5, 15, 48, 26, 27, 4, 36, 19, 50, 48, 46};
		array = InsertionSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
