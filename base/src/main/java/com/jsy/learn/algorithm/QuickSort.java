package com.jsy.learn.algorithm;

/**
 * 快速排序
 * 1.取最右下标值做为轴
 * 2.两端并行与轴比较,左边遇到大于轴,右边遇到小于轴,互换   最后处理轴换位
 * 3.两部分递归上逻辑
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {7,1,6,5,6,3};
        //int[] arr = {7,1,6,5,3,0};
        //int[] arr = {7,1,6,5,3,10};
        //左起始 右起始
        sort(arr,0,arr.length-1);
        print(arr);
    }

    //递归调用
    static void sort(int[] arr,int leftBound,int rightBound){
        if(leftBound >= rightBound) return;
        int mid = oneAxis(arr,leftBound,rightBound);
        sort(arr,leftBound,mid-1);
        sort(arr,mid+1,rightBound);
    }
    //单轴排序
    static int oneAxis(int[] arr,int leftBound,int rightBound){
        int axis = arr[rightBound];
        int left = leftBound;
        int right = rightBound-1;

        while(left <= right){
            while (left <=right && arr[left] <= axis){
                left ++;
            }
            while (left <=right && arr[right] > axis){
                right--;
            }
            if(left < right){
                swap(arr,left,right);
            }
        }
        swap(arr,left,rightBound);
        return left;
    }

    static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void print(int[] arr){
        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }
}
