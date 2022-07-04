package com.jsy.learn.thread.Lock;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        /*ArrayBlockingQueue queue = new ArrayBlockingQueue(1);
        queue.add(new Object());
        queue.take();*/

        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(1);
        linkedBlockingDeque.add(1);   //添加失败 抛异常
        linkedBlockingDeque.offer(1);//添加失败返回值
        linkedBlockingDeque.offer(1,100, TimeUnit.MINUTES);
        linkedBlockingDeque.put(1);//一直阻塞

        linkedBlockingDeque.take();
    }
}
