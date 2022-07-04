package com.jsy.learn.fanxing;

public class Singleton{
    private static Singleton singleton = new Singleton();
    public static int value1;
    public static int value2 = 0;

    private Singleton(){
        value1++;
        value2++;
    }

    public static Singleton getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        Singleton singleTon = Singleton.getInstance();
        System.out.println("value1=" + singleTon.value1);
        System.out.println("value2=" + singleTon.value2);
        Thread thread = new Thread();

    }
}
