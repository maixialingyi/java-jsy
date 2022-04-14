package com.jsy.learn.algorithm;

import java.util.Arrays;

/**
 * 归并排序
 * 前提: 子数组有序,子数组间无序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {1,3,5,7,2,4,6};
        int[] temp = new int[arr.length];
        //有序子数组分割点
        int mid = 3;

        int i = 0;
        int j = mid+1;
        int k = 0;

        while(i<=mid && j< arr.length){
            if(arr[i]<=arr[j]){
                temp[k] = arr[i];
                i++;
                k++;
            } else {
                temp[k] = arr[j];
                j++;
                k++;
            }
        }
        while(i<=mid){
            temp[k++] = arr[i++];
        }
        while(j<arr.length){
            temp[k++] = arr[j++];
        }

        print(temp);

        //todo jdk实现对象合并排序  Arrays.sort(T[] a, Comparator<? super T> c)
    }

    static void print(int[] arr){
        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }
}
