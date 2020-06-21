package com.jokey.bingo.algo.sort.insertion;

/**
 * @author JokeyFeng
 * @date: 2020/5/18
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.insertion
 * @comment:
 */
public class BinaryInsertSort {
	
	public static int[] sort(int[] array) {
		
		if (array.length < 2) {
			return array;
		}
		
		for (int i = 1; i < array.length; i++) {
			//left、right分别是有序区的左右边界
			int left = 0, right = i - 1, current = array[i];
			while (left <= right) {
				//搜索有序区中第一个大于current的位置，即为current要插入的位置
				int mid = left + ((right - left) >> 1);
				if (array[mid] > current) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			for (int j = i - 1; j >= left; j--) {
				array[j + 1] = array[j];
			}
			//left为打一个大于current的位置，插入current
			array[left] = current;
		}
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = {3, 38, 5, 15, 48, 26, 27, 4, 36, 19, 50, 48, 46};
		array = BinaryInsertSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
