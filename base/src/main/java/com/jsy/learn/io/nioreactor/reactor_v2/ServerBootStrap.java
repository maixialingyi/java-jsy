package com.jsy.learn.io.nioreactor.reactor_v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class ServerBootStrap {
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    ServerAcceptHandler acceptHandler;

    public ServerBootStrap group(EventLoopGroup boss, EventLoopGroup worker) {
        bossGroup = boss;
        workerGroup = worker;
        return this;
    }

    /**
     *  启动server
     */
    public void bind(int port) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port));

        acceptHandler = new ServerAcceptHandler(workerGroup, server);
        //获取boss线程组中线程
        EventLoop bossEventloop = bossGroup.chosser();
        //把启动server，bind端口的操作变成task，推送到eventloop中执行。
        bossEventloop.execute(new Runnable() {
            @Override
            public void run() {
                bossEventloop.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bossEventloop.name = Thread.currentThread() + bossEventloop.name;
                            System.out.println("bind...server...to " + bossEventloop.name);
                            server.register(bossEventloop.selector, SelectionKey.OP_ACCEPT, acceptHandler);
                        } catch (ClosedChannelException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
