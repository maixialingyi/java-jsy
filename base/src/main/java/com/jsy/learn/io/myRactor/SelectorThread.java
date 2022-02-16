package com.jsy.learn.io.myRactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 每线程对应一个selector，一个私有队列(用来传输外部线程事件注册)
 * 多线程情况下，该主机，该程序的并发客户端被分配到多个selector上
 * 每个客户端，只绑定到其中一个selector, 不会有交互问题
 */
public class SelectorThread extends ThreadLocal<LinkedBlockingQueue<Channel>> implements Runnable{
    Selector  selector = null;

    /**ThreadLocal中获取 队列*/
    LinkedBlockingQueue<Channel> threadLocalQueue = get();

    SelectorThreadGroup selectorThreadGroup;

    @Override
    protected LinkedBlockingQueue<Channel> initialValue() {
        return new LinkedBlockingQueue<>();
    }

    SelectorThread(SelectorThreadGroup selectorThreadGroup){
        try {
            this.selectorThreadGroup = selectorThreadGroup;
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true){
            try {
                /**
                 * 1.select()阻塞获取有效事件
                 * 为什么外部线程注册事件要通过 -队列- 再由SelectorThread执行注册逻辑?
                 * select()为阻塞的,外部线程需调用wakeup()唤醒 1.selector.wakeup() 2.register()
                 * 当外部线程调用wakeup(),还未执行register(),cpu时间片刚好用完了被挂起,切回前,切换过SelectorThread
                 * 调用select()后又阻塞,切换到外部注册线程则就注册不上了
                 *
                 * 解决方式: 外部线程注册事件,把channel放入SelectorThread的ThreadLocal中的阻塞队列中
                 *              注册逻辑放在SelectorThread中(下面代码)
                 *          外部线程调用selector.wakeup()
                 */
                int nums = selector.select();
                /**2 处理有效selectkeys */
                if(nums>0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while(iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        //复杂,接受客户端的过程（接收之后，要注册，多线程下，新的客户端，注册到那里呢？）
                        if(key.isAcceptable()){
                            acceptHandler(key);
                        }else if(key.isReadable()){
                            readHander(key);
                        }else if(key.isWritable()){

                        }
                    }
                }
                /** 3. 事件队列中获取要注册事件的Channel,进行事件注册 */
                if(!threadLocalQueue.isEmpty()){
                    Channel c = threadLocalQueue.take();
                    if(c instanceof ServerSocketChannel){
                        ServerSocketChannel server = (ServerSocketChannel) c;
                        server.register(selector,SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName()+" register listen");
                    }else if(c instanceof  SocketChannel){
                        SocketChannel client = (SocketChannel) c;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                        client.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println(Thread.currentThread().getName()+" register client: " + client.getRemoteAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readHander(SelectionKey key) {
        System.out.println(Thread.currentThread().getName()+" read......");

        ByteBuffer buffer = (ByteBuffer)key.attachment();
        SocketChannel client = (SocketChannel)key.channel();
        buffer.clear();
        while(true){
            try {
                int num = client.read(buffer);
                if(num > 0){
                    buffer.flip();  //将读到的内容翻转，然后直接写出
                    while(buffer.hasRemaining()){
                        client.write(buffer);
                    }
                    buffer.clear();
                }else if(num == 0){
                    break;
                }else if(num < 0 ){
                    //客户端断开了
                    System.out.println("client: " + client.getRemoteAddress()+"closed......");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName()+"   acceptHandler......");

        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //nextSelector(serverSocketChannel);  //轮训选择selector,每一个selector上都可以OP_ACCEPT OP_READ
            //nextSelectorV2(serverSocketChannel);//一个固定selector负责注册OP_ACCEPT,其他selector负责注册OP_READ
            selectorThreadGroup.nextSelectorV3(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorker(SelectorThreadGroup workerGroup) {
        this.selectorThreadGroup =  workerGroup;
    }
}
