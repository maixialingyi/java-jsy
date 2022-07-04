package com.jsy.learn.thread;

import java.util.concurrent.Semaphore;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/14 16:42
 */
public class SemaphoreTest {

    public static void main(String args[]) throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        System.out.println("是否公平模式FIFO"+semaphore.isFair());
        System.out.println("获取当前可用的许可证数量"+semaphore.availablePermits());
        //当前线程尝试去阻塞的获取permits个许可证(被中断抛异常)
        semaphore.acquire();
        //当前线程尝试去阻塞的获取permits个许可证(被中断抛异常)
        semaphore.acquire(2);
        //当前线程尝试去阻塞的获取1个许可证(不处理中断状态)
        semaphore.acquireUninterruptibly();
        //当前线程尝试去阻塞的获取permits个许可证(不处理中断状态)
        semaphore.acquireUninterruptibly(2);
        //前线程尝试去获取1个许可证 只尝试一次 失败不进队列
        semaphore.tryAcquire();
        //当前线程尝试去获取permits个许可证  只尝试一次 师表不进队列
        semaphore.tryAcquire(2);
        //
       // semaphore.tryAcquire(100,new TimeUnit());



    }
}
