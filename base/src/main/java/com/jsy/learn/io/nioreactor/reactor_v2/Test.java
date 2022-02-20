package com.jsy.learn.io.nioreactor.reactor_v2;


import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //创建包含一个线程的线程组,用于Selector循环监听ServerSocketChannel 的 OP_ACCEPT
        EventLoopGroup bossGroup = new EventLoopGroup(1);
        //创建包含三个线程的线程组,用于Selector循环监听SocketChannel 的 OP_READ OP_WRITE
        EventLoopGroup workerGroup = new EventLoopGroup(3);

        ServerBootStrap b = new ServerBootStrap();
        /**
         * boss   指定监听ServerSocketChannel 的 OP_ACCEPT 线程组
         * worker 指定监听SocketChannel 的 OP_READ OP_WRITE 线程组
         * bind()
         */
        b.group(bossGroup, workerGroup).bind(9090);

        System.in.read();
    }
}
