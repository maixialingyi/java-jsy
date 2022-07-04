package com.jsy.learn.thread.Lock;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(2);

        new Thread(() -> {
            downLatch.countDown();
            System.out.println("thread-1");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            downLatch.countDown();
            System.out.println("thread-2");
        }).start();

        downLatch.await();
        System.out.println("thread-main");
    }
}
