package com.jsy.learn.io.nioreactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 客户端可使用 telnet 127.0.0.1 9090链接
 * 2. ctrl+] 切换模式
 * 3. send 多个字符
 * 可读  :   内核数据准备完成 -> 被业务线程读取完成 都为可读. 内核数据被用户读完才为不可读
 * 单selecter,单线程线性处理读      : 内核数据读取完,才会进行下次select(),不会出现重复读
 * 单selecter,异步读处理读重复读问题 : 数据未读取完就进行下次select(),就会造成重复读问题重复读问题
 *      解决方式一:
 *              每次读/写事件,都先系统调用取消对读写事件监听
 *      解决方式二:
 *  *           多selecter线程并行,单selecter线程处理读写
 *  *           原理: 用cup核数 或 *2个线程,每个线程中创建一个selecter,把fds打散均匀注册到不同selecter上
 *  *            -> 多线程 且 避免了读事件重复(不用调用key.cancel()
 */

public class SingleThreadSyncReadWrite {

    private static final AtomicInteger atomicInteger = new AtomicInteger();
    private static final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

    public static void main(String[] args) throws Exception {
        SingleThreadSyncReadWrite.serverStart();
    }

    public static void serverStart() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);                //设置非阻塞
        serverSocketChannel.bind(new InetSocketAddress(9090));  //绑定端口
        // linux 多路复用器  select  poll  epoll  优先选择：epoll  但是可以 -D修正
        // epoll模型下，open()--》  epoll_create -> fd3
        Selector selector = Selector.open();

        /**
         * 向多路复用器中注册OP_ACCEPT事件
         * select，poll：jvm里开辟一个数组 fd4 放进去
         * epoll：  epoll_ctl(fd3,ADD,fd4,EPOLLIN
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动了。。。。。");

        while (true) {
            //获取多路复用器中,即内核红黑树中 所有key,key代表一个连接
            //Set<SelectionKey> keys = selector.keys();

            /**
             * select() 调用多路复用器 (select,poll  or  epoll  (epoll_wait)) , 可设置超时
             * select，poll  其实  内核的select（fd4）  poll(fd4)
             * epoll：       其实  内核的 epoll_wait()
             * 懒加载：selector.select()调用的时候触发了epoll_ctl的调用
             */
            while (selector.select() > 0) {
                /** 一次调用返回此时所有 有效状态的fd集合 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectionKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isAcceptable()) {
                        // 监听到链接事件,获取socketChannel,注册OP_READ OP_WRITE到selector
                        acceptHandler(selector, key);
                    } else if (key.isReadable()) {
                        System.out.println("监控到可读事件 >>>>>>>>>>>>>>>>>  " + atomicInteger.decrementAndGet());
                        readHandler(key);
                    } else if (key.isWritable()) {

                    }
                }
            }
        }
    }

    public static void acceptHandler(Selector selector, SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept(); //调用accept接受客户端  fd7
            client.configureBlocking(false);     //设置非阻塞
            ByteBuffer buffer = ByteBuffer.allocate(1);  //获取buffer 一次读一个字节
            /**
             * select，poll：jvm里开辟一个数组 fd7 放进去
             * epoll：       epoll_ctl(fd3,ADD,fd7,EPOLLIN  放入内核红黑树
             */
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("acceptHandler 接收新客户端：" + client.getRemoteAddress() + " 并注册读事件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readHandler(SelectionKey key) {
        //new Thread(() -> { // 异步读取,造成重读读问题测试
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            int read = 0;
            String msg = "";
            try {
                while (true) {
                    read = socketChannel.read(buffer);
                    buffer.flip();
                    if (read > 0) {
                        msg = msg + decoder.decode(buffer);
                        buffer.clear();
                    } else if (read == 0) {//无数据可读
                        break;
                    } else { //-1 客户端关闭了连接
                        socketChannel.close();
                        break;
                    }
                }
                System.out.println("读事件消息内容: " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}).start();
    }

}

