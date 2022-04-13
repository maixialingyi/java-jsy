package com.jsy.learn.algorithm;

/**
 * 选择排序
 * 遍历一遍找出最小,放最右侧,右侧已有序下标不参与下次
 * 从下标0开始向后比较,找出最小值,与下标0互换位置
 * 从下标1开始想后比较,找出最小值,与下标1互换位置
 * ..............
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 9, 5, 2};

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            int term = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = term;
        }

        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }
}
