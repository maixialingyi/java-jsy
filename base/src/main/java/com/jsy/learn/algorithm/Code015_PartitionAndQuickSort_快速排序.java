package com.jsy.learn.algorithm;

/**
 * 快速排序
 */
public class Code015_PartitionAndQuickSort_快速排序 {

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	/**
	 * 小于等于最右放左边，大于最后放右边，最后放置最右 -->  初最右为数组排序后位置
	 * [3,2,8, 4]
	 */
	public static int partition(int[] arr, int L, int R) {
		if (L > R) {
			return -1;
		}
		if (L == R) {
			return L;
		}
		int lessEqual = L - 1;  //定义小于等于区间临界下标
		int index = L;          //移动指针
		while (index < R) {
			if (arr[index] <= arr[R]) { //小于等于最右
				swap(arr, index, ++lessEqual);//小于等于临界下标+1 于 index交换
			}
			index++;
		}
		swap(arr, ++lessEqual, R);//小于等于临界下标+1 于R交换
		return lessEqual;         //返回临界下标
	}

	/**
	 * 小于最右放左边，等于最右放中间，大于最右放右边，最后放置最右 -->  于最右相等的元素，排序时不用动
	 * 小于临界下标向右，大于临界下标向左
	 */
	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) {
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1; //小于临界下标
		int more = R;     //大于临界下标
		int index = L;    //移动下标
		while (index < more) {
			if (arr[index] == arr[R]) {  //等于最右值，不动
				index++;
			} else if (arr[index] < arr[R]) { //小于最右，移动下标于小于临界下标+1交换，移动下标+1
				swap(arr, index++, ++less);
			} else {                        //大于最右，移动下标于大于临界下标-1交换
				swap(arr, index, --more);   //移动下标元素交换后，新元素未处理过，所以不动
			}                               //大于临界下标-1
		}
		swap(arr, more, R);        //最右值于大于临界下标交换
		return new int[] { less + 1, more };
	}

	/**
	 * 递归 一次处理一个元素
	 * 在arr[L..R]范围上，进行快速排序的过程：
	 * 1）用arr[R]对该范围做partition，<= arr[R]的数在左部分并且保证arr[R]最后来到左部分的最后一个位置，记为M； >arr[R]的数在右部分（arr[M+1..R]）
	 * 2）对arr[L..M-1]进行快速排序(递归)
	 * 3）对arr[M+1..R]进行快速排序(递归)
	 * 因为每一次partition都会搞定一个数的位置且不会再变动，所以排序能完成
	 */
	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process1(arr, 0, arr.length - 1);
	}

	public static void process1(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		int M = partition(arr, L, R);
		process1(arr, L, M - 1);
		process1(arr, M + 1, R);
	}

	/**
	 * 递归一次处理于最右元素相同的多个元素
	 * 在arr[L..R]范围上，进行快速排序的过程：
	 * 1）用arr[R]对该范围做partition，< arr[R]的数在左部分，== arr[R]的数中间，>arr[R]的数在右部分。假设== arr[R]的数所在范围是[a,b]
	 * 2）对arr[L..a-1]进行快速排序(递归)
	 * 3）对arr[b+1..R]进行快速排序(递归)
	 * 因为每一次partition都会搞定一批数的位置且不会再变动，所以排序能完成
	 */
	public static void quickSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process2(arr, 0, arr.length - 1);
	}

	public static void process2(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		int[] equalArea = netherlandsFlag(arr, L, R);
		process1(arr, L, equalArea[0] - 1);
		process1(arr, equalArea[1] + 1, R);
	}
	/**
	 * 随机找一个元素为最右元素 O(N * LogN)
	 * 1）通过分析知道，划分值越靠近中间，性能越好；越靠近两边，性能越差
	 * 2）随机选一个数进行划分的目的就是让好情况和差情况都变成概率事件
	 * 3）把每一种情况都列出来，会有每种情况下的时间复杂度，但概率都是1/N
	 * 4）那么所有情况都考虑，时间复杂度就是这种概率模型下的长期期望！
	 *
	 * 时间复杂度O(N*logN)，额外空间复杂度O(logN)都是这么来的。
	 */
	public static void quickSort3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process3(arr, 0, arr.length - 1);
	}

	public static void process3(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
		int[] equalArea = netherlandsFlag(arr, L, R);
		process1(arr, L, equalArea[0] - 1);
		process1(arr, equalArea[1] + 1, R);
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
			int[] arr3 = copyArray(arr1);
			quickSort1(arr1);
			quickSort2(arr2);
			quickSort3(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");

	}

}
