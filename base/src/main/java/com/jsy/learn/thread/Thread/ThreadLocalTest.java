package com.jsy.learn.thread.Thread;

public class ThreadLocalTest {
    private static ThreadLocal threadLocal = new ThreadLocal();
    private static ThreadLocal inthreadLocal = new InheritableThreadLocal();

    static class MyThread extends Thread{
        @Override
        public void run() {
            ThreadLocalTest.threadLocal.set("MyThread");
            ThreadLocalTest.inthreadLocal.set("MyThread");
            threadLocal.remove();
            System.out.println(threadLocal.get());
            System.out.println(inthreadLocal.get());

            new MySubThread().start();
        }
    }

    static class MySubThread extends Thread{
        @Override
        public void run() {
            ThreadLocalTest.threadLocal.set("MySubThread");
            System.out.println(threadLocal.get());
            System.out.println(inthreadLocal.get());
        }
    }

    public static void main(String[] args) {
        ThreadLocalTest.threadLocal.set("parent");
        //inthreadLocal.set("parent");

        new MyThread().start();
        Object[] objects = new Object[3];

    }
}
