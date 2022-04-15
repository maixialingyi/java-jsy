package com.jsy.learn.algorithm.sort;

import java.util.Random;

public class SortCommon {

    /**
     * @param length   数组长度
     * @param maxValue 最大值
     * @return
     */
    public static int[] randomArr(int length,int maxValue){
        int arr[] = new int[length];
        for(int i=0;i<length;i++){
            arr[i] = new Random().nextInt(maxValue);
        }
        print("生成数组",arr);
        return arr;
    }

    //两下标交换
    public static void exchange(int arr[],int a,int b){
        int term = arr[a];
        arr[a] = arr[b];
        arr[b] = term;
    }


    public static void print(String describe,int[] arr){
        System.out.println();
        System.out.print(describe + ": ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {
        SortCommon.randomArr(10,4);
    }
}
