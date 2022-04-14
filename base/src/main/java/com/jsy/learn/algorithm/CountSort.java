package com.jsy.learn.algorithm;

/**
 * 计数排序
 * 数据量大,值范围小   如公司一万名员工,按年龄排序
 *                    高考人数,按分数排序
 *
 */
public class CountSort {

    public static void main(String[] args) {
        //10以内数
        int[] arr = {7,1,7,6,6,1,4,3,5,8,8,4,3,2,1,1,6,5,7,1,7,6,6,1,4,3,5,8,8,4,3,2,1,1,6,5};
        //计数数组
        int countArr[] = new int[10];
        int resultArr[] = new int[arr.length];

        for(int i=0;i<arr.length;i++){
            countArr[arr[i]]++;
        }

        int j = 0;
        for(int i=0;i<countArr.length;i++){
            while(countArr[i]-- > 0){
                resultArr[j] = i;
                j++;
            }
        }
        print(resultArr);
    }
    static void print(int[] arr){
        // 打印------------------------
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }
}
