package com.jsy.learn.algorithm;

/**
 * 递归 + 归并 实现排序
 * 非递归方法实现
 *
 * 归并排序： 内部有序，外部无序  组内不比较，组间比较，减少比较替换次数
 */
public class Code029_MergeSort_递归_归并排序 {

	/** 递归 + 归并 方法实现*/
	public static void mergeSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	/**
	 * [5,4,3,2,1]
	 *                               f(L=0 ,R=4)
	 *                       /                            \
	 *                f(L=0 ,R=2)                      f(L=3 ,R=4)
	 *                /         \                     /           \
	 *         f(L=0 ,R=1)   f(L=2 ,R=2)         f(L=3 ,R=3)   f(L=4 ,R=4)
	 *         /       \
	 * f(L=0 ,R=0)   f(L=1 ,R=1)
	 */
	public static void process(int[] arr, int L, int R) {
		if (L == R) {     //递归结束条件
			return;
		}
		// 样本数分两份
		int mid = L + ((R - L) >> 1);
		process(arr, L, mid);
		process(arr, mid + 1, R);
		//归并排序
		merge(arr, L, mid, R);
	}

	/**
	 * [5,4,3,2,1]
	 * 如f(0,1)        help: [4,5]    -> [4,5,3,2,1]
	 *   f(0,2)        help: [3,4,5]  -> [3,5,4,2,1]
	 *   f(3,4)        help: [1,2]    -> [3,5,4,1,2]
	 *   f(0,4)        help: [1, , , , ]   右组取1
	 *                 help: [1,2, , , ]   右组取2   右组取完了
	 *                 左组剩余按顺序放help -> 顺序覆盖原数组
	 */
	public static void merge(int[] arr, int L, int M, int R) {
		// 存放归并比较后 有序
		int[] help = new int[R - L + 1];
		int i = 0;  //help数组起始下标
		int p1 = L; //左段数组起始位置
		int p2 = M + 1;//右段数组起始位置
		while (p1 <= M && p2 <= R) { //当左右段其中一个遍历完成，归并结束
			//左段，右段都从第一个下标开始比较， 取小的放入help数组，依次。。。。
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		//左段未遍历完
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		//右段未遍历完
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		//help覆盖原数组位置
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
	}

	/** 非递归方法实现*/
	public static void mergeSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		/**
		 * [5,4,3,2,1]
		 * 当前有序左组长度1 ： 5比4  3比2  不够分组1不比 ->  [4,5, 2,3, 1]
		 * 当前有序左组长度2 ： 4,5比2,3   不够分组1不比  ->  [2,3,4,5, 1]
		 * 当前有序左组长度4 ： 4,5，2,3比1      ->  [1,2,3,4,5]    8 > lentgh 跳出
		 */
		int N = arr.length;
		int mergeSize = 1;//当前有序的 左组长度 从一个开始
		while (mergeSize < N) {
			int L = 0;
			while (L < N) {
				int M = L + mergeSize - 1;
				if (M >= N) { //剩余元素不够分组，不处理
					break;
				}
				int R = Math.min(M + mergeSize, N - 1);//右组边界
				merge(arr, L, M, R);//归并排序，覆盖原有
				L = R + 1;   //左组左边界右移位右组右边界
			}
			//防止超过int值，溢出
			if (mergeSize > N / 2) {
				break;
			}
			mergeSize <<= 1;//左组*2
		}
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			mergeSort1(arr1);
			mergeSort2(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");
	}

}
