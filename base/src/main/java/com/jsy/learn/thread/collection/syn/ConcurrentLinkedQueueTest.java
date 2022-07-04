package com.jsy.learn.thread.collection.syn;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/18 10:40
 */
public class ConcurrentLinkedQueueTest {

    static Person p;

    public static void main(String args[]) throws InterruptedException {
        /*int a = 0;
        boolean bool = (a != (a = 1));
        System.out.println(bool);

        String str = "1";
        String str2 = "2";
        boolean bool2 = (str != (str = "2"));
        System.out.println(bool2);*/

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"---------"+ConcurrentLinkedQueueTest.p);
            }
        },"one");
        threadOne.start();

        Thread.sleep(1000);

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
             ConcurrentLinkedQueueTest.p = new Person();
                System.out.println(Thread.currentThread().getName()+"---------"+ConcurrentLinkedQueueTest.p);
            }
        },"Two");
        threadTwo.start();

        threadTwo.join();
        LockSupport.unpark(threadOne);
        threadOne.join();

        /*ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.add(new Object());*/
    }

    static class Person{}

}
