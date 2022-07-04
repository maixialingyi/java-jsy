package com.jsy.learn.thread.blockingqueue;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/10 19:37
 */
public class ArrayBlockingQueueTest {

    public static void main(String args[]){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(100);
        arrayBlockingQueue.offer(new Object());
        arrayBlockingQueue.element();
        arrayBlockingQueue.remove(new Object());




    }
}
