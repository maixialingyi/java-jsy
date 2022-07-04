package com.jsy.learn.thread.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) throws Exception {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            System.out.println("callable");
            return "success";
        };

        FutureTask future = new FutureTask(callable);
        Thread thread = new Thread(future);
        thread.start();
        future.get();
        System.out.println("mian");
    }
}
