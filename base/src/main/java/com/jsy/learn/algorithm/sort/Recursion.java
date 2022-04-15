package com.jsy.learn.algorithm.sort;

/**
 * 递归
 */
public class Recursion {

    public static void main(String[] args) {
        System.out.println(f(2));
    }

    // 叠加
    static long f(int n) {
        if (n < 1) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }
        return n + f(n - 1);
    }
}
