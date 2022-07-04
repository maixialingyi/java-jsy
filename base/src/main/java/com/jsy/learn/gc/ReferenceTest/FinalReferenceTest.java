package com.jsy.learn.gc.ReferenceTest;


/**
 *  -XX:+PrintGC                           输出GC日志
 *  -XX:+PrintGCDetails                    输出GC的详细日志
 *  -XX:+PrintGCDateStamps                 输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）
 *  -XX:+PrintReferenceGC
 */
public class FinalReferenceTest {

    public static void main(String[] args){
        for( ;; ) {
            for (int i = 0; i < 1000; i++) {
                Person p = new Person();
                System.out.println("");
            }
            System.gc();//增加垃圾回收器启动的概率
        }
    }
}

class Person {
    //对象被回收时调用finalize()
    protected void finalize() throws Throwable{
        System.out.println("调用finalize()！！！");
    }
}

