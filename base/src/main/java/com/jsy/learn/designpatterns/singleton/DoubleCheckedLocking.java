package com.jsy.learn.designpatterns.singleton;

/**
 * 双层检查锁
 */
public class DoubleCheckedLocking {
    private static volatile DoubleCheckedLocking INSTANCE;

    private DoubleCheckedLocking() {}

    public static DoubleCheckedLocking getInstance() {
        if (INSTANCE == null) {
            //双重检查
            synchronized (DoubleCheckedLocking.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DoubleCheckedLocking();
                }
            }
        }
        return INSTANCE;
    }


    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(DoubleCheckedLocking.getInstance().hashCode());
            }).start();
        }
    }
}
