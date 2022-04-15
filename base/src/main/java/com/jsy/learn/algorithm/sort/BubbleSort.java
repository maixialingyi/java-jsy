package com.jsy.learn.algorithm.sort;

/**
 * 冒泡排序: 相邻比较,前大换位,后半段已有序不参与下次运算
 * arr[0～N-1]范围上,前大换位,最后arr[N-2]和arr[N-1]
 * arr[0～N-2]范围上,前大换位,最后arr[N-3]和arr[N-2]
 * arr[0～N-3]范围上,前大换位,最后arr[N-4]和arr[N-3]
 * ......
 * arr[0～1]范围上,前大换位,最后arr[0]和arr[1]
 * <p>
 * 不变性:每次开始循环时,上次循环的结束下标(out)后的数据都是不变有序的
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = SortCommon.randomArr(1, 10);

        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length - i-1; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortCommon.exchange(arr, j, j + 1);
                }
            }
            SortCommon.print("排序过程", arr);
        }
        SortCommon.print("排序结果", arr);
    }
}
