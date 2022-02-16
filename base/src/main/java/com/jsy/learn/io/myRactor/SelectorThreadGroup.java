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

            //nextSelector(serverSocketChannel);  //轮训选择selector,每一个selector上都可以OP_ACCEPT OP_READ
            //nextSelectorV2(serverSocketChannel);//一个固定selector负责注册OP_ACCEPT,其他selector负责注册OP_READ
            nextSelectorV3(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 轮训选择selector,每一个selector上都可以OP_ACCEPT OP_READ
     */
    public void nextSelector(Channel c) {
        //轮训获取SelectorThread线程组中线程
        int index = xid.incrementAndGet() % selectorThreadArray.length;
        SelectorThread st = selectorThreadArray[index];
        //1.把要注册事件的Channel放入SelectorThread的ThreadLocal中队列
        st.threadLocalQueue.add(c);
        //2.通过打断selector.select()阻塞，selectorThread从ThreadLocal中的队列获取Channel,执行注册逻辑
        st.selector.wakeup();

        //todo 为什么不再此处注册 参见SelectorThread中注释
    }

    /**
     * ServerSocketChannel OP_ACCEPT 放到固定的0中
     * SocketChannel OP_READ 放入其他selector
     */
    public void nextSelectorV2(Channel c) {
        try {
            if (c instanceof ServerSocketChannel) {
                selectorThreadArray[0].threadLocalQueue.put(c);
                selectorThreadArray[0].selector.wakeup();
            } else {
                int index = xid.incrementAndGet() % (selectorThreadArray.length - 1);
                SelectorThread st = selectorThreadArray[index + 1];
                st.threadLocalQueue.add(c);
                st.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void nextSelectorV3(Channel channel) {
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
