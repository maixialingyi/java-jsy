package com.jsy.learn.thread.Lock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author jiangshaoyue
 * @Date 2019/5/27 15:18
 */
public class ReentrantLockTest {

    public static void main(String args[]) {
        Lock lock = new ReentrantLock(true);
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                lock.lock();
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName());
                lock.unlock();
            }
        }, "threadName-1");

        thread.start();
        thread.interrupt();
    }
}
