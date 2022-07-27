package com.jsy.learn.redpacket;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 公平算法
 * 配置用户固定概率
 *
 */
public class Generate {

    /**
     * 生成min到max范围的浮点数
     **/
    public static double nextDouble(final double min, final double max) {
        return min + ((max - min) * new Random().nextDouble());
    }

    public static String format(double value) {
        return new java.text.DecimalFormat("0.00").format(value); // 保留两位小数
    }

    //二倍均值法
    public static void main(String[] args) {
        int min = 5;

        int acount = 100;
        int num = 10;
        while(num > 1){
            if(num == 1){
                System.out.println(acount);
            }
            int redAcount = new Random().nextInt((acount/num - min)*2) + min;
            System.out.println(redAcount);
            num --;
            acount -= redAcount;
        }
        System.out.println(acount);
    }
}
