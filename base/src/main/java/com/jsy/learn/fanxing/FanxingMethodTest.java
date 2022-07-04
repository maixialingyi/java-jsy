package com.jsy.learn.fanxing;

public class FanxingMethodTest {

    public <T> void show(T t){
        System.out.println(t.toString());
    }

    public <T> void printMsg( T... args){
        for(T t : args){
            System.out.println("泛型测试t is " + t);
        }
    }


    public static <F> void showStatic(F f){
        System.out.println(f);
    }
    
    public static void main(String[] args) {
        FanxingMethodTest test = new FanxingMethodTest();
        test.show("1");
        test.printMsg(new Integer(1),new InternalError());
        FanxingMethodTest.showStatic("1");
    }
}
