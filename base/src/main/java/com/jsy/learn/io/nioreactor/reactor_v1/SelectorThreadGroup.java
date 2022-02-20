package com.jsy.learn.io.nioreactor.reactor_v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {  //天生都是boss

    AtomicInteger xid = new AtomicInteger(0);

    /**
     * Selecter线程组,用于管理
     */
    SelectorThread[] selectorThreadArray;
    /** 无workerGroup时自己就是workerGroup */
    SelectorThreadGroup workerGroup = this;

    SelectorThreadGroup(int num) {
        //创建线程数组
        selectorThreadArray = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            //创建处理selecter线程
            selectorThreadArray[i] = new SelectorThread(this);
            //启动selecter线程
            new Thread(selectorThreadArray[i]).start();
        }
    }

    public void setWorker(SelectorThreadGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public void bind(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //设置非阻塞
            serverSocketChannel.bind(new InetSocketAddress(port));

            //选择selector进行绑定
            nextSelector(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void nextSelector(Channel channel) {
        try {
            if (channel instanceof ServerSocketChannel) {
                //分配OP_ACCEPT到bossGroup
                int index = xid.incrementAndGet() % selectorThreadArray.length;
                SelectorThread selectorThread = selectorThreadArray[index];
                selectorThread.threadLocalQueue.put(channel);
                selectorThread.setWorker(workerGroup);
                //唤醒workerGroup select 阻塞,处理队列中注册事件
                selectorThread.selector.wakeup();
            } else {
                //分配OP_READ OP_WRITE到workerGroup
                int index = xid.incrementAndGet() % workerGroup.selectorThreadArray.length;
                SelectorThread selectorThread =  workerGroup.selectorThreadArray[index];
                selectorThread.threadLocalQueue.add(channel);
                //唤醒workerGroup select 阻塞,处理队列中注册事件
                selectorThread.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
