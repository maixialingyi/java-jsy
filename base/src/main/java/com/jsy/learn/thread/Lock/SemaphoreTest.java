package com.jsy.learn.thread.Lock;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 类说明：演示Semaphore用法，一个数据库连接池的实现
 */
public class SemaphoreTest {
    private final static int POOL_SIZE = 10;
    //两个指示器，分别表示池子还有可用连接和已用连接
    private final Semaphore useful, useless;
    //存放数据库连接的容器
    private static LinkedList<Object> pool = new LinkedList<Object>();

    //初始化池
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(i);
        }
    }

    public SemaphoreTest() {
        this.useful = new Semaphore(10);
        this.useless = new Semaphore(0);
    }

    /*归还连接*/
    public void returnConnect(Object connection) throws InterruptedException {
        if (connection != null) {
            System.out.println("当前有" + useful.getQueueLength() + "个线程等待数据库连接!!"+ "可用连接数：" + useful.availablePermits());
            useless.acquire();
            synchronized (pool) {
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    /*从池子拿连接*/
    public Object takeConnect() throws InterruptedException {
        useful.acquire();
        Object connection;
        synchronized (pool) {
            connection = pool.removeFirst();
        }
        useless.release();
        return connection;
    }

    public static void main(String[] args) {
        SemaphoreTest dbPool = new SemaphoreTest();

        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Random r = new Random();//让每个线程持有连接的时间不一样
                    long start = System.currentTimeMillis();
                    try {
                        Object connect = dbPool.takeConnect();
                        System.out.println("Thread_" + Thread.currentThread().getId()+ "_获取数据库连接共耗时【" + (System.currentTimeMillis() - start) + "】ms.");
                        Thread.sleep(100 + r.nextInt(100));//模拟业务操作，线程持有连接查询数据
                        System.out.println("查询数据完成，归还连接！");
                        dbPool.returnConnect(connect);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
