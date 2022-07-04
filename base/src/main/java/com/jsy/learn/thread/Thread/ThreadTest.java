package com.jsy.learn.thread.Thread;

public class ThreadTest {

    static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            //重写方法
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread");
            }
        };

        //thread.run();   //不会启动线程只是普通方法调用
        thread.start();   //启动线程，且调用run方法
        thread.join(1000);
        System.out.println("main");
    }
}
