package com.jsy.learn.algorithm;

/**
 * 二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {0,1,2,3,4,4,5,6};
        System.out.println(bs(arr, 7));
    }

    static int bs(int arr[],int data){
        int left = 0;
        int right = arr.length-1;
        while(left < right){
            int mid = left + (right - left)/2;
            if(data == arr[mid]){
                return mid;
            }
            if(data < arr[mid]){
                right = mid-1;
            }
            if(data > arr[mid]){
                left = mid+1;
            }
        }
        return -1;
    }
}
