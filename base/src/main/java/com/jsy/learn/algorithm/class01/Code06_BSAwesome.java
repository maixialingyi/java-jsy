package com.jsy.learn.algorithm.class01;

/**
 * 局部最小  1）0下标元素 < 1下标元素          0下标局部最小
 *  *      2）最大下标元素 > 最大下标-1元素   最大下标局部最小
 *  *      3）中间元素小于左右元素            局部最小
 *  *      条件：无序，相邻不相等   只返回一个局部最小就可以
 */
public class Code06_BSAwesome {

	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

}
