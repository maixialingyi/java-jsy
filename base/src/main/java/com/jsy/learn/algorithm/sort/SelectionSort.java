package com.jsy.learn.algorithm.sort;

/**
 * 选择排序: 相邻比较,变量标记最小下标,遍历完成最小下标与起始下标互换位置
 * arr[0～N-1]范围上，找到最小值下标，与起始下标0互换位置
 * arr[1～N-1]范围上，找到最小值下标，与起始下标1互换位置
 * arr[2～N-1]范围上，找到最小值下标，与起始下标2互换位置
 * …
 * arr[N-1～N-1]范围上，找到最小值位置，与起始下标N-1互换位置
 * <p>
 * 第一次遍历: 寻址N次   + 比较N-1次 + 替换1次
 * 第二次遍历: 寻址N-1次 + 比较N-2次 + 替换1次
 * 等差1数列求和公式:(an2 + a1n)/2 + N次替换 -> O(N2)
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = SortCommon.randomArr(10, 10);

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            SortCommon.exchange(arr, i, minIndex);
            SortCommon.print("排序过程", arr);
        }
        SortCommon.print("排序结果", arr);
    }
}
