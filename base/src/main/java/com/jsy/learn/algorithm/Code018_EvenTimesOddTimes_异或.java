package com.jsy.learn.algorithm;
/**
 * 异或运算：相同为0，不同为1   ->  忽略进位
 * 同或运算：相同为1，不同为0
 * N^N = 0
 * 0^N = 0
 * eor & (~eor + 1)   提取出最右的1
 * 多个数异或运算结果一样
 */
public class Code018_EvenTimesOddTimes_异或 {

	//arr中，只有一种数，出现奇数次
	//偶数个的数 异或后 = 0 最有剩下奇数个的数值
	public static void printOddTimesNum1(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

	// arr中，有两种数，出现奇数次
	public static void printOddTimesNum2(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		// eor = a ^ b
		// eor != 0
		// eor必然有一个位置上是1
		// 提取出最右的1，两奇数肯定在此位上 一个为0  一个为1
		// 通过次位可分开两数
		int rightOne = eor & (~eor + 1); // 提取出最右的1
		int onlyOne = 0; // eor'
		for (int i = 0 ; i < arr.length;i++) {
			if ((arr[i] & rightOne) != 0) {
				onlyOne ^= arr[i];
			}
		}
		System.out.println(onlyOne + " " + (eor ^ onlyOne));
	}

	public static void main(String[] args) {
		int a = 5;
		int b = 7;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
		printOddTimesNum1(arr1);

		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
		printOddTimesNum2(arr2);

	}

}
