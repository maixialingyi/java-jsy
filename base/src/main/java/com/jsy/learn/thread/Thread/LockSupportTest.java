package com.jsy.learn.thread.Thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            //重写方法
            @Override
            public void run() {
                System.out.println("进入线程");
                LockSupport.park(this);
                System.out.println("线程结束");
            }
        };
        thread.start();
        Thread.sleep(2000);
        //thread.interrupt();
        //LockSupport.unpark(thread);
    }
}
