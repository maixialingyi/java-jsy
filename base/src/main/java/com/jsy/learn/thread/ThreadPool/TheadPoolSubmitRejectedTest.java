package com.jsy.learn.thread.ThreadPool;


import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TheadPoolSubmitRejectedTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1,
                30, TimeUnit.SECONDS, new SynchronousQueue<>());

        try {
            Future future = executorService.submit(() -> {
                Thread.sleep(100);
                System.out.println("1");
                return "success";
            });

            //决绝后异常直接抛出，不执行
            Future future2 = executorService.submit(() -> {
                Thread.sleep(1);
                System.out.println("2");
                return "success";
            });

            String r = (String) future.get();
            String r1 = (String) future2.get();
        } catch (Exception e) {
            System.out.println("111");
            e.printStackTrace();
        }

        executorService.shutdown();
        Thread.currentThread().interrupt();
    }
}
