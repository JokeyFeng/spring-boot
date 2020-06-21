package com.jokey.bingo.algo.sort.merge;

import java.util.Arrays;

/**
 * @author JokeyFeng
 * @date: 2020/5/19
 * @project: spring-boot
 * @package: com.jokey.bingo.algo.sort.merge
 * @comment:
 */
public class MergeSort {
	
	public static int[] sort(int[] array) {
		if (array.length < 2) {
			return array;
		}
		int mid = array.length >> 1;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, array.length);
		
		return merge(sort(left), sort(right));
	}
	
	/**
	 * 归并排序-将两段有序数组结合成一个有序数组
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	private static int[] merge(int[] left, int[] right) {
		int[] result = new int[left.length + right.length];
		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				result[k++] = left[i++];
			} else {
				result[k++] = right[j++];
			}
		}
		
		while (i < left.length) {
			result[k++] = left[i++];
		}
		while (j < right.length) {
			result[k++] = right[j++];
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		int[] array = {3, 38, 5, 15, 48, 26, 27, 4, 36, 19, 50, 48, 46};
		array = MergeSort.sort(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
}
