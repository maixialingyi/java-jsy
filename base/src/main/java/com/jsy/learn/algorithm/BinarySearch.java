package com.jsy.learn.algorithm;

/**
 * 二分查找   1有序数组中 查找元素
 *          2有序数组中 查找等于X最左侧元素
 *          3局部最小  1）0下标元素 < 1下标元素          0下标局部最小
 *                    2）最大下标元素 > 最大下标-1元素   最大下标局部最小
 *                    3）中间元素小于左右元素            局部最小
 *                    条件：无序，相邻不相等   只返回一个局部最小就可以
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {0,2,2,3,4,4,5,4,6};
        //有序数组中 查找元素
        System.out.println(bs(arr, 4));
        //有序数组中 查找大于等于X最左侧元素
        System.out.println(bsLeft(arr, 2));
        //局部最小
        int[] arr1 = {3,2,1,4,5,6,7,8,9};
        System.out.println(bsAwesome(arr1));
    }

    // 有序数组中查找元素
    static int bs(int arr[],int data){
        int left = 0;
        int right = arr.length-1;
        int mid = -1;
        while(left < right){
            mid = left + (right - left)/2;
            if(data == arr[mid]){
                return mid;
            }else if(data < arr[mid]){
                right = mid-1;
            }else if(data > arr[mid]){
                left = mid+1;
            }
        }
        return -1;
    }

    static int bsLeft(int arr[],int data){
        int L = 0;
        int R = arr.length - 1;
        int index = -1; // 记录最左的对号
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= data) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    //局部最小
    static int bsAwesome(int arr[]){
        //0下标和最大下标是否为局部最小
        if(arr[0] < arr[1]){
            return 0;
        }else if(arr[arr.length-1] < arr[arr.length-2]){
            return arr.length-1;
        }
        //二分找
        int left = 0;
        int right = arr.length-1;
        int mid = -1;
        while(left < right){
            mid = (left + right)/2;
            //mid 小于左右  即局部最小
            if(arr[mid] < arr[mid-1] &&  arr[mid] < arr[mid+1]){
                return mid;
                //mid 大于左边 或 大于右边  或  大于左右 则任意取
            }else if(arr[mid] > arr[mid-1]){
                right = mid;
            }else if(arr[mid] > arr[mid+1]){
                left = mid;
            }

            //等同上
            /*if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }*/
        }
        return mid;
    }
}
