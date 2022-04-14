package com.jsy.learn.algorithm;

/**
 * 插入排序
 * 前提: 局部有序
 * 向有序方向两两比较,找到第一个比自己小的下标,与中间下标两两交换
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {1, 2, 7, 9, 5, 3};

        int in, out;
        for (out = 1; out < arr.length; out++) {
            int temp = arr[out];
            in = out;
            while (in > 0 && arr[in - 1] >= temp) {
                arr[in] = arr[in - 1];
                --in;
            }
            arr[in] = temp;
        }

        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }

}
