package com.jsy.learn.thread.Interrupte;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("before");
                LockSupport.park();
                System.out.println("after");
            }
        });
        thread.start();
        Thread.sleep(4000);
        LockSupport.unpark(thread);
        System.out.println("-------mian---------");
    }
}
