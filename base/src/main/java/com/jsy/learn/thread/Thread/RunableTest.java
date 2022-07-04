package com.jsy.learn.thread.Thread;

public class RunableTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("main");
    }
}
