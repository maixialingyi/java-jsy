package com.jsy.learn.algorithm;

/**
 * 冒泡排序
 * 遍历,相邻比较左大换位,最大的最终换到最右侧,后半段已有序不参与下次运算
 * 不变性:每次开始循环时,上次循环的结束下标(out)后的数据都是不变有序的
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 9, 5, 2};

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int term = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = term;
                }
            }
        }


        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }
}
