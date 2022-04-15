package com.jsy.learn.algorithm.sort;

/**
 * 插入排序: 标记下标向前比较,小于前互换,大于前或到下标0结束
 * arr[0～1]范围上,从arr[1]开始往前比较,小于前互换,大于前或到下标0结束
 * arr[0～2]范围上,从arr[2]开始往前比较
 * …………
 * arr[0～N]范围上,从arr[N]开始往前比较
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = SortCommon.randomArr(10, 10);

        for(int i = 1;i<arr.length;i++){
            int lIndex = i;
            while(lIndex>0 && arr[lIndex]<arr[lIndex-1]){
                SortCommon.exchange(arr, lIndex, lIndex-1);
                lIndex--;
            }
            SortCommon.print("排序过程", arr);
        }
        SortCommon.print("排序结果", arr);
    }

}
