package com.jsy.learn.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单线程,线性处理,不注册可写事件
 */
public class SocketMultiplexingSingleThreadv1 {

    int port = 9090;
    private ServerSocketChannel server = null;
    private Selector selector = null;   //linux 多路复用器（select poll    epoll kqueue） nginx  event{}

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1 service = new SocketMultiplexingSingleThreadv1();
        service.start();
    }

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false); //设置非阻塞
            server.bind(new InetSocketAddress(port));  //绑定端口

            //如果在epoll模型下，open--》  epoll_create -> fd3
            selector = Selector.open();  //  select  poll  *epoll  优先选择：epoll  但是可以 -D修正

            /**
             * 向多路复用器中注册 链接事件
             * select，poll：jvm里开辟一个数组 fd4 放进去
             * epoll：  epoll_ctl(fd3,ADD,fd4,EPOLLIN
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {  /**死循环*/
                //获取多路复用器中,即内核红黑树中 所有key key代表一个连接
                //Set<SelectionKey> keys = selector.keys();

                /**
                 * select() 调用多路复用器 (select,poll  or  epoll  (epoll_wait)) , 可设置超时
                 * select，poll  其实  内核的select（fd4）  poll(fd4)
                 * epoll：       其实  内核的 epoll_wait()
                 *
                 * 懒加载：selector.select()调用的时候触发了epoll_ctl的调用
                 */
                while (selector.select() > 0) {
                    /** 一次调用返回此时所有 有效状态的fd集合 */
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove(); //set  不移除会重复循环处理 ?
                        if (key.isAcceptable()) { //链接事件处理
                            /**
                             * select，poll，链接在jvm中保存,和前边的fd4那个listen的一起
                             * epoll： 我们希望通过epoll_ctl把新的客户端fd注册到内核空间
                             */
                            acceptHandler(key);
                        } else if (key.isReadable()) { //read处理,并做了一次write
                            readHandler(key);
                            //在当前线程，这个方法可能会阻塞  ，如果阻塞了十年，其他的IO早就没电了。。。
                            //所以，为什么提出了 IO THREADS
                            //redis  是不是用了epoll，redis是不是有个io threads的概念 ，redis是不是单线程的
                            //tomcat 8,9  异步的处理方式  IO  和   处理上  解耦
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept(); //调用accept接受客户端  fd7
            client.configureBlocking(false);     //设置非阻塞

            ByteBuffer buffer = ByteBuffer.allocate(8192);  //获取buffer

            /**
             * 连接注册到多了复用器, 并监听有效读事件
             * select，poll：jvm里开辟一个数组 fd7 放进去
             * epoll：       epoll_ctl(fd3,ADD,fd7,EPOLLIN  放入内核红黑树
             */
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip(); // 翻转buffer,做写操作
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {//无数据可读
                    break;
                } else { //-1 客户端关闭了连接
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
