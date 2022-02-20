package com.jsy.learn.io.nioreactor.reactor_v2;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerAcceptHandler implements Handler {
    ServerSocketChannel key;
    EventLoopGroup cGroup;

    ServerAcceptHandler(EventLoopGroup cGroup, ServerSocketChannel server) {
        this.key = server;
        this.cGroup = cGroup;
    }

    @Override
    public void doRead() {
        try {
            final EventLoop eventLoop = cGroup.chosser();
            final SocketChannel client = key.accept();
            client.configureBlocking(false);
            client.setOption(StandardSocketOptions.TCP_NODELAY, true);
            final ClientReader cHandler = new ClientReader(client);
            eventLoop.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        System.out.println("socket...send...to " + eventLoop.name+ " client port : " + client.socket().getPort());

                        client.register(eventLoop.selector, SelectionKey.OP_READ, cHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
