package com.jsy.learn.algorithm;

/**
 * 求数组arr[L..R]中的最大值，怎么用递归方法实现。
 *
 * 1）将[L..R]范围分成左右两半。左：[L..Mid]  右[Mid+1..R]
 * 2）左部分求最大值，右部分求最大值
 * 3） [L..R]范围上的最大值，是max{左部分最大值，右部分最大值}
 * 注意：2）是个递归过程，当范围上只有一个数，就可以不用再递归了
 *
 * 分解的最小粒度，作为递归停止条件
 */

/**
 * T(N) = a * T(N/b) + O(N^d)(其中的a、b、d都是常数
 * a方法中自调用几次   b子中处理样本数占比  d其他额外逻辑)
 * 的递归函数，可以直接通过Master公式来确定时间复杂度
 * 如果 log(b,a) < d，复杂度为O(N^d)
 * 如果 log(b,a) > d，复杂度为O(N^log(b,a))
 * 如果 log(b,a) == d，复杂度为O(N^d  * logN)
 */
public class Code014_GetMax_递归 {

	// 求arr中的最大值
	public static int getMax(int[] arr) {
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上求最大值
	public static int process(int[] arr, int L, int R) {
		if (L == R) { // arr[L..R]范围上只有一个数，直接返回，base case
			return arr[L];
		}
		//  L..mid  mid+1...R
		// int mid = (L+R)/2
		int mid = L + ((R - L) >> 1); // 中点
		int leftMax = process(arr, L, mid);
		int rightMax = process(arr, mid + 1, R);
		return Math.max(leftMax, rightMax);
	}

}
