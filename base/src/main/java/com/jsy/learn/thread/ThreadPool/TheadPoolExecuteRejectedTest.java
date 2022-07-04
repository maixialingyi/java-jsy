package com.jsy.learn.thread.ThreadPool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TheadPoolExecuteRejectedTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 2,
                30, TimeUnit.SECONDS, new SynchronousQueue<>());

        try {
            executorService.execute(() -> {
                int w = 1/0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            });

            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2");
            });
        } catch (Exception e) {
            System.out.println("111");
            e.printStackTrace();
        }

        executorService.shutdown();
        Thread.currentThread().interrupt();
    }
}
