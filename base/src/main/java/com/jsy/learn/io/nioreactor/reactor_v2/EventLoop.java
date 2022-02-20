package com.jsy.learn.io.nioreactor.reactor_v2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoop implements Executor {

    Selector selector;
    Thread thread = null;
    BlockingQueue events = new LinkedBlockingQueue();
    int NOT_STARTED = 1;
    int STARTED = 2;
    AtomicInteger STAT = new AtomicInteger(1);
    String name;


    public EventLoop(String name) {
        try {
            this.name = name;
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws InterruptedException, IOException {
        System.out.println("server已经开始：");
        for (; ; ) {
            int nums = selector.select();
            if (nums > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();  //会一直阻塞，不过可以通过外界有task到达来wakeup唤醒
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    Handler handler = (Handler) key.attachment();

                    handler.doRead();
                }
            }
            //run events
            runrTask();
        }
    }

    //线程池需要持有线程和消息队列
    @Override
    public void execute(Runnable task) {
        try {
            //放入当前线程队列
            events.put(task);
            //唤醒select()
            this.selector.wakeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //EventLoop 线程未启动,启动一个线程并执行
        if (!inEventLoop() && STAT.incrementAndGet() == STARTED) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        thread = Thread.currentThread();
                        EventLoop.this.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void runrTask() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Runnable task = (Runnable) events.poll(10, TimeUnit.MILLISECONDS);
            if (task != null) {
                events.remove(task);
                task.run();
            }
        }
    }

    private boolean inEventLoop() {
        return thread == Thread.currentThread();
    }
}
