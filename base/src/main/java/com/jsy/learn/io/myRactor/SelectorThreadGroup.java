package com.jsy.learn.io.myRactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 马士兵教育
 * @create: 2020-06-21 20:37
 */
public class SelectorThreadGroup {  //天生都是boss

    AtomicInteger xid = new AtomicInteger(0);

    /**
     * Selecter线程组,用于管理
     */
    SelectorThread[] selectorThreadArray;
    ServerSocketChannel serverSocketChannel = null;
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
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
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
                selectorThread.selector.wakeup();
            } else {
                //分配读写到workerGroup
                int index = xid.incrementAndGet() % workerGroup.selectorThreadArray.length;
                SelectorThread selectorThread =  workerGroup.selectorThreadArray[index];
                selectorThread.threadLocalQueue.add(channel);
                selectorThread.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
