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
 * 单线程,线性处理,注册写事件
 */
public class SocketMultiplexingSingleThreadv1_1 {

    int port = 9090;
    private ServerSocketChannel server = null;
    private Selector selector = null;

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1_1 service = new SocketMultiplexingSingleThreadv1_1();
        service.start();
    }

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
                while (selector.select() > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);  //处理read,每次注册write事件
                        } else if (key.isWritable()) {
                            /**
                             * 连接可写: 系统send-queue为空,有空间,则可写,反之不可写.
                             * 为什么要写操作前才注册write事件? 如果连接创建后就注册write,系统send-queue为空,则会照成每次select()都
                             *      有可写事件,照成死循环,
                             *      所以什么时候要写才注册写事件,下次select()就可进行写操作,写完成要key.cancel()多路复用器中取消监听
                             *      因为取消的整个key监听,读也取消,需再次注册读
                             */
                            //写事件<--  send-queue  只要是空的，就一定会给你返回可以写的事件，就会回调我们的写方法
                            //多路复用器能不能写是参考send-queue有没有空间,如果一开始就注册了write的事件，进入死循环，一直调起！！！
                            //第一步骤，准备要写的数据
                            //第二步骤，判断send-queue是否有空间,即多路复用器是否可写
                            //所以 : 读 read 一开始就要注册，但是write依赖以上关系，什么时候用什么时候注册
                            //可用key.cancel(); 取消多路复用器中对此连接的写监听
                            writeHandler(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHandler(SelectionKey key) {
        System.out.println("write handler...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        while (buffer.hasRemaining()) {
            try {
                client.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.clear();
        key.cancel(); //从多路复用器中取消,取消事件监听
        try {
            client.close(); //关闭连接
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {

        System.out.println("read handler.....");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    client.register(key.selector(), SelectionKey.OP_WRITE, buffer);
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
